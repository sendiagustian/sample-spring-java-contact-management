package id.sendistudio.spring.base.data.views;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactView {

    private String uid;

    private String fistName;

    private String lastName;

    private String phone;

    private String email;

    private Date createdAt;

    private Date updatedAt;

    public static class ContactViewRowMapper implements RowMapper<ContactView> {

        @Override
        public ContactView mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
            ContactView contact = new ContactView();
            contact.setUid(rs.getString("uid"));
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
