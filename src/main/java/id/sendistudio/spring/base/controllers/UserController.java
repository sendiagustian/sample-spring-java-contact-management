package id.sendistudio.spring.base.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.sendistudio.spring.base.app.middlewares.responses.WebResponse;
import id.sendistudio.spring.base.data.requests.UpdateUserRequest;
import id.sendistudio.spring.base.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "User Management")
@RequestMapping("/api/v1/user")
public class UserController {
    
    @Autowired
    UserService userService;
    
    @GetMapping("/gets")
    @SecurityRequirement(name = "LOG_SERVICE_TRX")
    public ResponseEntity<WebResponse> gets(@RequestParam Optional<String> username) {
        return ResponseEntity.ok(userService.gets(username));
    }

    @PutMapping("update-data")
    public ResponseEntity<WebResponse> putMethodName(@RequestBody(required = true) UpdateUserRequest request) {
        return ResponseEntity.ok(userService.update(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<WebResponse> delete(@RequestParam String username) {
        return ResponseEntity.ok(userService.delete(username));
    }
}
