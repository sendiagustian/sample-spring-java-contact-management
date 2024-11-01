package id.sendistudio.spring.base.services;

import id.sendistudio.spring.base.app.middlewares.responses.WebResponse;
import id.sendistudio.spring.base.data.requests.LoginRequest;
import id.sendistudio.spring.base.data.requests.RegisterRequest;

public interface AuthService {
    WebResponse register(RegisterRequest request);

    WebResponse login(Boolean setExpired, LoginRequest request);

    WebResponse logout(String username);
}
