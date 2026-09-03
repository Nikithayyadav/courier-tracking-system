package com.couriertracking.shipment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TrackingHistoryRepository
        extends JpaRepository<TrackingHistory, UUID> {

    List<TrackingHistory> findByShipmentIdOrderByCreatedAtAsc(UUID shipmentId);
}