package com.couriertracking.shipment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class AssignDeliveryAgentRequest {

    @NotNull(message = "Delivery agent ID is required")
    private UUID deliveryAgentId;
}