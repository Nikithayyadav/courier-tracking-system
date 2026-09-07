package com.couriertracking.customer;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerifyOtpRequest {

    @NotBlank(message = "Mobile is required")
    private String mobile;

    @NotBlank(message = "OTP is required")
    private String otp;
}