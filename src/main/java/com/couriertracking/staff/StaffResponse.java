package com.couriertracking.staff;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class StaffResponse {

    private UUID id;

    private String name;

    private String mobile;

    private StaffRole role;

    private String address;

    private boolean active;

    private LocalDateTime createdAt;
}