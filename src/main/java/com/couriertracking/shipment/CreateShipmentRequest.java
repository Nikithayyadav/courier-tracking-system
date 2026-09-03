package com.couriertracking.shipment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateShipmentRequest {

    @NotBlank(message = "Sender name is required")
    private String senderName;

    @NotBlank(message = "Sender mobile is required")
    private String senderMobile;

    @NotBlank(message = "Sender address is required")
    private String senderAddress;

    @NotBlank(message = "Receiver name is required")
    private String receiverName;

    @NotBlank(message = "Receiver mobile is required")
    private String receiverMobile;

    @NotBlank(message = "Receiver address is required")
    private String receiverAddress;

    @NotNull(message = "Package type is required")
    private PackageType packageType;

    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be greater than zero")
    private Double weight;

    @NotNull(message = "Distance is required")
    @Positive(message = "Distance must be greater than zero")
    private Double distance;
}