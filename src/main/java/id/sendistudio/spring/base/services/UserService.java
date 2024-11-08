package id.sendistudio.spring.base.services;

import id.sendistudio.spring.base.app.middlewares.responses.WebResponse;
import id.sendistudio.spring.base.data.requests.UpdateUserRequest;

public interface UserService {
    WebResponse gets(String username);

    WebResponse updateData(String username, UpdateUserRequest request);

    WebResponse delete(String username);
}
