package com.couriertracking.customer;

import com.couriertracking.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(
            @Valid @RequestBody CustomerRequest request) {

        CustomerResponse response =
                customerService.createCustomer(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        response,
                        null,
                        null
                )
        );
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> generateOtp(
            @Valid @RequestBody CustomerLoginRequest request) {

        String otp =
                customerService.generateOtp(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        otp,
                        null,
                        null
                )
        );
    }
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<CustomerResponse>> verifyOtp(
            @Valid @RequestBody VerifyOtpRequest request) {

        CustomerResponse response =
                customerService.verifyOtp(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        response,
                        null,
                        null
                )
        );
    }
}