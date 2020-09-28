package com.vutbr.feec.utko.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vutbr.feec.utko.demo.entities.LightEntity;
import com.vutbr.feec.utko.demo.repository.LightRepository;
import com.vutbr.feec.utko.demo.utils.AbstractSensorsFields;
import com.vutbr.feec.utko.demo.utils.LightReportValue;
import com.vutbr.feec.utko.demo.utils.SensorsState;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LightService {

    private LightRepository lightRepository;
    private ObjectMapper objectMapper;
    private MQTTPublisherService mqttPublisherService;
    private ModelMapper modelMapper;

    public LightService(LightRepository lightRepository,
                        ObjectMapper objectMapper,
                        ModelMapper modelMapper,
                        MQTTPublisherService mqttPublisherService) {
        this.lightRepository = lightRepository;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
        this.mqttPublisherService = mqttPublisherService;
    }

    public void storeLightData(String[] sensorIds, MqttMessage mqttMessage) throws JsonProcessingException {
        LightEntity lightEntity = new LightEntity();
        lightEntity.setUserInHome(true);
        String reportType = sensorIds[3];
        setLightValue(reportType, mqttMessage, lightEntity);
        addBasicLightFields(lightEntity, sensorIds);
        checkAnomaly(sensorIds, lightEntity);
        lightRepository.saveLight(lightEntity);
    }

    private void checkAnomaly(String[] sensorIds, LightEntity lightEntity) {
        String state = lightEntity.getState();
        if (sensorIds[2] != null && sensorIds[2].equals("sb1") && state != null && state.equals(SensorsState.ON)) {
            String message = "Your TV ambient lights are unexpectedly ON. Please check what is the source of such event.";
            // delay this action with 1.5 seconds
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(() -> mqttPublisherService.invokeAnomalyLight("lights_alert", "alert", message));
        } else if (sensorIds[2] != null && sensorIds[2].equals("sb1") && state != null & state.equals(SensorsState.OFF)) {
            String message = "Your lights are working back as expected..";
            // delay this action with 1.5 seconds
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(() -> mqttPublisherService.invokeAnomalyLight("lights_alert", "ok", message));
        }
    }

    private void setLightValue(String reportType, MqttMessage mqttMessage, LightEntity lightEntity) throws JsonProcessingException {
        if (reportType.equals(AbstractSensorsFields.SWITCH)) {
            String reportValueAndUnit = this.getReport(mqttMessage).getValue();
            lightEntity.setState(reportValueAndUnit);
        }
    }

    private void addBasicLightFields(LightEntity lightEntity, String[] sensorsIds) {
        lightEntity.setRecordTimestamp(new Timestamp(System.currentTimeMillis()));
        lightEntity.setHomeId(sensorsIds[0]);
        lightEntity.setGatewayId(sensorsIds[1]);
        lightEntity.setDeviceId(sensorsIds[2]);
    }

    private LightReportValue getReport(MqttMessage mqttMessage) throws JsonProcessingException {
        return objectMapper.readValue(new String(mqttMessage.getPayload()), LightReportValue.class);
    }

}
