package id.sendistudio.spring.base.services;

import id.sendistudio.spring.base.app.middlewares.responses.WebResponse;
import id.sendistudio.spring.base.data.requests.CreateAddressRequest;
import id.sendistudio.spring.base.data.requests.PaginationAddressRequest;
import id.sendistudio.spring.base.data.requests.UpdateAddressRequest;

public interface AddressService {
    WebResponse create(CreateAddressRequest request);

    WebResponse gets(String uid, String country, String province, String city, String street);

    WebResponse getPagination(PaginationAddressRequest request);

    WebResponse updateData(String uid, UpdateAddressRequest request);

    WebResponse delete(String uid);
}
