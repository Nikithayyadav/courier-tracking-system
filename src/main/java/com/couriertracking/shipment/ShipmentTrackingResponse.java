package com.couriertracking.shipment;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ShipmentTrackingResponse {

    private Shipment shipment;

    private List<TrackingHistoryResponse> trackingHistory;
}