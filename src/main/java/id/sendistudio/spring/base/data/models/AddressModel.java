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
@Setter
@Getter
public class AddressModel {
    private String uid;

    private String contactUid;

    private String street;

    private String city;

    private String province;

    private String country;

    private String postalCode;

    private Date createdAt;

    private Date updatedAt;

    public static class AddressRowMapper implements RowMapper<AddressModel> {

        @Override
        public AddressModel mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
            AddressModel address = new AddressModel();
            address.setUid(rs.getString("uid"));
            address.setContactUid(rs.getString("contactUid"));
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
