package com.vutbr.feec.utko.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vutbr.feec.utko.demo.repository.LightRepository;
import com.vutbr.feec.utko.demo.utils.MqttAnomalyMessageLight;
import com.vutbr.feec.utko.demo.utils.MqttAnomalyMessageLightValue;
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
                    if (lightRepository.findSb1OnAnomaly()) {
                        String message = "Your TV ambient lights are unexpectedly ON. Please check what is the source of such event.";
                        this.invokeAnomalyLight("lights_alert", "alert", message);
                    }
                },
                0,
                15,
                TimeUnit.SECONDS);
        ScheduledExecutorService scheduledExecutorServiceBackToNormal = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorServiceBackToNormal.scheduleAtFixedRate(() -> {
                    if (lightRepository.findSb1OffAnomaly()) {
                        String message = "Your lights are working back as expected..";
                        this.invokeAnomalyLight("lights_alert", "ok", message);
                    }
                },
                0,
                30,
                TimeUnit.SECONDS);
    }

    private void invokeAnomalyLight(String eventName, String status, String message) {
        // <home_id>/<gateway_id>/<dev_id>/<entity>/<msg_direction>
        MqttMessage mqttMessagePayloadToPublish = new MqttMessage();

        MqttAnomalyMessageLight mqttAnomalyMessageLight = new MqttAnomalyMessageLight();
        MqttAnomalyMessageLightValue mqttAnomalyMessageLightValue = new MqttAnomalyMessageLightValue();
        mqttAnomalyMessageLightValue.setEventName(eventName);
        mqttAnomalyMessageLightValue.setMessage(message);
        mqttAnomalyMessageLightValue.setStatus(status);
        mqttAnomalyMessageLight.setTimestamp(System.currentTimeMillis());
        mqttAnomalyMessageLight.setValue(mqttAnomalyMessageLightValue);

        try {
            mqttMessagePayloadToPublish.setPayload(objectMapper.writeValueAsString(mqttAnomalyMessageLight).getBytes());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("BRQ/BUT/events/in");
        mqttPublisherService.sendMessage(sb.toString(), mqttMessagePayloadToPublish);
    }
}