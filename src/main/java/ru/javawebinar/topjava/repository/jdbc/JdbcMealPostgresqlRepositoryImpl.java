package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;


@Repository
@Profile("postgres")
public class JdbcMealPostgresqlRepositoryImpl extends AbstractJdbcMealRepositoryImpl<LocalDateTime> {
    @Override
    protected LocalDateTime dateConvert(LocalDateTime dateTime) {
        return dateTime;
    }

    @Autowired
    public JdbcMealPostgresqlRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(dataSource, jdbcTemplate, namedParameterJdbcTemplate);
    }
}
