package com.vutbr.feec.utko.demo.service;

import com.vutbr.feec.utko.demo.config.IMqttClientInstance;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQTTPublisherService {

    private static Logger LOG = LoggerFactory.getLogger(MQTTPublisherService.class);

    public void sendMessage(String mqttTopic, MqttMessage mqttMessage) {
        try {
            IMqttClient iMqttClient = IMqttClientInstance.getInstance();
            iMqttClient.publish(mqttTopic, mqttMessage);
            LOG.info("MqttClient client published a message..:" + System.lineSeparator() + new String(mqttMessage.getPayload()));
        } catch (MqttException e) {
            LOG.error(e.getMessage());
        }
    }

}
