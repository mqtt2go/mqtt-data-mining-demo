package com.vutbr.feec.utko.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vutbr.feec.utko.demo.entities.SocketEntity;
import com.vutbr.feec.utko.demo.entities.SocketLastSettingsEntity;
import com.vutbr.feec.utko.demo.repository.SocketRepository;
import com.vutbr.feec.utko.demo.utils.*;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;
import java.util.Optional;

public class SocketService {

    private SocketRepository socketRepository;
    private ObjectMapper objectMapper;
    private ModelMapper modelMapper;

    public SocketService(SocketRepository socketRepository,
                         ObjectMapper objectMapper,
                         ModelMapper modelMapper) {
        this.socketRepository = socketRepository;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }

    public void storeSocketData(String[] sensorIds, MqttMessage mqttMessage) throws JsonProcessingException {
        SocketEntity socket = new SocketEntity();
        socket.setUserInHome(true);
        String reportType = sensorIds[3];
        try {
            Optional<SocketLastSettingsEntity> socketLastSettingsOpt = socketRepository.findSocketSettingsByHomeIdGatewayIdDeviceId(sensorIds[0], sensorIds[1], sensorIds[2]);
            SocketLastSettingsEntity socketLastSettings = socketLastSettingsOpt.get();
            modelMapper.map(socketLastSettings, socket);
            setSocketValue(reportType, mqttMessage, socket, socketLastSettings);
            // update last settings record
            addBasicSocketLastSettingsFields(socketLastSettings, sensorIds);
            socketRepository.updateSocketLastSettings(socketLastSettings);
        } catch (org.springframework.dao.EmptyResultDataAccessException ex) {
            SocketLastSettingsEntity socketLastSettings = new SocketLastSettingsEntity();
            setSocketValue(reportType, mqttMessage, socket, socketLastSettings);
            // create new record
            addBasicSocketLastSettingsFields(socketLastSettings, sensorIds);
            socketRepository.saveSocketLastSettings(socketLastSettings);
        }
        addBasicSocketFields(socket, sensorIds);
        socketRepository.saveSocket(socket);
    }

    private void setSocketValue(String reportType, MqttMessage mqttMessage, SocketEntity socket, SocketLastSettingsEntity socketLastSettings) throws JsonProcessingException {
        if (reportType.equals(AbstractSensorsFields.STATE)) {
            String reportValueAndUnit = this.getReport(mqttMessage).getReport();
            socket.setState(reportValueAndUnit);
            socketLastSettings.setState(reportValueAndUnit);
        } else if (reportType.equals(AbstractSensorsFields.CONSUMPTION)) {
            ReportValueAndUnitDecimal reportValueAndUnit = this.getReportValueAndUnit(mqttMessage).getReport();
            socket.setConsumption(reportValueAndUnit.getValue());
            socket.setConsumptionUnit(reportValueAndUnit.getUnit());
            socketLastSettings.setConsumption(reportValueAndUnit.getValue());
            socketLastSettings.setConsumptionUnit(reportValueAndUnit.getUnit());
        } else if (reportType.equals(AbstractSensorsFields.CURRENT)) {
            ReportValueAndUnitDecimal reportValueAndUnit = this.getReportValueAndUnit(mqttMessage).getReport();
            socket.setCurrent(reportValueAndUnit.getValue());
            socket.setCurrentUnit(reportValueAndUnit.getUnit());
            socketLastSettings.setCurrent(reportValueAndUnit.getValue());
            socketLastSettings.setCurrentUnit(reportValueAndUnit.getUnit());
        } else if (reportType.equals(AbstractSensorsFields.VOLTAGE)) {
            ReportValueAndUnitDecimal reportValueAndUnit = this.getReportValueAndUnit(mqttMessage).getReport();
            socket.setVoltage(reportValueAndUnit.getValue());
            socket.setVoltageUnit(reportValueAndUnit.getUnit());
            socketLastSettings.setVoltage(reportValueAndUnit.getValue());
            socketLastSettings.setVoltageUnit(reportValueAndUnit.getUnit());
        } else if (reportType.equals(AbstractSensorsFields.POWER)) {
            ReportValueAndUnitDecimal reportValueAndUnit = this.getReportValueAndUnit(mqttMessage).getReport();
            socket.setPower(reportValueAndUnit.getValue());
            socket.setPowerUnit(reportValueAndUnit.getUnit());
            socketLastSettings.setPower(reportValueAndUnit.getValue());
            socketLastSettings.setPowerUnit(reportValueAndUnit.getUnit());
        }
    }

    private void addBasicSocketFields(SocketEntity socket, String[] sensorsIds) {
        socket.setRecordTimestamp(new Timestamp(System.currentTimeMillis()));
        socket.setHomeId(sensorsIds[0]);
        socket.setGatewayId(sensorsIds[1]);
        socket.setDeviceId(sensorsIds[2]);
    }

    private void addBasicSocketLastSettingsFields(SocketLastSettingsEntity socketLastSettings, String[] sensorIds) {
        socketLastSettings.setRecordTimestamp(new Timestamp(System.currentTimeMillis()));
        socketLastSettings.setHomeId(sensorIds[0]);
        socketLastSettings.setGatewayId(sensorIds[1]);
        socketLastSettings.setDeviceId(sensorIds[2]);
    }

    private SensorsReportValueAndUnitBigDecimal getReportValueAndUnit(MqttMessage mqttMessage) throws JsonProcessingException {
        return objectMapper.readValue(new String(mqttMessage.getPayload()), SensorsReportValueAndUnitBigDecimal.class);
    }

    private SensorsReport getReport(MqttMessage mqttMessage) throws JsonProcessingException {
        return objectMapper.readValue(new String(mqttMessage.getPayload()), SensorsReport.class);
    }

}
