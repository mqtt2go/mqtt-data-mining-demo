package com.vutbr.feec.utko.demo.config;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IMqttClientInstance {

    private static Logger LOG = LoggerFactory.getLogger(IMqttClientInstance.class);

    public static final String MQTT_BROKER_IP = "192.168.42.172";
    public static final int MQTT_BROKER_PORT = 8883;
    public static final String MQTT_BROKER_USERNAME = "mqtt2go";
    public static final String MQTT_BROKER_PASSWORD = "FritzBox2";
    public static final String MQTT_DATA_MINING_APP_TOPIC = "BRQ/BUT";
    public static final String MQTT_CLIENT_ID = "mqttisthebest";
    public static final String MQTT_BROKER_QUERY_ALL_TOPIC = "BRQ/BUT";
    public static IMqttClient instance;

    public static IMqttClient getInstance() {
        try {
            if (instance == null) {
                instance = new MqttClient("ssl://" + MQTT_BROKER_IP, MQTT_CLIENT_ID);
            }
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setKeepAliveInterval(15);
            connOpts.setConnectionTimeout(30);
            connOpts.setUserName(MQTT_BROKER_USERNAME);
            connOpts.setPassword(MQTT_BROKER_PASSWORD.toCharArray());

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            if (!instance.isConnected()) {
                instance.connect(options);
            }
        } catch (MqttException e) {
            LOG.error("IMqttClientInstance getInstance() error:", e);
        }
        return instance;
    }

    private IMqttClientInstance() {
    }
}