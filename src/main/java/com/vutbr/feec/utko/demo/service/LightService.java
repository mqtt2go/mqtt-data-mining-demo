package com.vutbr.feec.utko.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vutbr.feec.utko.demo.entities.LightEntity;
import com.vutbr.feec.utko.demo.entities.LightLastSettingsEntity;
import com.vutbr.feec.utko.demo.repository.LightRepository;
import com.vutbr.feec.utko.demo.utils.AbstractSensorsFields;
import com.vutbr.feec.utko.demo.utils.LightReportValue;
import com.vutbr.feec.utko.demo.utils.SensorsReport;
import com.vutbr.feec.utko.demo.utils.SensorsReportValueAndUnit;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;
import java.util.Optional;

public class LightService {

    private LightRepository lightRepository;
    private ObjectMapper objectMapper;
    private ModelMapper modelMapper;

    public LightService(LightRepository lightRepository,
                        ObjectMapper objectMapper,
                        ModelMapper modelMapper) {
        this.lightRepository = lightRepository;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }

    public void storeLightData(String[] sensorIds, MqttMessage mqttMessage) throws JsonProcessingException {
        LightEntity lightEntity = new LightEntity();
        lightEntity.setUserInHome(true);
        String reportType = sensorIds[3];
        try {
            Optional<LightLastSettingsEntity> lightLastSettingsOpt = lightRepository.findLightSettingsByHomeIdGatewayIdDeviceId(sensorIds[0], sensorIds[1], sensorIds[2]);
            LightLastSettingsEntity lightLastSettings = lightLastSettingsOpt.get();
            modelMapper.map(lightLastSettings, lightEntity);
            setLightValue(reportType, mqttMessage, lightEntity, lightLastSettings);
            // update last settings record
            addBasicLightLastSettingsFields(lightLastSettings, sensorIds);
            lightRepository.updateLightLastSettings(lightLastSettings);
        } catch (org.springframework.dao.EmptyResultDataAccessException ex) {
            LightLastSettingsEntity lightLastSettingsEntity = new LightLastSettingsEntity();
            setLightValue(reportType, mqttMessage, lightEntity, lightLastSettingsEntity);
            // create new record
            addBasicLightLastSettingsFields(lightLastSettingsEntity, sensorIds);
            lightRepository.saveLightLastSettings(lightLastSettingsEntity);
        }
        addBasicLightFields(lightEntity, sensorIds);
        lightRepository.saveLight(lightEntity);
    }

    private void setLightValue(String reportType, MqttMessage mqttMessage, LightEntity lightEntity, LightLastSettingsEntity lightLastSettings) throws JsonProcessingException {
        if (reportType.equals(AbstractSensorsFields.SWITCH)) {
            String reportValueAndUnit = this.getReport(mqttMessage).getValue();
            lightEntity.setState(reportValueAndUnit);
            lightLastSettings.setState(reportValueAndUnit);
        }
    }

    private void addBasicLightFields(LightEntity lightEntity, String[] sensorsIds) {
        lightEntity.setRecordTimestamp(new Timestamp(System.currentTimeMillis()));
        lightEntity.setHomeId(sensorsIds[0]);
        lightEntity.setGatewayId(sensorsIds[1]);
        lightEntity.setDeviceId(sensorsIds[2]);
    }

    private void addBasicLightLastSettingsFields(LightLastSettingsEntity switchLastSettings, String[] sensorIds) {
        switchLastSettings.setRecordTimestamp(new Timestamp(System.currentTimeMillis()));
        switchLastSettings.setHomeId(sensorIds[0]);
        switchLastSettings.setGatewayId(sensorIds[1]);
        switchLastSettings.setDeviceId(sensorIds[2]);
    }

    private LightReportValue getReport(MqttMessage mqttMessage) throws JsonProcessingException {
        return objectMapper.readValue(new String(mqttMessage.getPayload()), LightReportValue.class);
    }

}
