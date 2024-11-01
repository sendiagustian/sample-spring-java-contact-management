package id.sendistudio.spring.base.app.utils;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class QueryUtil {
    private final JdbcTemplate jdbcTemplate;

    // Generic method to execute a query for a list of models
    public <T> List<T> queryForList(String sql, RowMapper<T> rowMapper, Object... args) {
        return jdbcTemplate.query(sql, rowMapper, args);
    }

    // Generic method to execute a query for a single model
    public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... args) {
        return jdbcTemplate.queryForObject(sql, rowMapper, args);
    }

    // Generic method to execute an insert/update/delete operation
    public int exec(String sql, Object... args) {
        return jdbcTemplate.update(sql, args);
    }

    // Generic method to execute batch updates
    public int[] batchExec(String sql, List<Object[]> batchArgs) {
        return jdbcTemplate.batchUpdate(sql, batchArgs);
    }
}
