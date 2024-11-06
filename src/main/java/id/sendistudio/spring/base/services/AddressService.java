package id.sendistudio.spring.base.services;

import java.util.Optional;

import id.sendistudio.spring.base.app.middlewares.responses.WebResponse;
import id.sendistudio.spring.base.data.requests.CreateAddressRequest;
import id.sendistudio.spring.base.data.requests.PaginationAddressRequest;
import id.sendistudio.spring.base.data.requests.UpdateAddressRequest;

public interface AddressService {
    WebResponse create(CreateAddressRequest request);

    WebResponse gets(Optional<String> uid, Optional<String> country, Optional<String> province, Optional<String> city,
            Optional<String> street);

    WebResponse getPagination(PaginationAddressRequest request);

    WebResponse updateData(String uid, UpdateAddressRequest request);

    WebResponse delete(String uid);
}
