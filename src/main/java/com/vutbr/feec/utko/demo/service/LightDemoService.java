package com.vutbr.feec.utko.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vutbr.feec.utko.demo.config.IMqttClientInstance;
import com.vutbr.feec.utko.demo.data.LightDemoRepository;
import com.vutbr.feec.utko.demo.utils.AbstractSensorsFields;
import com.vutbr.feec.utko.demo.utils.LightDto;
import com.vutbr.feec.utko.demo.utils.MqttAnomalyMessageLight;
import com.vutbr.feec.utko.demo.utils.SensorsState;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.time.LocalDateTime;

public class LightDemoService {

    private static Logger LOG = LoggerFactory.getLogger(LightDemoService.class);

    private MQTTPublisherService mqttPublisherService;
    private LightDemoRepository lightDemoRepository;
    private ObjectMapper objectMapper;

    public LightDemoService(MQTTPublisherService mqttPublisherService,
                            LightDemoRepository lightDemoRepository,
                            ObjectMapper objectMapper) {
        this.mqttPublisherService = mqttPublisherService;
        this.lightDemoRepository = lightDemoRepository;
        this.objectMapper = objectMapper;
    }

    public void subscribeMqttMessages() {
        String topic = "#";
        try {
            IMqttClientInstance.getInstance().subscribe(topic, (actualTopicValue, mqttMessage) -> {
                try {
                    LOG.info("Received MQTT message");
                    LOG.info("Subscribint topic: " + topic);
                    LOG.info("Actual topic: " + actualTopicValue);
                    String payload = new String(mqttMessage.getPayload());
                    LOG.info("MQTT message payload: " + payload);

                    // <home_id>/<gateway_id>/<dev_id>/<entity>/<msg_direction>
                    String[] sensorIds = actualTopicValue.split("/");

                    // if the communication is going in than it is not essential
                    if (sensorIds != null && sensorIds[4].equals("in")) {
                        return;
                    }
                    if (sensorIds != null && sensorIds.length > 2) {
                        String lightDemoId = sensorIds[2];
                        String sensorState = sensorIds[3];
                        if (sensorState.equals(AbstractSensorsFields.STATE)) {
                            LightDto lightDto = objectMapper.readValue(payload, LightDto.class);
                            lightDemoRepository.storeSettings(lightDemoId, lightDto.getState(), LocalDateTime.now(Clock.systemUTC()));

                            if (lightDto.getState().equals(SensorsState.ON)) {
                                MqttMessage mqttMessagePayloadToPublish = new MqttMessage();

                                MqttAnomalyMessageLight mqttAnomalyMessageLight = new MqttAnomalyMessageLight();
                                mqttAnomalyMessageLight.setMessage("It is not common that the light with id : " + lightDemoId + " is on in that time");
                                mqttAnomalyMessageLight.setTimestamp(System.currentTimeMillis());

                                mqttMessagePayloadToPublish.setPayload(objectMapper.writeValueAsString(mqttAnomalyMessageLight).getBytes());
                                StringBuilder sb = new StringBuilder();
                                sb.append(sensorIds[0]); //<home_id>
                                sb.append("/");
                                sb.append(sensorIds[1]); //<gateway_id>
                                sb.append("/");
                                sb.append("events");
                                mqttPublisherService.sendMessage(sb.toString(), mqttMessagePayloadToPublish);
                            }
                        }
                    }
                } catch (Exception e) {
                    LOG.error("MqttBrokerSubscriberAndAnalyzerServiceImqttClient subscribeMessage()", e);
                }
            });
        } catch (MqttException e) {
            LOG.error("Mqtt Subscribtion is not possible", e);
        }
    }
}
