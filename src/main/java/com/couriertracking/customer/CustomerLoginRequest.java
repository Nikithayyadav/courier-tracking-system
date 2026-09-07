package com.couriertracking.customer;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerLoginRequest {

    @NotBlank(message = "Mobile is required")
    private String mobile;
}