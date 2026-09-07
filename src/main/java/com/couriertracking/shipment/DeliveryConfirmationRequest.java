package com.couriertracking.shipment;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeliveryConfirmationRequest {

    @NotBlank(message = "Location is required")
    private String location;

    private String remarks;
}