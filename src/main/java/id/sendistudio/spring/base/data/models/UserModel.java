package id.sendistudio.spring.base.data.models;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class UserModel {

    private String username;
    private String password;
    private String name;
    private String token;
    private BigInteger tokenExpiredAt;
    private String createdAt;
    private String updatedAt;

    public static class UserRowMapper implements RowMapper<UserModel> {
        @Override
        public UserModel mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
            UserModel user = new UserModel();
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setName(rs.getString("name"));
            user.setToken(rs.getString("token"));
            if (rs.getBigDecimal("tokenExpiredAt") != null) {
                user.setTokenExpiredAt(rs.getBigDecimal("tokenExpiredAt").toBigInteger());
            }
            user.setCreatedAt(rs.getString("createdAt"));
            user.setUpdatedAt(rs.getString("updatedAt"));
            return user;
        }
    }
}
