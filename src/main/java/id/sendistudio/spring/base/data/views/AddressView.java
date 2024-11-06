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
public class AddressView {
    private String uid;

    private String street;

    private String city;

    private String province;

    private String country;

    private String postalCode;

    private Date createdAt;

    private Date updatedAt;

    public static class AddressViewRowMapper implements RowMapper<AddressView> {

        @Override
        public AddressView mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
            AddressView address = new AddressView();
            address.setUid(rs.getString("uid"));
            address.setStreet(rs.getString("street"));
            address.setCity(rs.getString("city"));
            address.setProvince(rs.getString("province"));
            address.setCountry(rs.getString("country"));
            address.setPostalCode(rs.getString("postalCode"));
            address.setCreatedAt(rs.getDate("createdAt"));
            address.setUpdatedAt(rs.getDate("updatedAt"));
            return address;
        }
    }
}
