package id.sendistudio.spring.base.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.sendistudio.spring.base.app.middlewares.responses.WebResponse;
import id.sendistudio.spring.base.data.requests.ContactRequest;
import id.sendistudio.spring.base.data.requests.PaginationContactRequest;
import id.sendistudio.spring.base.services.ContactService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Contact Management")
@RequestMapping("/api/v1/contact")
public class ContactController {

    @Autowired
    ContactService contactService;

    @PostMapping("/create")
    @SecurityRequirement(name = "X-API-TOKEN")
    @SecurityRequirement(name = "LOG-SERVICE-TRX")
    public ResponseEntity<WebResponse> create(@Valid @RequestBody(required = true) ContactRequest request) {
        return ResponseEntity.ok(contactService.create(request));
    }

    @GetMapping("/gets")
    @SecurityRequirement(name = "X-API-TOKEN")
    @SecurityRequirement(name = "LOG-SERVICE-TRX")
    public ResponseEntity<WebResponse> gets(
            @RequestParam(required = false) String uid,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String name) {

        return ResponseEntity.ok(contactService.gets(uid, phone, name));
    }

    @GetMapping("/get-pagination")
    @SecurityRequirement(name = "X-API-TOKEN")
    @SecurityRequirement(name = "LOG-SERVICE-TRX")
    public ResponseEntity<WebResponse> gets(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        PaginationContactRequest request = new PaginationContactRequest();

        request.setName(name);
        request.setPhone(phone);
        request.setEmail(email);
        request.setPage(page == null ? 1 : page);
        request.setSize(size == null ? 10 : size);

        return ResponseEntity.ok(contactService.getPagination(request));
    }

    @PutMapping("/update-data")
    @SecurityRequirement(name = "X-API-TOKEN")
    @SecurityRequirement(name = "LOG-SERVICE-TRX")
    public ResponseEntity<WebResponse> updateData(
            @RequestParam(required = true) String uid,
            @RequestBody(required = true) ContactRequest request) {

        return ResponseEntity.ok(contactService.updateData(uid, request));
    }

    @DeleteMapping("/delete-by-uid")
    @SecurityRequirement(name = "X-API-TOKEN")
    @SecurityRequirement(name = "LOG-SERVICE-TRX")
    public ResponseEntity<WebResponse> deleteByUid(@RequestParam(required = true) String uid) {
        return ResponseEntity.ok(contactService.deleteByUid(uid));
    }

    @DeleteMapping("/delete-by-phone")
    @SecurityRequirement(name = "X-API-TOKEN")
    @SecurityRequirement(name = "LOG-SERVICE-TRX")
    public ResponseEntity<WebResponse> deleteByPhone(@RequestParam(required = true) String phone) {
        return ResponseEntity.ok(contactService.deleteByPhone(phone));
    }

}
