package com.couriertracking.staff;

import com.couriertracking.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @PostMapping
    public ResponseEntity<ApiResponse<StaffResponse>> createStaff(
            @Valid @RequestBody StaffRequest request) {

        StaffResponse response =
                staffService.createStaff(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        response,
                        null,
                        null
                )
        );
    }
}