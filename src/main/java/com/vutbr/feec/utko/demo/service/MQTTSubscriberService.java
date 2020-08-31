package com.vutbr.feec.utko.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vutbr.feec.utko.demo.config.IMqttClientInstance;
import com.vutbr.feec.utko.demo.dto.LightFromBrokerDto;
import com.vutbr.feec.utko.demo.utils.*;
import com.vutbr.feec.utko.demo.utils.mappings.*;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MQTTSubscriberService {

    private static Logger LOG = LoggerFactory.getLogger(MQTTSubscriberService.class);

    private MQTTPublisherService mqttPublisherService;
    private LightService lightService;
    private SocketService socketService;
    private CameraService cameraService;
    private MultiSensorService multiSensorService;
    private ObjectMapper objectMapper;

    // key - device_id, value - device_type
    private static Map<String, String> DEVICE_ID_TYPE_MAPPING_MAP = new ConcurrentHashMap<>();
    private static boolean LIGHT_STATE = false;

    public MQTTSubscriberService(MQTTPublisherService mqttPublisherService,
                                 LightService lightService,
                                 SocketService socketService,
                                 CameraService cameraService,
                                 MultiSensorService multiSensorService,
                                 ObjectMapper objectMapper) {
        this.mqttPublisherService = mqttPublisherService;
        this.lightService = lightService;
        this.socketService = socketService;
        this.cameraService = cameraService;
        this.multiSensorService = multiSensorService;
        this.objectMapper = objectMapper;
    }

    public void subscribeMqttMessages(String deviceIdToCheck) {
        String topic = "#";
        try {
            IMqttClientInstance.getInstance().subscribe(topic, (actualTopicValue, mqttMessage) -> {
                try {
                    LOG.info("Received MQTT message");
                    LOG.info("Subscribint topic: " + topic);
                    LOG.info("Actual topic: " + actualTopicValue);
                    String payload = new String(mqttMessage.getPayload());
                    LOG.info("MQTT message payload: " + System.lineSeparator() + payload);

                    // <home_id>/<gateway_id>/<dev_id>/<entity>/<msg_direction>
                    String[] sensorIds = actualTopicValue.split("/");

                    if (!actualTopicValue.contains("BRQ/BUT")) {
                        return;
                    }

                    DEVICE_ID_TYPE_MAPPING_MAP.put("sb1", "light");

                    if (payload.contains("query_all") && payload.contains("report_name")) {
                        refreshMapForDeviceIDTypeMapping(mqttMessage);
                        return;
                    }

                    String deviceType = DEVICE_ID_TYPE_MAPPING_MAP.get(sensorIds[2]);
                    if ((deviceType == null || deviceType.equals("")) && !sensorIds[3].equals(AbstractSensorsFields.MOTION_DETECTION)) {
                        return;
                    }

                    // if the communication is going in than it is not essential
                    if (sensorIds[4] != null && sensorIds[4].equals("in")) {
                        return;
                    }

                    if (deviceType != null) {
                        if (deviceType.equals("socket")) {
                            socketService.storeSocketData(sensorIds, mqttMessage);
                        } else if (deviceType.equals("light")) {
                            lightService.storeLightData(sensorIds, mqttMessage);
                        } else if (deviceType.equals("multi_sensor")) {
                            System.out.println("Store multisensor data..");
                            multiSensorService.storeMultiSensorData(sensorIds, mqttMessage);
                        } else if (deviceType.equals("camera")) {
                            cameraService.storeCameraData(sensorIds, mqttMessage);
                        }
                    }
                } catch (Exception e) {
                    LOG.error("MQTTSubscriberService subscribeMessage()", e);
                }
            });
        } catch (MqttException e) {
            LOG.error("Mqtt Subscribtion is not possible", e);
        }
    }

    public void publishRequestForGetAllDevicesTypes() throws JsonProcessingException {
        QueryAllComands queryAllComands = new QueryAllComands();
        mqttPublisherService.sendMessage(IMqttClientInstance.MQTT_BROKER_QUERY_ALL_TOPIC, new MqttMessage(objectMapper.writeValueAsString(queryAllComands).getBytes()));
        System.out.println("Published a message: " + objectMapper.writeValueAsString(queryAllComands));
    }

    private void refreshMapForDeviceIDTypeMapping(MqttMessage mqttMessage) throws JsonProcessingException {
        DeviceIdDeviceTypeMappingRoot deviceIdDeviceTypeMappingRoot = objectMapper.readValue(new String(mqttMessage.getPayload()), DeviceIdDeviceTypeMappingRoot.class);
        DeviceIdDeviceTypeMapping deviceIdDeviceTypeMapping = deviceIdDeviceTypeMappingRoot.getDeviceIdDeviceTypeMapping();
        if (deviceIdDeviceTypeMapping.getCameraMapping() != null) {
            CameraMapping[] cameraMapping = deviceIdDeviceTypeMapping.getCameraMapping();
            for (CameraMapping camera : cameraMapping) {
                DEVICE_ID_TYPE_MAPPING_MAP.put(camera.getId(), "camera");
            }
        }
        if (deviceIdDeviceTypeMapping.getLightMapping() != null) {
            LightMapping[] lightMapping = deviceIdDeviceTypeMapping.getLightMapping();
            for (LightMapping light : lightMapping) {
                DEVICE_ID_TYPE_MAPPING_MAP.put(light.getId(), "light");
            }
        }
        if (deviceIdDeviceTypeMapping.getMultiSensorMapping() != null) {
            MultiSensorMapping[] multiSensorMapping = deviceIdDeviceTypeMapping.getMultiSensorMapping();
            for (MultiSensorMapping multiSensor : multiSensorMapping) {
                DEVICE_ID_TYPE_MAPPING_MAP.put(multiSensor.getId(), "multi_sensor");
            }
        }
        if (deviceIdDeviceTypeMapping.getSocketMapping() != null) {
            SocketMapping[] socketMapping = deviceIdDeviceTypeMapping.getSocketMapping();
            for (SocketMapping socket : socketMapping) {
                DEVICE_ID_TYPE_MAPPING_MAP.put(socket.getId(), "socket");
            }
        }
        if (deviceIdDeviceTypeMapping.getDoorWindowSensorMapping() != null) {
            DoorWindowSensorMapping[] doorWindowSensorMapping = deviceIdDeviceTypeMapping.getDoorWindowSensorMapping();
            for (DoorWindowSensorMapping doorWindowSensor : doorWindowSensorMapping) {
                DEVICE_ID_TYPE_MAPPING_MAP.put(doorWindowSensor.getId(), "door");
            }
        }
        DEVICE_ID_TYPE_MAPPING_MAP.forEach((k, v) -> System.out.println("Key: " + k + ", Value: " + v));
    }

}
