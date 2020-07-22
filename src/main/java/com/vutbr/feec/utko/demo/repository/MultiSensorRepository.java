package com.vutbr.feec.utko.demo.repository;

import com.vutbr.feec.utko.demo.entities.MultiSensorEntity;
import com.vutbr.feec.utko.demo.entities.MultiSensorLastSettingsEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

public class MultiSensorRepository {

    private JdbcTemplate jdbcTemplate;

    public MultiSensorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void saveMultiSensor(MultiSensorEntity multiSensorEntity) {
        jdbcTemplate.update(
                "INSERT INTO multi_sensor (user_in_home, state, temperature, temperature_unit, humidity, humidity_unit, motion, smoke, water, record_timestamp, group_id, device_id, home_id, gateway_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                multiSensorEntity.isUserInHome(),
                multiSensorEntity.getState(),
                multiSensorEntity.getTemperature(),
                multiSensorEntity.getTemperatureUnit(),
                multiSensorEntity.getHumidity(),
                multiSensorEntity.getHumidityUnit(),
                multiSensorEntity.getMotion(),
                multiSensorEntity.getSmoke(),
                multiSensorEntity.getWater(),
                multiSensorEntity.getRecordTimestamp(),
                multiSensorEntity.getGroupId(),
                multiSensorEntity.getDeviceId(),
                multiSensorEntity.getHomeId(),
                multiSensorEntity.getGatewayId());
    }

    public void saveMultiSensorLastSettings(MultiSensorLastSettingsEntity multiSensorEntity) {
        jdbcTemplate.update(
                "INSERT INTO multi_sensor_last_settings (user_in_home, state, temperature, temperature_unit, humidity, humidity_unit, motion, smoke, water, record_timestamp, group_id, device_id, home_id, gateway_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                multiSensorEntity.isUserInHome(),
                multiSensorEntity.getState(),
                multiSensorEntity.getTemperature(),
                multiSensorEntity.getTemperatureUnit(),
                multiSensorEntity.getHumidity(),
                multiSensorEntity.getHumidityUnit(),
                multiSensorEntity.getMotion(),
                multiSensorEntity.getSmoke(),
                multiSensorEntity.getWater(),
                multiSensorEntity.getRecordTimestamp(),
                multiSensorEntity.getGroupId(),
                multiSensorEntity.getDeviceId(),
                multiSensorEntity.getHomeId(),
                multiSensorEntity.getGatewayId());
    }

    public void updateMultiSensorLastSettings(MultiSensorLastSettingsEntity multiSensorEntity) {
        jdbcTemplate.update(
                "UPDATE multi_sensor_last_settings SET user_in_home = ?, state = ?, temperature = ?, temperature_unit = ?, humidity = ?, humidity_unit = ?, motion = ?, smoke = ?, water = ?, record_timestamp = ?, group_id = ?, device_id = ?, home_id = ?, gateway_id = ? WHERE device_id = ? AND home_id = ? AND gateway_id = ?",
                multiSensorEntity.isUserInHome(),
                multiSensorEntity.getState(),
                multiSensorEntity.getTemperature(),
                multiSensorEntity.getTemperatureUnit(),
                multiSensorEntity.getHumidity(),
                multiSensorEntity.getHumidityUnit(),
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

    public Optional<MultiSensorLastSettingsEntity> findMultiSensorSettingsByHomeIdGatewayIdDeviceId(String homeId,
                                                                                                    String gatewayId,
                                                                                                    String deviceId) {
        MultiSensorLastSettingsEntity multiSensorLastSettingsEntity =
                jdbcTemplate.queryForObject("SELECT * FROM multi_sensor_last_settings WHERE home_id = ? AND gateway_id = ? AND device_id = ?",
                        new Object[]{homeId, gatewayId, deviceId},
                        (rs, rowNum) ->
                                new MultiSensorLastSettingsEntity(
                                        rs.getLong("id"),
                                        rs.getBoolean("user_in_home"),
                                        rs.getString("state"),
                                        rs.getBigDecimal("temperature"),
                                        rs.getString("temperature_unit"),
                                        rs.getBigDecimal("humidity"),
                                        rs.getString("humidity_unit"),
                                        rs.getString("motion"),
                                        rs.getString("smoke"),
                                        rs.getString("water"),
                                        rs.getTimestamp("record_timestamp"),
                                        rs.getString("group_id"),
                                        rs.getString("device_id"),
                                        rs.getString("home_id"),
                                        rs.getString("gateway_id")
                                ));
        return Optional.ofNullable(multiSensorLastSettingsEntity);
    }
}
