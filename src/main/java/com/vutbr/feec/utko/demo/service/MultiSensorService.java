package com.vutbr.feec.utko.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vutbr.feec.utko.demo.entities.MultiSensorEntity;
import com.vutbr.feec.utko.demo.entities.MultiSensorLastSettingsEntity;
import com.vutbr.feec.utko.demo.repository.MultiSensorRepository;
import com.vutbr.feec.utko.demo.utils.*;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Optional;

public class MultiSensorService {

    private MultiSensorRepository multiSensorRepository;
    private ObjectMapper objectMapper;
    private ModelMapper modelMapper;

    @Autowired
    public MultiSensorService(MultiSensorRepository multiSensorRepository,
                              ObjectMapper objectMapper,
                              ModelMapper modelMapper) {
        this.multiSensorRepository = multiSensorRepository;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }

    public void storeMultiSensorData(String[] sensorIds, MqttMessage mqttMessage) throws JsonProcessingException {
        MultiSensorEntity multiSensor = new MultiSensorEntity();
        String reportType = sensorIds[3];
        try {
            Optional<MultiSensorLastSettingsEntity> multiSensorLastSettingsOpt = multiSensorRepository.findMultiSensorSettingsByHomeIdGatewayIdDeviceId(sensorIds[0], sensorIds[1], sensorIds[2]);
            MultiSensorLastSettingsEntity multiSensorLastSettings = multiSensorLastSettingsOpt.get();
            modelMapper.map(multiSensorLastSettings, multiSensor);
            setMultiSensorValue(reportType, mqttMessage, multiSensor, multiSensorLastSettings);
            // update last settings record
            addBasicMultiSensorLastSettingsFields(multiSensorLastSettings, sensorIds);
            multiSensorRepository.updateMultiSensorLastSettings(multiSensorLastSettings);
        } catch (org.springframework.dao.EmptyResultDataAccessException ex) {
            MultiSensorLastSettingsEntity multiSensorLastSettings = new MultiSensorLastSettingsEntity();
            setMultiSensorValue(reportType, mqttMessage, multiSensor, multiSensorLastSettings);
            // create new record
            addBasicMultiSensorLastSettingsFields(multiSensorLastSettings, sensorIds);
            multiSensorRepository.saveMultiSensorLastSettings(multiSensorLastSettings);

        }
        addBasicMultiSensorFields(multiSensor, sensorIds);
        multiSensorRepository.saveMultiSensor(multiSensor);
    }

    private void setMultiSensorValue(String reportType, MqttMessage mqttMessage, MultiSensorEntity multiSensor, MultiSensorLastSettingsEntity multiSensorLastSettings) throws JsonProcessingException {
        if (reportType.equals(AbstractSensorsFields.STATE)) {
            String reportValueAndUnit = this.getReport(mqttMessage).getReport();
            multiSensor.setState(reportValueAndUnit);
            multiSensorLastSettings.setState(reportValueAndUnit);
        } else if (reportType.equals(AbstractSensorsFields.TEMPERATURE)) {
            ReportValueAndUnitDecimal reportValueAndUnit = this.getReportValueAndUnit(mqttMessage).getReport();
            multiSensor.setTemperature(reportValueAndUnit.getValue());
            multiSensorLastSettings.setTemperature(reportValueAndUnit.getValue());
        } else if (reportType.equals(AbstractSensorsFields.HUMIDITY)) {
            ReportValueAndUnitDecimal reportValueAndUnit = this.getReportValueAndUnit(mqttMessage).getReport();
            multiSensor.setHumidity(reportValueAndUnit.getValue());
            multiSensorLastSettings.setHumidity(reportValueAndUnit.getValue());
        } else if (reportType.equals(AbstractSensorsFields.MOTION)) {
            String reportValueAndUnit = this.getReport(mqttMessage).getReport();
            multiSensor.setMotion(reportValueAndUnit);
            multiSensorLastSettings.setMotion(reportValueAndUnit);
        } else if (reportType.equals(AbstractSensorsFields.SMOKE)) {
            String reportValueAndUnit = this.getReport(mqttMessage).getReport();
            multiSensor.setSmoke(reportValueAndUnit);
            multiSensorLastSettings.setSmoke(reportValueAndUnit);
        } else if (reportType.equals(AbstractSensorsFields.WATER)) {
            String reportValueAndUnit = this.getReport(mqttMessage).getReport();
            multiSensor.setWater(reportValueAndUnit);
            multiSensorLastSettings.setWater(reportValueAndUnit);
        }
    }

    private void addBasicMultiSensorFields(MultiSensorEntity multiSensor, String[] sensorsIds) {
        multiSensor.setRecordTimestamp(new Timestamp(System.currentTimeMillis()));
        multiSensor.setHomeId(sensorsIds[0]);
        multiSensor.setGatewayId(sensorsIds[1]);
        multiSensor.setDeviceId(sensorsIds[2]);
    }

    private void addBasicMultiSensorLastSettingsFields(MultiSensorLastSettingsEntity multiSensorLastSettings, String[] sensorIds) {
        multiSensorLastSettings.setRecordTimestamp(new Timestamp(System.currentTimeMillis()));
        multiSensorLastSettings.setHomeId(sensorIds[0]);
        multiSensorLastSettings.setGatewayId(sensorIds[1]);
        multiSensorLastSettings.setDeviceId(sensorIds[2]);
    }

    public SensorsReportValueAndUnitBigDecimal getReportValueAndUnit(MqttMessage mqttMessage) throws JsonProcessingException {
        return objectMapper.readValue(new String(mqttMessage.getPayload()), SensorsReportValueAndUnitBigDecimal.class);
    }

    public SensorsReport getReport(MqttMessage mqttMessage) throws JsonProcessingException {
        return objectMapper.readValue(new String(mqttMessage.getPayload()), SensorsReport.class);
    }

}
