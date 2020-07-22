package com.vutbr.feec.utko.demo.repository;

import com.vutbr.feec.utko.demo.entities.MultiSensorLastSettingsEntity;
import com.vutbr.feec.utko.demo.entities.SocketEntity;
import com.vutbr.feec.utko.demo.entities.SocketLastSettingsEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

public class SocketRepository {

    private JdbcTemplate jdbcTemplate;

    public SocketRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveSocket(SocketEntity socketEntity) {
        jdbcTemplate.update(
                "INSERT INTO socket (user_in_home, state, consumption, ccurrent, voltage, power, record_timestamp, group_id, device_id, home_id, gateway_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                socketEntity.isUserInHome(),
                socketEntity.getState(),
                socketEntity.getConsumption(),
                socketEntity.getCurrent(),
                socketEntity.getVoltage(),
                socketEntity.getPower(),
                socketEntity.getRecordTimestamp(),
                socketEntity.getGroupId(),
                socketEntity.getDeviceId(),
                socketEntity.getHomeId(),
                socketEntity.getGatewayId());
    }

    public void saveSocketLastSettings(SocketLastSettingsEntity socketLastSettingsEntity) {
        jdbcTemplate.update(
                "INSERT INTO socket_last_settings (user_in_home, state, consumption, ccurrent, voltage, power, record_timestamp, group_id, device_id, home_id, gateway_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                socketLastSettingsEntity.isUserInHome(),
                socketLastSettingsEntity.getState(),
                socketLastSettingsEntity.getConsumption(),
                socketLastSettingsEntity.getCurrent(),
                socketLastSettingsEntity.getVoltage(),
                socketLastSettingsEntity.getPower(),
                socketLastSettingsEntity.getRecordTimestamp(),
                socketLastSettingsEntity.getGroupId(),
                socketLastSettingsEntity.getDeviceId(),
                socketLastSettingsEntity.getHomeId(),
                socketLastSettingsEntity.getGatewayId());
    }

    public void updateMultiSensorLastSettings(MultiSensorLastSettingsEntity multiSensorEntity) {
        jdbcTemplate.update(
                "UPDATE multi_sensor_last_settings SET user_in_home = ?, state = ?, consumption = ?, ccurrent = ?, voltage = ?, power = ?, record_timestamp = ?, group_id = ?, device_id = ?, home_id = ?, gateway_id = ? WHERE device_id = ? AND home_id = ? AND gateway_id = ?",
                multiSensorEntity.isUserInHome(),
                multiSensorEntity.getState(),
                multiSensorEntity.getTemperature(),
                multiSensorEntity.getHumidity(),
                multiSensorEntity.getMotion(),
                multiSensorEntity.getSmoke(),
                multiSensorEntity.getWater(),
                multiSensorEntity.getRecordTimestamp(),
                multiSensorEntity.getGroupId(),
                multiSensorEntity.getDeviceId(),
                multiSensorEntity.getHomeId(),
                multiSensorEntity.getGatewayId(),
                multiSensorEntity.getDeviceId(),
                multiSensorEntity.getHomeId(),
                multiSensorEntity.getGatewayId());
    }


    public Optional<SocketLastSettingsEntity> findSocketSettingsByHomeIdGatewayIdDeviceId(String homeId,
                                                                                          String gatewayId,
                                                                                          String deviceId) {
        SocketLastSettingsEntity socketLastSettingsEntity =
                jdbcTemplate.queryForObject("SELECT * FROM socket_last_settings WHERE home_id = ? AND gateway_id = ? AND device_id = ?",
                        new Object[]{homeId, gatewayId, deviceId},
                        (rs, rowNum) ->
                                new SocketLastSettingsEntity(
                                        rs.getLong("id"),
                                        rs.getBoolean("user_in_home"),
                                        rs.getString("state"),
                                        rs.getBigDecimal("consumption"),
                                        rs.getBigDecimal("ccurrent"),
                                        rs.getBigDecimal("voltage"),
                                        rs.getBigDecimal("power"),
                                        rs.getTimestamp("record_timestamp"),
                                        rs.getString("group_id"),
                                        rs.getString("device_id"),
                                        rs.getString("home_id"),
                                        rs.getString("gateway_id")
                                ));
        return Optional.ofNullable(socketLastSettingsEntity);
    }
}
