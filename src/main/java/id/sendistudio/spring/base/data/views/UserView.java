package id.sendistudio.spring.base.data.views;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserView {

    private String username;

    private String name;

    @Nullable
    private String token;

    private String createdAt;

    private String updatedAt;

    public static class UserViewRowMapper implements RowMapper<UserView> {
        @Override
        public UserView mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
            UserView user = new UserView();
            user.setUsername(rs.getString("username"));
            user.setName(rs.getString("name"));
            user.setToken(rs.getString("token"));
            user.setCreatedAt(rs.getString("createdAt"));
            user.setUpdatedAt(rs.getString("updatedAt"));
            return user;
        }
    }
}
