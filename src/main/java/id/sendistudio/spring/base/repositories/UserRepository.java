package id.sendistudio.spring.base.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import id.sendistudio.spring.base.app.utils.QueryUtil;
import id.sendistudio.spring.base.app.utils.TypeUtil;
import id.sendistudio.spring.base.data.requests.RegisterRequest;
import id.sendistudio.spring.base.data.requests.UpdateUserRequest;
import id.sendistudio.spring.base.data.views.UserView;

@Repository
public class UserRepository {

    @Autowired
    QueryUtil query;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TypeUtil typeUtil;

    public Boolean create(RegisterRequest request) {
        String sql = "INSERT INTO tbUsers (username, password, name) VALUES (?, ?, ?)";
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        int response = query.exec(sql, request.getUsername(), request.getPassword(), request.getName());
        return response > 0;
    }

    public String getPasswordByUsername(String username) {
        String sql = "SELECT password FROM tbUsers WHERE username = ?";
        TypeUtil.StringRowMapper stringRowMapper = new TypeUtil.StringRowMapper("password");
        var data = query.queryForObject(sql, stringRowMapper, username);
        return data;
    }

    public List<UserView> getAll() {
        String sql = "SELECT * FROM tbUsers";
        return query.queryForList(sql, new UserView.UserViewRowMapper());
    }

    public UserView getByUsername(String username) {
        String sql = "SELECT * FROM tbUsers WHERE username = ?";
        return query.queryForObject(sql, new UserView.UserViewRowMapper(), username);
    }

    public Boolean updateToken(String username, String token, Date tokenExpiredAt) {
        String sql = "UPDATE tbUsers SET token = ?, tokenExpiredAt = ? WHERE username = ?";
        Long timestamLong = tokenExpiredAt == null ? null : tokenExpiredAt.getTime();
        int response = query.exec(sql, token, timestamLong, username);
        return response > 0;
    }

    public Boolean updateData(UpdateUserRequest request) {
        String sql = "UPDATE tbUsers SET name = ? WHERE username = ?";
        int response = query.exec(sql, request.getName(), request.getUsername());
        return response > 0;
    }

    public Boolean deleteByUsername(String username) {
        String sql = "DELETE FROM tbUsers WHERE username = ?";
        int response = query.exec(sql, username);
        return response > 0;
    }

}
