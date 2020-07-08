package com.vutbr.feec.utko.demo.data;

import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

public class LightDemoRepository {
    
    private JdbcTemplate jdbcTemplate;

    public LightDemoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * <pre>
     * <code>
     * SELECT COUNT(*) FROM light_demo WHERE light_demo.timestamp_record > DATE_SUB(NOW(),INTERVAL 3 HOUR) AND (state IS NOT NULL AND state <> '')
     * </code>
     * </pre>
     *
     * @param lightDemoId
     * @return
     */
    public boolean findActionInTheLastHour(String lightDemoId, Integer numberOfOnStatesRecords) {
        Integer foundRecords = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM light_demo WHERE light_demo.timestamp_record > DATE_SUB(NOW(),INTERVAL 1 HOUR) AND (state IS NOT NULL AND state <> '')",
                Integer.class);
        if (foundRecords > numberOfOnStatesRecords) {
            return true;
        } else {
            return false;
        }
    }

    public void storeSettings(String lightDemoId, String state, LocalDateTime recordTimestamp) {
        jdbcTemplate.update(
                "INSERT INTO light_demo (timestamp_record, state, light_id) VALUES (?, ?, ?)",
                recordTimestamp, state, lightDemoId);
    }
}
