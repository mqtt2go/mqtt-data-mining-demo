package com.vutbr.feec.utko.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vutbr.feec.utko.demo.entities.CameraEntity;
import com.vutbr.feec.utko.demo.repository.CameraRepository;
import com.vutbr.feec.utko.demo.utils.AbstractSensorsFields;
import com.vutbr.feec.utko.demo.utils.SensorsReport;
import com.vutbr.feec.utko.demo.utils.SensorsReportValueAndUnit;
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
        String reportType = sensorIds[3];
        setCameraValue(reportType, mqttMessage, cameraEntity);
        // create new record
        addBasicFields(cameraEntity, sensorIds);
        cameraRepository.saveCamera(cameraEntity);
    }

    private void setCameraValue(String reportType, MqttMessage mqttMessage, CameraEntity cameraEntity) throws JsonProcessingException {
        if (reportType.equals(AbstractSensorsFields.STATE)) {
            String reportValueAndUnit = this.getReport(mqttMessage).getReport();
            cameraEntity.setState(reportValueAndUnit);
        }
    }

    private void addBasicFields(CameraEntity cameraEntity, String[] sensorsIds) {
        cameraEntity.setRecordTimestamp(new Timestamp(System.currentTimeMillis()));
        cameraEntity.setHomeId(sensorsIds[0]);
        cameraEntity.setGatewayId(sensorsIds[1]);
        cameraEntity.setDeviceId(sensorsIds[2]);
    }

    private SensorsReportValueAndUnit getReportValueAndUnit(MqttMessage mqttMessage) throws JsonProcessingException {
        return objectMapper.readValue(new String(mqttMessage.getPayload()), SensorsReportValueAndUnit.class);
    }

    private SensorsReport getReport(MqttMessage mqttMessage) throws JsonProcessingException {
        return objectMapper.readValue(new String(mqttMessage.getPayload()), SensorsReport.class);
    }

}
