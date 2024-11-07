package id.sendistudio.spring.base.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import id.sendistudio.spring.base.app.utils.QueryUtil;
import id.sendistudio.spring.base.app.utils.TypeUtil;
import id.sendistudio.spring.base.data.requests.CreateAddressRequest;
import id.sendistudio.spring.base.data.requests.PaginationAddressRequest;
import id.sendistudio.spring.base.data.requests.UpdateAddressRequest;
import id.sendistudio.spring.base.data.views.AddressView;

@Repository
public class AddressRepository {
    @Autowired
    QueryUtil query;

    @Autowired
    TypeUtil typeUtil;

    public Boolean create(String uid, CreateAddressRequest request) {
        String sql = "INSERT INTO tbAddresses (uid, contactUid, street, city, province, country, postalCode) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int response = query.exec(
                sql, uid,
                request.getContactUid(),
                request.getStreet(),
                request.getCity(),
                request.getProvince(),
                request.getCountry(),
                request.getPostalCode());

        return response > 0;
    }

    public List<AddressView> gets(String country, String province, String city, String street) {
        String sql = "SELECT * FROM tbAddresses";

        List<Object> parameters = new ArrayList<>();

        if (country != null) {
            Boolean whereExist = sql.contains("WHERE");
            sql += whereExist ? " AND (country LIKE ?)" : " WHERE (country LIKE ?)";
            String searchPattern = "%" + country + "%";
            parameters.add(searchPattern);
            parameters.add(searchPattern);
        }

        if (province != null) {
            Boolean whereExist = sql.contains("WHERE");
            sql += whereExist ? " AND (province LIKE ?)" : " WHERE (province LIKE ?)";
            String searchPattern = "%" + province + "%";
            parameters.add(searchPattern);
        }

        if (city != null) {
            Boolean whereExist = sql.contains("WHERE");
            sql += whereExist ? " AND city LIKE ?" : " WHERE (city LIKE ?)";
            String searchPattern = "%" + city + "%";
            parameters.add(searchPattern);
        }

        if (street != null) {
            Boolean whereExist = sql.contains("WHERE");
            sql += whereExist ? " AND street LIKE ?" : " WHERE (street LIKE ?)";
            String searchPattern = "%" + street + "%";
            parameters.add(searchPattern);
        }

        return query.queryForList(sql, new AddressView.AddressViewRowMapper(), parameters.toArray());
    }

    public Optional<Integer> countTotalPagination(PaginationAddressRequest request) {
        String sql = "SELECT COUNT(*) as total FROM tbAddresses";
        List<Object> parameters = new ArrayList<>();

        if (request.getCountry() != null) {
            Boolean whereExist = sql.contains("WHERE");
            sql += whereExist ? " AND (country LIKE ?)" : " WHERE (country LIKE ?)";
            String searchPattern = "%" + request.getCountry() + "%";
            parameters.add(searchPattern);
            parameters.add(searchPattern);
        }

        if (request.getProvince() != null) {
            Boolean whereExist = sql.contains("WHERE");
            sql += whereExist ? " AND (province LIKE ?)" : " WHERE (province LIKE ?)";
            String searchPattern = "%" + request.getProvince() + "%";
            parameters.add(searchPattern);
        }

        if (request.getCity() != null) {
            Boolean whereExist = sql.contains("WHERE");
            sql += whereExist ? " AND city LIKE ?" : " WHERE (city LIKE ?)";
            String searchPattern = "%" + request.getCity() + "%";
            parameters.add(searchPattern);
        }

        if (request.getStreet() != null) {
            Boolean whereExist = sql.contains("WHERE");
            sql += whereExist ? " AND street LIKE ?" : " WHERE (street LIKE ?)";
            String searchPattern = "%" + request.getStreet() + "%";
            parameters.add(searchPattern);
        }

        TypeUtil.IntegerRowMapper integerRowMapper = new TypeUtil.IntegerRowMapper("total");
        return query.queryForObject(sql, integerRowMapper, parameters.toArray());
    }

    public PageImpl<AddressView> getPagination(PaginationAddressRequest request, Integer total) {
        String sql = "SELECT * FROM tbAddresses";

        List<Object> parameters = new ArrayList<>();

        if (request.getCountry() != null) {
            Boolean whereExist = sql.contains("WHERE");
            sql += whereExist ? " AND (country LIKE ?)" : " WHERE (country LIKE ?)";
            String searchPattern = "%" + request.getCountry() + "%";
            parameters.add(searchPattern);
            parameters.add(searchPattern);
        }

        if (request.getProvince() != null) {
            Boolean whereExist = sql.contains("WHERE");
            sql += whereExist ? " AND (province LIKE ?)" : " WHERE (province LIKE ?)";
            String searchPattern = "%" + request.getProvince() + "%";
            parameters.add(searchPattern);
        }

        if (request.getCity() != null) {
            Boolean whereExist = sql.contains("WHERE");
            sql += whereExist ? " AND city LIKE ?" : " WHERE (city LIKE ?)";
            String searchPattern = "%" + request.getCity() + "%";
            parameters.add(searchPattern);
        }

        if (request.getStreet() != null) {
            Boolean whereExist = sql.contains("WHERE");
            sql += whereExist ? " AND street LIKE ?" : " WHERE (street LIKE ?)";
            String searchPattern = "%" + request.getStreet() + "%";
            parameters.add(searchPattern);
        }

        Integer offset = typeUtil.getOffset(request.getPage(), request.getSize());

        sql += " ORDER BY createdAt DESC LIMIT ? OFFSET ?";
        parameters.add(request.getSize());
        parameters.add(offset);

        List<AddressView> addressViews = query.queryForList(
                sql,
                new AddressView.AddressViewRowMapper(),
                parameters.toArray());

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        return new PageImpl<AddressView>(addressViews, pageable, total);
    }

    public Optional<AddressView> getByUid(String uid) {
        String sql = "SELECT * FROM tbAddresses WHERE uid = ?";
        return query.queryForObject(sql, new AddressView.AddressViewRowMapper(), uid);
    }

    public Optional<AddressView> getByPhone(String phone) {
        String sql = "SELECT * FROM tbAddresses WHERE phone = ?";
        return query.queryForObject(sql, new AddressView.AddressViewRowMapper(), phone);
    }

    public Boolean updateData(String uid, UpdateAddressRequest request) {
        String sql = "UPDATE tbAddresses SET street = ?, city = ?, province = ?, country = ?, postalCode = ? WHERE uid = ?";
        int response = query.exec(
                sql,
                request.getStreet(),
                request.getCity(),
                request.getProvince(),
                request.getCountry(),
                request.getPostalCode(),
                uid);
        return response > 0;
    }

    public Boolean delete(String uid) {
        String sql = "DELETE FROM tbAddresses WHERE uid = ?";
        int response = query.exec(sql, uid);
        return response > 0;
    }
}
