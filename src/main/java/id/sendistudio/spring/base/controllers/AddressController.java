package id.sendistudio.spring.base.controllers;

import java.util.Optional;

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
import id.sendistudio.spring.base.data.requests.CreateAddressRequest;
import id.sendistudio.spring.base.data.requests.PaginationAddressRequest;
import id.sendistudio.spring.base.data.requests.UpdateAddressRequest;
import id.sendistudio.spring.base.services.AddressService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Address Management")
@RequestMapping("/api/v1/address")
public class AddressController {
    
    @Autowired
    AddressService addressService;

    @PostMapping("/create")
    @SecurityRequirement(name = "X-API-TOKEN")
    @SecurityRequirement(name = "LOG-SERVICE-TRX")
    public ResponseEntity<WebResponse> create(@Valid @RequestBody(required = true) CreateAddressRequest request) {
        return ResponseEntity.ok(addressService.create(request));
    }

    @GetMapping("/gets")
    @SecurityRequirement(name = "X-API-TOKEN")
    @SecurityRequirement(name = "LOG-SERVICE-TRX")
    public ResponseEntity<WebResponse> gets(
            @RequestParam(required = false) Optional<String> uid,
            @RequestParam(required = false) Optional<String> country,
            @RequestParam(required = false) Optional<String> province,
            @RequestParam(required = false) Optional<String> city,
            @RequestParam(required = false) Optional<String> street) {

        return ResponseEntity.ok(addressService.gets(uid, country, province, city, street));
    }

    @GetMapping("/get-pagination")
    @SecurityRequirement(name = "X-API-TOKEN")
    @SecurityRequirement(name = "LOG-SERVICE-TRX")
    public ResponseEntity<WebResponse> gets(
            @RequestParam(required = false) Optional<String> country,
            @RequestParam(required = false) Optional<String> province,
            @RequestParam(required = false) Optional<String> city,
            @RequestParam(required = false) Optional<String> street,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        PaginationAddressRequest request = new PaginationAddressRequest();

        request.setCountry(country);
        request.setProvince(province);
        request.setCity(city);
        request.setStreet(street);

        request.setPage(page == null ? 1 : page);
        request.setSize(size == null ? 10 : size);

        return ResponseEntity.ok(addressService.getPagination(request));
    }

    @PutMapping("/update-data")
    @SecurityRequirement(name = "X-API-TOKEN")
    @SecurityRequirement(name = "LOG-SERVICE-TRX")
    public ResponseEntity<WebResponse> updateData(
            @RequestParam(required = true) String uid,
            @RequestBody(required = true) UpdateAddressRequest request) {

        return ResponseEntity.ok(addressService.updateData(uid, request));
    }

    @DeleteMapping("/delete-by-uid")
    @SecurityRequirement(name = "X-API-TOKEN")
    @SecurityRequirement(name = "LOG-SERVICE-TRX")
    public ResponseEntity<WebResponse> deleteByUid(@RequestParam(required = true) String uid) {
        return ResponseEntity.ok(addressService.delete(uid));
    }
}
