package com.couriertracking.shipment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import com.couriertracking.ApiResponse;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PostMapping
    public ResponseEntity<Shipment> createShipment(
            @Valid @RequestBody CreateShipmentRequest request) {

        Shipment shipment =
                shipmentService.createShipment(request);

        return ResponseEntity.ok(shipment);
    }
    @PutMapping("/{shipmentId}/status")
    public ResponseEntity<ApiResponse<Shipment>> updateShipmentStatus(
            @PathVariable UUID shipmentId,
            @Valid @RequestBody UpdateShipmentStatusRequest request) {

        Shipment shipment =
                shipmentService.updateShipmentStatus(shipmentId, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        shipment,
                        null,
                        null
                )
        );
    }
}