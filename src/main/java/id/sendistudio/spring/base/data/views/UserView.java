package id.sendistudio.spring.base.data.views;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserView {

    private String username;

    private String name;

    private String token;

    private Long tokenExpiredAt;

    private String createdAt;

    private String updatedAt;

    public static class UserViewRowMapper implements RowMapper<UserView> {
        @Override
        public UserView mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
            UserView user = new UserView();
            user.setUsername(rs.getString("username"));
            user.setName(rs.getString("name"));
            user.setToken(rs.getString("token"));
            if (rs.getBigDecimal("tokenExpiredAt") != null) {
                user.setTokenExpiredAt(rs.getBigDecimal("tokenExpiredAt").longValue());
            }
            user.setCreatedAt(rs.getString("createdAt"));
            user.setUpdatedAt(rs.getString("updatedAt"));
            return user;
        }
    }
}
