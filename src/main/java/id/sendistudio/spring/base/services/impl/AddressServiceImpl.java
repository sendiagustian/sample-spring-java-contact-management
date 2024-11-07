package id.sendistudio.spring.base.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import id.sendistudio.spring.base.app.middlewares.responses.DataPaginationResponse;
import id.sendistudio.spring.base.app.middlewares.responses.DataResponse;
import id.sendistudio.spring.base.app.middlewares.responses.ErrorResponse;
import id.sendistudio.spring.base.app.middlewares.responses.MessageResponse;
import id.sendistudio.spring.base.app.middlewares.responses.WebResponse;
import id.sendistudio.spring.base.app.utils.ErrorUtil;
import id.sendistudio.spring.base.app.utils.TypeUtil;
import id.sendistudio.spring.base.app.utils.ValidatorUtil;
import id.sendistudio.spring.base.data.requests.CreateAddressRequest;
import id.sendistudio.spring.base.data.requests.PaginationAddressRequest;
import id.sendistudio.spring.base.data.requests.UpdateAddressRequest;
import id.sendistudio.spring.base.data.views.AddressView;
import id.sendistudio.spring.base.data.views.PagingView;
import id.sendistudio.spring.base.repositories.AddressRepository;
import id.sendistudio.spring.base.services.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    ValidatorUtil validator;

    @Autowired
    ErrorUtil errorHandler;

    @Autowired
    TypeUtil typeUtil;

    @Autowired
    AddressRepository addressRepository;

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "addressCache", allEntries = true),
            @CacheEvict(value = "addressPaginationCache", allEntries = true),
    })
    public WebResponse create(CreateAddressRequest request) {
        try {
            validator.validate(request);

            String uid = typeUtil.genereateUUID();

            Boolean result = addressRepository.create(uid, request);

            if (result) {
                return new DataResponse<String>(200, uid);
            } else {
                return new ErrorResponse(500, "Failed create address");
            }

        } catch (DataAccessException e) {
            return errorHandler.errorData(e);
        } catch (Exception e) {
            return errorHandler.errorServer(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "addressCache", unless = "#result == null or #result.status != 200")
    public WebResponse gets(String uid, String country, String province, String city, String street) {

        try {

            if (uid != null && (country != null || province != null || city != null || street != null)) {
                return new ErrorResponse(400, "Invalid request can't use uid with other parameter");
            } else {
                if (uid != null) {
                    AddressView result = addressRepository.getByUid(uid).orElse(null);

                    if (result == null) {
                        return new ErrorResponse(404, "Address not found");
                    }

                    return new DataResponse<AddressView>(200, result);
                } else {
                    List<AddressView> results = addressRepository.gets(country, province, city, street);
                    return new DataResponse<List<AddressView>>(200, results);
                }
            }
        } catch (DataAccessException e) {
            return errorHandler.errorNotFound(e);
        } catch (Exception e) {
            return errorHandler.errorServer(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "addressPaginationCache", key = "#request", unless = "#result == null or #result.status != 200")
    public WebResponse getPagination(PaginationAddressRequest request) {
        try {
            Integer total = addressRepository.countTotalPagination(request).orElse(null);
            Page<AddressView> results = addressRepository.getPagination(request, total);

            PagingView paging = new PagingView();
            paging.setSize(results.getSize());
            paging.setTotalData(total);
            paging.setCurrentPage(results.getNumber());
            paging.setTotalPage(results.getTotalPages());

            return new DataPaginationResponse<List<AddressView>>(200, results.getContent(), paging);
        } catch (DataAccessException e) {
            return errorHandler.errorNotFound(e);
        } catch (Exception e) {
            return errorHandler.errorServer(e);
        }
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "addressCache", allEntries = true),
            @CacheEvict(value = "addressPaginationCache", allEntries = true),
    })
    public WebResponse updateData(String uid, UpdateAddressRequest request) {
        try {
            Boolean result = addressRepository.updateData(uid, request);

            if (result) {
                return new MessageResponse(200, "Success update address");
            } else {
                return new ErrorResponse(500, "Failed update address");
            }
        } catch (DataAccessException e) {
            return errorHandler.errorData(e);
        } catch (Exception e) {
            return errorHandler.errorServer(e);
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "addressCache", allEntries = true),
            @CacheEvict(value = "addressPaginationCache", allEntries = true),
    })
    public WebResponse delete(String uid) {
        try {
            Boolean result = addressRepository.delete(uid);

            if (result) {
                return new MessageResponse(200, "Success delete address");
            } else {
                return new ErrorResponse(500, "Failed delete address");
            }

        } catch (DataAccessException e) {
            return errorHandler.errorData(e);
        } catch (Exception e) {
            return errorHandler.errorServer(e);
        }
    }

}
