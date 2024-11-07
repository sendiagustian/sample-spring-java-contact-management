package id.sendistudio.spring.base.data.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel implements UserDetails {

    private String username;

    private String password;

    private String name;

    @Nullable
    private String token;

    private String createdAt;

    private String updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(username != null ? username : "ADMIN"));
    }

    public static class UserRowMapper implements RowMapper<UserModel> {
        @Override
        public UserModel mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
            UserModel user = new UserModel();
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setName(rs.getString("name"));
            user.setToken(rs.getString("token"));
            user.setCreatedAt(rs.getString("createdAt"));
            user.setUpdatedAt(rs.getString("updatedAt"));
            return user;
        }
    }

}
