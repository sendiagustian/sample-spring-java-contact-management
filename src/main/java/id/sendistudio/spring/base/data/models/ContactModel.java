package id.sendistudio.spring.base.data.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ContactModel {

    private String uid;

    private String username;

    private String fistName;

    private String lastName;

    private String phone;

    private String email;

    private Date createdAt;

    private Date updatedAt;

    public static class ContactRowMapper implements RowMapper<ContactModel> {

        @Override
        public ContactModel mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
            ContactModel contact = new ContactModel();
            contact.setUid(rs.getString("uid"));
            contact.setUsername(rs.getString("username"));
            contact.setFistName(rs.getString("firstName"));
            contact.setLastName(rs.getString("lastName"));
            contact.setPhone(rs.getString("phone"));
            contact.setEmail(rs.getString("email"));
            contact.setCreatedAt(rs.getDate("createdAt"));
            contact.setUpdatedAt(rs.getDate("updatedAt"));
            return contact;
        }

    }
}
