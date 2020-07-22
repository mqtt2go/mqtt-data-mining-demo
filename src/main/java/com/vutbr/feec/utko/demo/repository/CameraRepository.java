package com.vutbr.feec.utko.demo.repository;

import com.vutbr.feec.utko.demo.entities.CameraEntity;
import org.springframework.jdbc.core.JdbcTemplate;

public class CameraRepository {

    private JdbcTemplate jdbcTemplate;

    public CameraRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveCamera(CameraEntity cameraEntity) {
        jdbcTemplate.update(
                "INSERT INTO camera (user_in_home, state, record_timestamp, group_id, device_id, home_id, gateway_id) VALUES (?, ?, ?, ?, ?, ?, ?)",
                cameraEntity.isUserInHome(),
                cameraEntity.getState(),
                cameraEntity.getRecordTimestamp(),
                cameraEntity.getGroupId(),
                cameraEntity.getDeviceId(),
                cameraEntity.getHomeId(),
                cameraEntity.getGatewayId());
    }

}
