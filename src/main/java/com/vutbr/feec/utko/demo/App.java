package com.vutbr.feec.utko.demo;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vutbr.feec.utko.demo.data.LightDemoRepository;
import com.vutbr.feec.utko.demo.dbconnection.HikariDataSourceJdbcTemplate;
import com.vutbr.feec.utko.demo.service.LightDemoService;
import com.vutbr.feec.utko.demo.service.MQTTPublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

public class App {

    private static Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            JdbcTemplate jdbcTemplate = new JdbcTemplate(HikariDataSourceJdbcTemplate.getDataSource());

            MQTTPublisherService mqttPublisherService = new MQTTPublisherService();
            LightDemoRepository lightDemoRepository = new LightDemoRepository(jdbcTemplate);
            LightDemoService lightDemoService = new LightDemoService(mqttPublisherService, lightDemoRepository, objectMapper);

            lightDemoService.subscribeMqttMessages();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
    }
}