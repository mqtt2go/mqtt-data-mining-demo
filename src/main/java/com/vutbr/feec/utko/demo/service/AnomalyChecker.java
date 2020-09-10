package com.vutbr.feec.utko.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vutbr.feec.utko.demo.repository.LightRepository;
import com.vutbr.feec.utko.demo.utils.MqttAnomalyMessageLight;
import com.vutbr.feec.utko.demo.utils.MqttAnomalyMessageLightValue;
import com.vutbr.feec.utko.demo.utils.SensorsState;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AnomalyChecker {

    private MQTTPublisherService mqttPublisherService;
    private LightRepository lightRepository;
    private ObjectMapper objectMapper;

    public AnomalyChecker(MQTTPublisherService mqttPublisherService,
                          LightRepository lightRepository,
                          ObjectMapper objectMapper) {
        this.mqttPublisherService = mqttPublisherService;
        this.lightRepository = lightRepository;
        this.objectMapper = objectMapper;
    }

    public void checkLightAnomaly() {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
                    String state = lightRepository.findSb1Anomaly();
                    if (state != null && state.equals(SensorsState.ON)) {
                        String message = "Your TV ambient lights are unexpectedly ON. Please check what is the source of such event.";
                        mqttPublisherService.invokeAnomalyLight("lights_alert", "alert", message);
                    }
                },
                0,
                2,
                TimeUnit.SECONDS);
        ScheduledExecutorService scheduledExecutorServiceBackToNormal = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorServiceBackToNormal.scheduleAtFixedRate(() -> {
                    String state = lightRepository.findSb1Anomaly();
                    if (state != null & state.equals(SensorsState.OFF)) {
                        String message = "Your lights are working back as expected..";
                        mqttPublisherService.invokeAnomalyLight("lights_alert", "ok", message);
                    }
                },
                0,
                2,
                TimeUnit.SECONDS);
    }

}