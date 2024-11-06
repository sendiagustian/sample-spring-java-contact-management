package id.sendistudio.spring.base.services;

import java.util.Optional;

import id.sendistudio.spring.base.app.middlewares.responses.WebResponse;
import id.sendistudio.spring.base.data.requests.ContactRequest;
import id.sendistudio.spring.base.data.requests.PaginationContactRequest;

public interface ContactService {
    WebResponse create(ContactRequest request);

    WebResponse gets(Optional<String> uid, Optional<String> phone, Optional<String> name);

    WebResponse getPagination(PaginationContactRequest request);

    WebResponse updateData(String username, ContactRequest request);

    WebResponse deleteByUid(String uid);

    WebResponse deleteByPhone(String phone);
}
