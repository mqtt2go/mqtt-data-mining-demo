package com.vutbr.feec.utko.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vutbr.feec.utko.demo.config.ObjectMapperConfig;
import com.vutbr.feec.utko.demo.repository.CameraRepository;
import com.vutbr.feec.utko.demo.repository.MultiSensorRepository;
import com.vutbr.feec.utko.demo.repository.SocketRepository;
import com.vutbr.feec.utko.demo.repository.LightRepository;
import com.vutbr.feec.utko.demo.dbconnection.HikariDataSourceJdbcTemplate;
import com.vutbr.feec.utko.demo.service.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

public class App {

    private static Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        try {
            ObjectMapperConfig objectMapperConfig = new ObjectMapperConfig();
            ObjectMapper objectMapper = objectMapperConfig.getObjectMapper();

            ModelMapper modelMapper = new ModelMapper();

            JdbcTemplate jdbcTemplate = new JdbcTemplate(HikariDataSourceJdbcTemplate.getDataSource());

            SocketRepository socketRepository = new SocketRepository(jdbcTemplate);
            LightRepository lightRepository = new LightRepository(jdbcTemplate);
            CameraRepository cameraRepository = new CameraRepository(jdbcTemplate);
            MultiSensorRepository multiSensorRepository = new MultiSensorRepository(jdbcTemplate);

            SocketService socketService = new SocketService(socketRepository, objectMapper, modelMapper);
            LightService lightService = new LightService(lightRepository, objectMapper, modelMapper);
            CameraService cameraService = new CameraService(cameraRepository, objectMapper, modelMapper);
            MultiSensorService multiSensorService = new MultiSensorService(multiSensorRepository, objectMapper, modelMapper);

            MQTTPublisherService mqttPublisherService = new MQTTPublisherService(objectMapper);
            MQTTSubscriberService mqttSubscriberService = new MQTTSubscriberService(mqttPublisherService, lightService, socketService, cameraService, multiSensorService, objectMapper);
//            if (args != null && args.length > 0 && !args[0].isBlank()) {
//                mqttSubscriberService.subscribeMqttMessages(args[0]);
//            }
            mqttSubscriberService.subscribeMqttMessages("sb1");
            mqttSubscriberService.publishRequestForGetAllDevicesTypes();

            AnomalyChecker anomalyChecker = new AnomalyChecker(mqttPublisherService, lightRepository, objectMapper);
            anomalyChecker.checkLightAnomaly();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        } catch (JsonProcessingException e) {
            LOG.error(e.getMessage());
        }
    }
}