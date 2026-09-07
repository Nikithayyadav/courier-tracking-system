package com.couriertracking.shipment;

import com.couriertracking.customer.Customer;
import com.couriertracking.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;
import com.couriertracking.staff.Staff;
import com.couriertracking.staff.StaffRepository;
import com.couriertracking.staff.StaffRole;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final StaffRepository staffRepository;
    private final CustomerRepository customerRepository;
    private final TrackingHistoryRepository trackingHistoryRepository;

    public Shipment createShipment(CreateShipmentRequest request) {

        Customer sender = customerRepository
                .findByMobile(request.getSenderMobile())
                .orElseGet(() -> createCustomer(
                        request.getSenderName(),
                        request.getSenderMobile(),
                        request.getSenderAddress()
                ));

        Customer receiver = customerRepository
                .findByMobile(request.getReceiverMobile())
                .orElseGet(() -> createCustomer(
                        request.getReceiverName(),
                        request.getReceiverMobile(),
                        request.getReceiverAddress()
                ));

        Shipment shipment = new Shipment();

        shipment.setTrackingNumber(generateTrackingNumber());
        shipment.setSender(sender);
        shipment.setReceiver(receiver);
        shipment.setPackageType(request.getPackageType());
        shipment.setWeight(request.getWeight());
        shipment.setDistance(request.getDistance());
        shipment.setDeliveryCharge(
                calculateDeliveryCharge(
                        request.getWeight(),
                        request.getDistance(),
                        request.getPackageType()
                )
        );
        shipment.setStatus(ShipmentStatus.BOOKED);

        Shipment savedShipment = shipmentRepository.save(shipment);

        TrackingHistory history = new TrackingHistory();
        history.setShipment(savedShipment);
        history.setStatus(ShipmentStatus.BOOKED);
        history.setLocation(request.getSenderAddress());
        history.setRemarks("Shipment booked");

        trackingHistoryRepository.save(history);

        return savedShipment;
    }

    private Customer createCustomer(
            String name,
            String mobile,
            String address) {

        Customer customer = new Customer();

        customer.setName(name);
        customer.setMobile(mobile);
        customer.setAddress(address);
        customer.setActive(true);

        return customerRepository.save(customer);
    }

    private String generateTrackingNumber() {

        return "CT-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }

    private double calculateDeliveryCharge(
            double weight,
            double distance,
            PackageType packageType) {

        double charge;

        // Domestic charges
        if (packageType == PackageType.DOMESTIC) {

            // Local: up to 100 km
            if (distance <= 100) {
                charge = getDomesticCharge(weight, 28, 48, 60, 87, 116, 145, 174, 35);

                // Within State: 101 - 500 km
            } else if (distance <= 500) {
                charge = getDomesticCharge(weight, 76, 101, 130, 178, 243, 298, 361, 60);

                // Zone / Metro: 501 - 1000 km
            } else if (distance <= 1000) {
                charge = getDomesticCharge(weight, 82, 137, 182, 254, 355, 441, 539, 95);

                // Other States: above 1000 km
            } else {
                charge = getDomesticCharge(weight, 90, 143, 228, 319, 450, 560, 686, 120);
            }

        } else {

            // International shipping is more expensive.
            // Project-defined multiplier.
            double domesticEquivalent =
                    getDomesticCharge(weight, 90, 143, 228, 319, 450, 560, 686, 120);

            charge = domesticEquivalent * 3;
        }

        return charge;
    }
    private double getDomesticCharge(
            double weight,
            double upto500g,
            double upto1kg,
            double upto1_5kg,
            double upto2kg,
            double upto3kg,
            double upto4kg,
            double upto5kg,
            double additionalKg) {

        if (weight <= 0.5) {
            return upto500g;

        } else if (weight <= 1) {
            return upto1kg;

        } else if (weight <= 1.5) {
            return upto1_5kg;

        } else if (weight <= 2) {
            return upto2kg;

        } else if (weight <= 3) {
            return upto3kg;

        } else if (weight <= 4) {
            return upto4kg;

        } else if (weight <= 5) {
            return upto5kg;

        } else {
            double extraWeight = Math.ceil(weight - 5);
            return upto5kg + (extraWeight * additionalKg);
        }
    }
    public Shipment updateShipmentStatus(
            UUID shipmentId,
            UpdateShipmentStatusRequest request) {

        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() ->
                        new RuntimeException("Shipment not found"));

        shipment.setStatus(request.getStatus());

        if (request.getStatus() == ShipmentStatus.DELIVERED) {
            shipment.setDeliveredAt(LocalDateTime.now());
        }

        Shipment updatedShipment = shipmentRepository.save(shipment);

        TrackingHistory history = new TrackingHistory();
        history.setShipment(updatedShipment);
        history.setStatus(request.getStatus());
        history.setLocation(request.getLocation());
        history.setRemarks(request.getRemarks());

        trackingHistoryRepository.save(history);

        return updatedShipment;
    }

    public ShipmentTrackingResponse trackShipment(String trackingNumber) {

        Shipment shipment = shipmentRepository
                .findByTrackingNumber(trackingNumber)
                .orElseThrow(() ->
                        new RuntimeException("Shipment not found"));

        List<TrackingHistory> history =
                trackingHistoryRepository
                        .findByShipmentIdOrderByCreatedAtAsc(
                                shipment.getId());

        List<TrackingHistoryResponse> historyResponse =
                history.stream()
                        .map(item -> new TrackingHistoryResponse(
                                item.getId(),
                                item.getStatus(),
                                item.getLocation(),
                                item.getRemarks(),
                                item.getCreatedAt()
                        ))
                        .toList();

        return new ShipmentTrackingResponse(
                shipment,
                historyResponse
        );
    }
    public Shipment assignDeliveryAgent(
            UUID shipmentId,
            AssignDeliveryAgentRequest request) {

        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() ->
                        new RuntimeException("Shipment not found"));

        Staff deliveryAgent = staffRepository
                .findByIdAndRole(
                        request.getDeliveryAgentId(),
                        StaffRole.DELIVERY_AGENT
                )
                .orElseThrow(() ->
                        new RuntimeException("Delivery agent not found"));

        shipment.setDeliveryAgent(deliveryAgent);

        return shipmentRepository.save(shipment);
    }
    public Shipment cancelShipment(UUID shipmentId) {

        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() ->
                        new RuntimeException("Shipment not found"));

        shipment.setStatus(ShipmentStatus.CANCELLED);

        Shipment cancelledShipment =
                shipmentRepository.save(shipment);

        TrackingHistory history = new TrackingHistory();
        history.setShipment(cancelledShipment);
        history.setStatus(ShipmentStatus.CANCELLED);
        history.setLocation("Shipment Counter");
        history.setRemarks("Shipment cancelled");

        trackingHistoryRepository.save(history);

        return cancelledShipment;
    }
    public Shipment searchShipment(String trackingNumber) {

        return shipmentRepository
                .findByTrackingNumber(trackingNumber)
                .orElseThrow(() ->
                        new RuntimeException("Shipment not found"));
    }
    public List<TrackingHistoryResponse> getDeliveryHistory(UUID shipmentId) {

        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() ->
                        new RuntimeException("Shipment not found"));

        List<TrackingHistory> history =
                trackingHistoryRepository
                        .findByShipmentIdOrderByCreatedAtAsc(
                                shipment.getId());

        return history.stream()
                .map(item -> new TrackingHistoryResponse(
                        item.getId(),
                        item.getStatus(),
                        item.getLocation(),
                        item.getRemarks(),
                        item.getCreatedAt()
                ))
                .toList();
    }
    public Shipment confirmDelivery(
            UUID shipmentId,
            DeliveryConfirmationRequest request) {

        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() ->
                        new RuntimeException("Shipment not found"));

        shipment.setStatus(ShipmentStatus.DELIVERED);
        shipment.setDeliveredAt(LocalDateTime.now());

        Shipment deliveredShipment =
                shipmentRepository.save(shipment);

        TrackingHistory history = new TrackingHistory();
        history.setShipment(deliveredShipment);
        history.setStatus(ShipmentStatus.DELIVERED);
        history.setLocation(request.getLocation());
        history.setRemarks(request.getRemarks());

        trackingHistoryRepository.save(history);

        return deliveredShipment;
    }
}