package com.example.demodigitalocean;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@RestController
public class DbController {
    private final JdbcTemplate jdbc;

    public DbController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @GetMapping("/db/now")
    public String now() {
        OffsetDateTime ts = jdbc.queryForObject("select now()", OffsetDateTime.class);
        return "DB time: " + ts;
    }
}
