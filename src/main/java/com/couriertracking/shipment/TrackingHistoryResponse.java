package com.couriertracking.shipment;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TrackingHistoryResponse {

    private UUID id;

    private ShipmentStatus status;

    private String location;

    private String remarks;

    private LocalDateTime createdAt;
}