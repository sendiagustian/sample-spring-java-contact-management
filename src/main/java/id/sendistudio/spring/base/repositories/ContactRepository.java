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
import id.sendistudio.spring.base.data.requests.ContactRequest;
import id.sendistudio.spring.base.data.requests.PaginationContactRequest;
import id.sendistudio.spring.base.data.views.ContactView;

@Repository
public class ContactRepository {
    @Autowired
    QueryUtil query;

    @Autowired
    TypeUtil typeUtil;

    public Boolean create(String uid, String username, ContactRequest request) {
        String sql = "INSERT INTO tbContacts (uid, username, firstName, lastName, phone, email) VALUES (?, ?, ?, ?, ?, ?)";
        int response = query.exec(sql, uid, username, request.getFirstName(), request.getLastName(), request.getPhone(),
                request.getEmail());
        return response > 0;
    }

    public List<ContactView> gets(String name) {
        String sql = "SELECT * FROM tbContacts";

        List<Object> parameters = new ArrayList<>();

        if (name != null) {
            sql += " WHERE firstName LIKE ? OR lastName LIKE ?";
            String searchPattern = "%" + name + "%";
            parameters.add(searchPattern);
            parameters.add(searchPattern);
        }

        return query.queryForList(sql, new ContactView.ContactViewRowMapper(), parameters.toArray());
    }

    public Optional<Integer> countTotalPagination(PaginationContactRequest request) {
        String sql = "SELECT COUNT(*) as total FROM tbContacts";
        List<Object> parameters = new ArrayList<>();

        if (request.getName() != null) {
            Boolean whereExist = sql.contains("WHERE");
            sql += whereExist ? " AND (firstName LIKE ? OR lastName LIKE ?)"
                    : " WHERE (firstName LIKE ? OR lastName LIKE ?)";
            String searchPattern = "%" + request.getName() + "%";
            parameters.add(searchPattern);
            parameters.add(searchPattern);
        }

        if (request.getEmail() != null) {
            Boolean whereExist = sql.contains("WHERE");
            sql += whereExist ? " AND (email LIKE ?)" : " WHERE (email LIKE ?)";
            String searchPattern = "%" + request.getEmail() + "%";
            parameters.add(searchPattern);
        }

        if (request.getPhone() != null) {
            Boolean whereExist = sql.contains("WHERE");
            sql += whereExist ? " AND phone LIKE ?" : " WHERE (phone LIKE ?)";
            String searchPattern = "%" + request.getPhone() + "%";
            parameters.add(searchPattern);
        }

        TypeUtil.IntegerRowMapper integerRowMapper = new TypeUtil.IntegerRowMapper("total");
        return query.queryForObject(sql, integerRowMapper, parameters.toArray());
    }

    public PageImpl<ContactView> getPagination(PaginationContactRequest request, Integer total) {
        String sql = "SELECT * FROM tbContacts";

        List<Object> parameters = new ArrayList<>();

        if (request.getName() != null) {
            Boolean whereExist = sql.contains("WHERE");
            sql += whereExist ? " AND (firstName LIKE ? OR lastName LIKE ?)"
                    : " WHERE (firstName LIKE ? OR lastName LIKE ?)";
            String searchPattern = "%" + request.getName() + "%";
            parameters.add(searchPattern);
            parameters.add(searchPattern);
        }

        if (request.getEmail() != null) {
            Boolean whereExist = sql.contains("WHERE");
            sql += whereExist ? " AND (email LIKE ?)" : " WHERE (email LIKE ?)";
            String searchPattern = "%" + request.getEmail() + "%";
            parameters.add(searchPattern);
        }

        if (request.getPhone() != null) {
            Boolean whereExist = sql.contains("WHERE");
            sql += whereExist ? " AND phone LIKE ?" : " WHERE (phone LIKE ?)";
            String searchPattern = "%" + request.getPhone() + "%";
            parameters.add(searchPattern);
        }

        Integer offset = typeUtil.getOffset(request.getPage(), request.getSize());

        sql += " ORDER BY createdAt DESC LIMIT ? OFFSET ?";
        parameters.add(request.getSize());
        parameters.add(offset);

        List<ContactView> contactViews = query.queryForList(
                sql,
                new ContactView.ContactViewRowMapper(),
                parameters.toArray());

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        return new PageImpl<ContactView>(contactViews, pageable, total);
    }

    public Optional<ContactView> getByUid(String uid) {
        String sql = "SELECT * FROM tbContacts WHERE uid = ?";
        return query.queryForObject(sql, new ContactView.ContactViewRowMapper(), uid);
    }

    public Optional<ContactView> getByPhone(String phone) {
        String sql = "SELECT * FROM tbContacts WHERE phone = ?";
        return query.queryForObject(sql, new ContactView.ContactViewRowMapper(), phone);
    }

    public Boolean updateData(String uid, ContactRequest request) {
        String sql = "UPDATE tbContacts SET firstName = ?, lastName = ?, phone = ?, email = ? WHERE uid = ?";
        int response = query.exec(sql, request.getFirstName(), request.getLastName(), request.getPhone(),
                request.getEmail(), uid);
        return response > 0;
    }

    public Boolean deleteByUid(String uid) {
        String sql = "DELETE FROM tbContacts WHERE uid = ?";
        int response = query.exec(sql, uid);
        return response > 0;
    }

    public Boolean deleteByPhone(String phone) {
        String sql = "DELETE FROM tbContacts WHERE phone = ?";
        int response = query.exec(sql, phone);
        return response > 0;
    }
}
