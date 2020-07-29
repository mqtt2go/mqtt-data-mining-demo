package com.vutbr.feec.utko.demo.repository;

import com.vutbr.feec.utko.demo.entities.LightEntity;
import com.vutbr.feec.utko.demo.entities.LightLastSettingsEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

public class LightRepository {

    private JdbcTemplate jdbcTemplate;

    public LightRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void saveLight(LightEntity lightEntity) {
        jdbcTemplate.update(
                "INSERT INTO light (user_in_home, state, record_timestamp, group_id, device_id, home_id, gateway_id) VALUES (?, ?, ?, ?, ?, ?, ?)",
                lightEntity.isUserInHome(),
                lightEntity.getState(),
                lightEntity.getRecordTimestamp(),
                lightEntity.getGroupId(),
                lightEntity.getDeviceId(),
                lightEntity.getHomeId(),
                lightEntity.getGatewayId());
    }
    
    public void saveLightLastSettings(LightLastSettingsEntity lightLastSettingsEntity) {
        jdbcTemplate.update(
                "INSERT INTO light_last_settings (user_in_home, state, record_timestamp, group_id, device_id, home_id, gateway_id) VALUES (?, ?, ?, ?, ?, ?, ?)",
                lightLastSettingsEntity.isUserInHome(),
                lightLastSettingsEntity.getState(),
                lightLastSettingsEntity.getRecordTimestamp(),
                lightLastSettingsEntity.getGroupId(),
                lightLastSettingsEntity.getDeviceId(),
                lightLastSettingsEntity.getHomeId(),
                lightLastSettingsEntity.getGatewayId());
    }

    public void updateLightLastSettings(LightLastSettingsEntity lightLastSettingsEntity) {
        jdbcTemplate.update(
                "UPDATE light_last_settings SET user_in_home = ?, state = ?, record_timestamp = ?, group_id = ?, device_id = ?, home_id = ?, gateway_id = ? WHERE device_id = ? AND home_id = ? AND gateway_id = ?",
                lightLastSettingsEntity.isUserInHome(),
                lightLastSettingsEntity.getState(),
                lightLastSettingsEntity.getRecordTimestamp(),
                lightLastSettingsEntity.getGroupId(),
                lightLastSettingsEntity.getDeviceId(),
                lightLastSettingsEntity.getHomeId(),
                lightLastSettingsEntity.getGatewayId(),
                lightLastSettingsEntity.getDeviceId(),
                lightLastSettingsEntity.getHomeId(),
                lightLastSettingsEntity.getGatewayId());
    }

    public Optional<LightLastSettingsEntity> findLightSettingsByHomeIdGatewayIdDeviceId(String homeId,
                                                                                        String gatewayId,
                                                                                        String deviceId) {
        LightLastSettingsEntity lightLastSettingsEntity =
                jdbcTemplate.queryForObject("SELECT * FROM light_last_settings WHERE home_id = ? AND gateway_id = ? AND device_id = ?",
                        new Object[]{homeId, gatewayId, deviceId},
                        (rs, rowNum) ->
                                new LightLastSettingsEntity(
                                        rs.getLong("id"),
                                        rs.getBoolean("user_in_home"),
                                        rs.getString("state"),
                                        rs.getTimestamp("record_timestamp"),
                                        rs.getString("group_id"),
                                        rs.getString("device_id"),
                                        rs.getString("home_id"),
                                        rs.getString("gateway_id")
                                ));
        return Optional.ofNullable(lightLastSettingsEntity);
    }

    /**
     * <pre>
     * <code>
     * SELECT COUNT(*) FROM light WHERE light.record_timestamp > DATE_SUB(NOW(),INTERVAL 15 second) AND (state IS NOT NULL AND state <> '' AND state LIKE 'on') AND device_id = 'sb1'
     * </code>
     * </pre>
     *
     * @return
     */
    public boolean findSb1OnAnomaly() {
        Integer foundRecords = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM light WHERE light.record_timestamp > DATE_SUB(NOW(),INTERVAL 15 second) AND (state IS NOT NULL AND state <> '' AND state LIKE 'on') AND device_id = 'sb1'",
                Integer.class);
        if (foundRecords > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean findSb1OffAnomaly() {
        Integer foundRecords = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM light WHERE light.record_timestamp > DATE_SUB(NOW(),INTERVAL 15 second) AND (state IS NOT NULL AND state <> '' AND state LIKE 'off') AND device_id = 'sb1'",
                Integer.class);
        if (foundRecords > 0) {
            return true;
        } else {
            return false;
        }
    }

}
