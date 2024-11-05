package id.sendistudio.spring.base.data.views;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserView {

    private String username;

    private String name;

    private Optional<String> token;

    private String createdAt;

    private String updatedAt;

    public static class UserViewRowMapper implements RowMapper<UserView> {
        @Override
        public UserView mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
            UserView user = new UserView();
            user.setUsername(rs.getString("username"));
            user.setName(rs.getString("name"));
            user.setToken(Optional.ofNullable(rs.getString("token")));
            user.setCreatedAt(rs.getString("createdAt"));
            user.setUpdatedAt(rs.getString("updatedAt"));
            return user;
        }
    }
}
