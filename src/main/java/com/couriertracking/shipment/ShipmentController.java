package com.couriertracking.shipment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import com.couriertracking.ApiResponse;
import java.util.List;

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
    @GetMapping("/track/{trackingNumber}")
    public ResponseEntity<ApiResponse<ShipmentTrackingResponse>> trackShipment(
            @PathVariable String trackingNumber) {

        ShipmentTrackingResponse response =
                shipmentService.trackShipment(trackingNumber);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        response,
                        null,
                        null
                )
        );
    }
    @PutMapping("/{shipmentId}/assign-agent")
    public ResponseEntity<ApiResponse<Shipment>> assignDeliveryAgent(
            @PathVariable UUID shipmentId,
            @Valid @RequestBody AssignDeliveryAgentRequest request) {

        Shipment shipment =
                shipmentService.assignDeliveryAgent(
                        shipmentId,
                        request
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        shipment,
                        null,
                        null
                )
        );
    }
    @PutMapping("/{shipmentId}/cancel")
    public ResponseEntity<ApiResponse<Shipment>> cancelShipment(
            @PathVariable UUID shipmentId) {

        Shipment shipment =
                shipmentService.cancelShipment(shipmentId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        shipment,
                        null,
                        null
                )
        );
    }
    @GetMapping("/search/{trackingNumber}")
    public ResponseEntity<ApiResponse<Shipment>> searchShipment(
            @PathVariable String trackingNumber) {

        Shipment shipment =
                shipmentService.searchShipment(trackingNumber);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        shipment,
                        null,
                        null
                )
        );
    }
    @GetMapping("/{shipmentId}/history")
    public ResponseEntity<ApiResponse<List<TrackingHistoryResponse>>> getDeliveryHistory(
            @PathVariable UUID shipmentId) {

        List<TrackingHistoryResponse> history =
                shipmentService.getDeliveryHistory(shipmentId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        history,
                        null,
                        null
                )
        );
    }
    @PostMapping("/{shipmentId}/confirm-delivery")
    public ResponseEntity<ApiResponse<Shipment>> confirmDelivery(
            @PathVariable UUID shipmentId,
            @Valid @RequestBody DeliveryConfirmationRequest request) {

        Shipment shipment =
                shipmentService.confirmDelivery(
                        shipmentId,
                        request
                );

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