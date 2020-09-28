package com.vutbr.feec.utko.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vutbr.feec.utko.demo.config.IMqttClientInstance;
import com.vutbr.feec.utko.demo.utils.MqttAnomalyMessageLight;
import com.vutbr.feec.utko.demo.utils.MqttAnomalyMessageLightValue;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQTTPublisherService {

    private static Logger LOG = LoggerFactory.getLogger(MQTTPublisherService.class);

    private ObjectMapper objectMapper;

    public MQTTPublisherService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void sendMessage(String mqttTopic, MqttMessage mqttMessage) {
        try {
            IMqttClient iMqttClient = IMqttClientInstance.getInstance();
            iMqttClient.publish(mqttTopic, mqttMessage);
            LOG.info("MqttClient client published a message..:" + System.lineSeparator() + new String(mqttMessage.getPayload()));
        } catch (MqttException e) {
            LOG.error(e.getMessage());
        }
    }

    public void invokeAnomalyLight(String eventName, String status, String message) {
        // <home_id>/<gateway_id>/<dev_id>/<entity>/<msg_direction>
        MqttMessage mqttMessagePayloadToPublish = new MqttMessage();

        MqttAnomalyMessageLight mqttAnomalyMessageLight = new MqttAnomalyMessageLight();
        MqttAnomalyMessageLightValue mqttAnomalyMessageLightValue = new MqttAnomalyMessageLightValue();
        mqttAnomalyMessageLightValue.setEventName(eventName);
        mqttAnomalyMessageLightValue.setMessage(message);
        mqttAnomalyMessageLightValue.setStatus(status);
        mqttAnomalyMessageLight.setTimestamp(System.currentTimeMillis());
        mqttAnomalyMessageLight.setValue(mqttAnomalyMessageLightValue);
        mqttAnomalyMessageLight.setType("alert");

        try {
            mqttMessagePayloadToPublish.setPayload(objectMapper.writeValueAsString(mqttAnomalyMessageLight).getBytes());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("BRQ/BUT/events/in");
        this.sendMessage(sb.toString(), mqttMessagePayloadToPublish);
    }

}
