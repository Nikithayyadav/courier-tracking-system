package com.couriertracking.customer;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CustomerResponse {

    private UUID id;

    private String name;

    private String mobile;

    private String address;

    private boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}