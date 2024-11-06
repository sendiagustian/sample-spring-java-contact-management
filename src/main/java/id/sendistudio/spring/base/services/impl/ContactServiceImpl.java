package id.sendistudio.spring.base.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import id.sendistudio.spring.base.app.middlewares.responses.DataPaginationResponse;
import id.sendistudio.spring.base.app.middlewares.responses.DataResponse;
import id.sendistudio.spring.base.app.middlewares.responses.ErrorResponse;
import id.sendistudio.spring.base.app.middlewares.responses.WebResponse;
import id.sendistudio.spring.base.app.utils.ErrorUtil;
import id.sendistudio.spring.base.app.utils.JwtTokenUtil;
import id.sendistudio.spring.base.app.utils.ServletUtil;
import id.sendistudio.spring.base.app.utils.TypeUtil;
import id.sendistudio.spring.base.app.utils.ValidatorUtil;
import id.sendistudio.spring.base.data.requests.ContactPaginationRequest;
import id.sendistudio.spring.base.data.requests.ContactRequest;
import id.sendistudio.spring.base.data.views.ContactView;
import id.sendistudio.spring.base.data.views.PagingView;
import id.sendistudio.spring.base.repositories.ContactRepository;
import id.sendistudio.spring.base.services.ContactService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    protected ValidatorUtil validator;

    @Autowired
    ErrorUtil errorHandler;

    @Autowired
    TypeUtil typeUtil;

    @Autowired
    ServletUtil servlet;

    @Autowired
    JwtTokenUtil jwt;

    @Override
    @Transactional
    public WebResponse create(ContactRequest request) {
        try {
            validator.validate(request);

            String uid = typeUtil.genereateUUID();

            String token = servlet.getApiToken();

            String username = jwt.extractUsername(token).orElse(null);

            Boolean result = contactRepository.create(uid, username, request);

            if (result) {
                return new DataResponse<String>(200, uid);
            } else {
                return new ErrorResponse(500, "Failed to create contact");
            }
        } catch (DataAccessException e) {
            return errorHandler.errorData(e);
        } catch (Exception e) {
            return errorHandler.errorServer(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public WebResponse gets(Optional<String> uid, Optional<String> phone, Optional<String> name) {
        try {
            if ((uid.isPresent() || phone.isPresent()) && name.isPresent()) {
                return new ErrorResponse(400, "Invalid request can't use uid, phone, and name at the same time");
            } else if (uid.isPresent() && phone.isPresent()) {
                return new ErrorResponse(400, "Invalid request can't use uid and phone at the same time");
            } else {
                if (uid.isPresent()) {
                    ContactView result = contactRepository.getByUid(uid.get()).orElse(null);

                    if (result == null) {
                        return new ErrorResponse(404, "Contact not found");
                    }

                    return new DataResponse<ContactView>(200, result);
                } else if (phone.isPresent()) {
                    ContactView result = contactRepository.getByPhone(phone.get()).orElse(null);

                    if (result == null) {
                        return new ErrorResponse(404, "Contact not found");
                    }

                    return new DataResponse<ContactView>(200, result);
                } else {
                    List<ContactView> results = contactRepository.gets(name);
                    return new DataResponse<List<ContactView>>(200, results.isEmpty() ? List.of() : results);
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
    public WebResponse getPagination(ContactPaginationRequest request) {
        try {
            Integer total = contactRepository.countTotalPagination(request).orElse(0);
            Page<ContactView> results = contactRepository.getPagination(request, total);

            PagingView paging = new PagingView();
            paging.setSize(results.getSize());
            paging.setTotalData(total);
            paging.setCurrentPage(results.getNumber());
            paging.setTotalPage((results.getTotalPages() - 1));

            return new DataPaginationResponse<List<ContactView>>(200, results.getContent(), paging);
        } catch (DataAccessException e) {
            return errorHandler.errorNotFound(e);
        } catch (Exception e) {
            return errorHandler.errorServer(e);
        }
    }

    @Override
    @Transactional
    public WebResponse updateData(String username, ContactRequest request) {
        try {
            validator.validate(request);
            Boolean result = contactRepository.updateData(username, request);

            if (result) {
                return new DataResponse<String>(200, "Contact updated successfully");
            } else {
                return new ErrorResponse(500, "Failed to update contact");
            }
        } catch (DataAccessException e) {
            return errorHandler.errorData(e);
        } catch (Exception e) {
            return errorHandler.errorServer(e);
        }
    }

    @Override
    @Transactional
    public WebResponse deleteByUid(String uid) {
        try {
            Boolean result = contactRepository.deleteByUid(uid);

            if (result) {
                return new DataResponse<String>(200, "Contact deleted successfully");
            } else {
                return new ErrorResponse(500, "Failed to delete contact");
            }
        } catch (DataAccessException e) {
            return errorHandler.errorData(e);
        } catch (Exception e) {
            return errorHandler.errorServer(e);
        }
    }

    @Override
    @Transactional
    public WebResponse deleteByPhone(String phone) {
        try {
            Boolean result = contactRepository.deleteByPhone(phone);

            if (result) {
                return new DataResponse<String>(200, "Contact deleted successfully");
            } else {
                return new ErrorResponse(500, "Failed to delete contact");
            }
        } catch (DataAccessException e) {
            return errorHandler.errorData(e);
        } catch (Exception e) {
            return errorHandler.errorServer(e);
        }
    }

}
