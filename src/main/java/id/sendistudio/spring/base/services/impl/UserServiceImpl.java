package id.sendistudio.spring.base.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import id.sendistudio.spring.base.app.middlewares.responses.DataResponse;
import id.sendistudio.spring.base.app.middlewares.responses.ErrorResponse;
import id.sendistudio.spring.base.app.middlewares.responses.MessageResponse;
import id.sendistudio.spring.base.app.middlewares.responses.WebResponse;
import id.sendistudio.spring.base.app.utils.ErrorUtil;
import id.sendistudio.spring.base.app.utils.JwtTokenUtil;
import id.sendistudio.spring.base.app.utils.ValidatorUtil;
import id.sendistudio.spring.base.data.requests.UpdateUserRequest;
import id.sendistudio.spring.base.data.views.UserView;
import id.sendistudio.spring.base.repositories.UserRepository;
import id.sendistudio.spring.base.services.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ValidatorUtil validator;

    @Autowired
    ErrorUtil errorHandler;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenUtil jwt;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "userCache", key = "#username", unless = "#result == null or #result.status != 200")
    public WebResponse gets(Optional<String> username) {
        try {
            if (username.isPresent() && !username.get().isEmpty()) {
                UserView result = userRepository.getByUsername(username.get()).orElse(null);

                if (result == null) {
                    return new ErrorResponse(404, "User not found");
                }

                return new DataResponse<UserView>(200, result);
            } else {
                List<UserView> result = userRepository.getAll();
                return new DataResponse<List<UserView>>(200, result.isEmpty() ? List.of() : result);
            }
        } catch (DataAccessException e) {
            return errorHandler.errorNotFound(e);
        } catch (Exception e) {
            return errorHandler.errorServer(e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "userCache", allEntries = true)
    public WebResponse updateData(String username, UpdateUserRequest request) {
        try {
            validator.validate(request);
            Boolean result = userRepository.updateData(username, request);

            if (result) {
                return new MessageResponse(200, request.getName() + " data updated!");
            } else {
                return new ErrorResponse(404, "User not found");
            }
        } catch (DataAccessException e) {
            return errorHandler.errorData(e);
        } catch (Exception e) {
            return errorHandler.errorServer(e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "userCache", allEntries = true)
    public WebResponse delete(String username) {
        try {
            Boolean result = userRepository.deleteByUsername(username);

            if (result) {
                return new MessageResponse(200, username + " deleted!");
            } else {
                return new ErrorResponse(404, "User not found");
            }
        } catch (DataAccessException e) {
            return errorHandler.errorData(e);
        } catch (Exception e) {
            return errorHandler.errorServer(e);
        }
    }

}
