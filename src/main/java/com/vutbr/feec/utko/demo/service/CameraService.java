package com.vutbr.feec.utko.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vutbr.feec.utko.demo.entities.CameraEntity;
import com.vutbr.feec.utko.demo.repository.CameraRepository;
import com.vutbr.feec.utko.demo.utils.AbstractSensorsFields;
import com.vutbr.feec.utko.demo.utils.SensorsReportValue;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;

public class CameraService {

    private CameraRepository cameraRepository;
    private ObjectMapper objectMapper;
    private ModelMapper modelMapper;

    public CameraService(CameraRepository cameraRepository,
                         ObjectMapper objectMapper,
                         ModelMapper modelMapper) {
        this.cameraRepository = cameraRepository;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }

    public void storeCameraData(String[] sensorIds, MqttMessage mqttMessage) throws JsonProcessingException {
        CameraEntity cameraEntity = new CameraEntity();
        cameraEntity.setUserInHome(true);
        String reportType = sensorIds[3];
        setCameraValue(reportType, mqttMessage, cameraEntity);
        // create new record
        addBasicFields(cameraEntity, sensorIds);
        cameraRepository.saveCamera(cameraEntity);
    }

    private void setCameraValue(String reportType, MqttMessage mqttMessage, CameraEntity cameraEntity) throws JsonProcessingException {
        if (reportType.equals(AbstractSensorsFields.MOTION_DETECTION)) {
            String reportValue = this.getReport(mqttMessage).getReport().getValue();
            cameraEntity.setState(reportValue);
        }
    }

    private void addBasicFields(CameraEntity cameraEntity, String[] sensorsIds) {
        cameraEntity.setRecordTimestamp(new Timestamp(System.currentTimeMillis()));
        cameraEntity.setHomeId(sensorsIds[0]);
        cameraEntity.setGatewayId(sensorsIds[1]);
        cameraEntity.setDeviceId(sensorsIds[2]);
    }

    private SensorsReportValue getReport(MqttMessage mqttMessage) throws JsonProcessingException {
        return objectMapper.readValue(new String(mqttMessage.getPayload()), SensorsReportValue.class);
    }

}
