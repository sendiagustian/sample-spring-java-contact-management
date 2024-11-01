package id.sendistudio.spring.base.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.sendistudio.spring.base.app.middlewares.responses.WebResponse;
import id.sendistudio.spring.base.data.requests.LoginRequest;
import id.sendistudio.spring.base.data.requests.RegisterRequest;
import id.sendistudio.spring.base.services.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Authentication")
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<WebResponse> register(@RequestBody(required = true) RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<WebResponse> login(@RequestParam(required = false) Boolean setExpired,
            @RequestBody(required = true) LoginRequest request) {
        return ResponseEntity.ok(authService.login(setExpired, request));
    }

    @PostMapping("/logout")
    public ResponseEntity<WebResponse> logout(@RequestParam String username) {
        return ResponseEntity.ok(authService.logout(username));
    }

}
