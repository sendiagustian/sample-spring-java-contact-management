package id.sendistudio.spring.base.services.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import id.sendistudio.spring.base.data.requests.LoginRequest;
import id.sendistudio.spring.base.data.requests.RegisterRequest;
import id.sendistudio.spring.base.data.views.LoginView;
import id.sendistudio.spring.base.data.views.UserView;
import id.sendistudio.spring.base.repositories.UserRepository;
import id.sendistudio.spring.base.services.AuthService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

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

    private WebResponse generatenewToken(LoginRequest request, Boolean setExpired) {
        LoginView loginView = new LoginView();

        String token = jwt.generateToken(request.getUsername(), setExpired);
        Optional<Date> expiredToken = jwt.extractExpiration(token);

        Boolean updateToken = userRepository.updateToken(request.getUsername(), token, expiredToken);
        if (!updateToken) {
            return new ErrorResponse(500, "Failed to update token");
        }

        loginView.setToken(Optional.of(token));
        loginView.setExpiredAt(expiredToken.map(Date::getTime).map(Optional::ofNullable).orElse(Optional.empty()));

        return new DataResponse<LoginView>(200, loginView);
    }

    @Override
    @Transactional
    public WebResponse register(RegisterRequest request) {
        try {
            validator.validate(request);
            Boolean result = userRepository.create(request);

            if (result) {
                return new DataResponse<String>(200, request.getUsername());
            } else {
                return new ErrorResponse(409, "Username already exists");
            }
        } catch (DataAccessException e) {
            return errorHandler.errorData(e);
        } catch (Exception e) {
            return errorHandler.errorServer(e);
        }
    }

    @Override
    @Transactional
    public WebResponse login(Boolean setExpired, LoginRequest request) {
        try {
            setExpired = (setExpired != null) ? setExpired : false;
            validator.validate(request);

            LoginView loginView = new LoginView();
            String passwordhased = userRepository.getPasswordByUsername(request.getUsername());

            if (passwordhased == null) {
                return new ErrorResponse(401, "Invalid username or password");
            }

            Boolean isPasswordMatch = passwordEncoder.matches(request.getPassword(), passwordhased);

            if (!isPasswordMatch) {
                return new ErrorResponse(401, "Invalid username or password");
            }

            UserView user = userRepository.getByUsername(request.getUsername()).orElse(null);

            if (user.getToken() == null || user.getToken().isEmpty()) {
                return generatenewToken(request, setExpired);
            } else {
                Boolean validToken = jwt.validateToken(user.getToken().orElse(null), request.getUsername());

                if (!validToken) {
                    throw new Exception("Invalid Token");
                }

                Boolean tokenExpired = jwt.isTokenExpired(user.getToken().orElse(null));

                if (tokenExpired != null && tokenExpired) {
                    return generatenewToken(request, setExpired);
                }

                Optional<Date> expiredToken = jwt.extractExpiration(user.getToken().orElse(null));

                loginView.setToken(user.getToken());
                loginView.setExpiredAt(expiredToken.map(Date::getTime));
            }

            return new DataResponse<LoginView>(200, loginView);
        } catch (DataAccessException e) {
            return errorHandler.errorData(e);
        } catch (Exception e) {
            return errorHandler.errorServer(e);
        }
    }

    @Override
    @Transactional
    public WebResponse logout(String username) {
        try {
            Boolean result = userRepository.updateToken(username, null, null);

            if (result) {
                return new MessageResponse(200, username + " logout!");
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
