package com.vutbr.feec.utko.demo.utils.mappings;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceIdDeviceTypeMapping {

    @JsonProperty("multi_sensor")
    private MultiSensorMapping[] multiSensorMapping;
    @JsonProperty("socket")
    private SocketMapping[] socketMapping;
    @JsonProperty("light")
    private LightMapping[] lightMapping;
    @JsonProperty("camera")
    private CameraMapping[] cameraMapping;
    @JsonProperty("door")
    private DoorWindowSensorMapping[] doorWindowSensorMapping;

    public DeviceIdDeviceTypeMapping() {
    }

    public MultiSensorMapping[] getMultiSensorMapping() {
        return multiSensorMapping;
    }

    public DeviceIdDeviceTypeMapping(MultiSensorMapping[] multiSensorMapping, SocketMapping[] socketMapping, LightMapping[] lightMapping, CameraMapping[] cameraMapping) {
        this.multiSensorMapping = multiSensorMapping;
        this.socketMapping = socketMapping;
        this.lightMapping = lightMapping;
        this.cameraMapping = cameraMapping;
    }

    public void setMultiSensorMapping(MultiSensorMapping[] multiSensorMapping) {
        this.multiSensorMapping = multiSensorMapping;
    }

    public SocketMapping[] getSocketMapping() {
        return socketMapping;
    }

    public void setSocketMapping(SocketMapping[] socketMapping) {
        this.socketMapping = socketMapping;
    }

    public LightMapping[] getLightMapping() {
        return lightMapping;
    }

    public void setLightMapping(LightMapping[] lightMapping) {
        this.lightMapping = lightMapping;
    }

    public CameraMapping[] getCameraMapping() {
        return cameraMapping;
    }

    public void setCameraMapping(CameraMapping[] cameraMapping) {
        this.cameraMapping = cameraMapping;
    }

    public DoorWindowSensorMapping[] getDoorWindowSensorMapping() {
        return doorWindowSensorMapping;
    }

    public void setDoorWindowSensorMapping(DoorWindowSensorMapping[] doorWindowSensorMapping) {
        this.doorWindowSensorMapping = doorWindowSensorMapping;
    }
}
