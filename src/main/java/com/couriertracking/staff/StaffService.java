package com.couriertracking.staff;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;

    public StaffResponse createStaff(StaffRequest request) {

        Staff staff = new Staff();

        staff.setName(request.getName());
        staff.setMobile(request.getMobile());
        staff.setRole(request.getRole());
        staff.setAddress(request.getAddress());
        staff.setActive(true);

        Staff savedStaff = staffRepository.save(staff);

        return new StaffResponse(
                savedStaff.getId(),
                savedStaff.getName(),
                savedStaff.getMobile(),
                savedStaff.getRole(),
                savedStaff.getAddress(),
                savedStaff.isActive(),
                savedStaff.getCreatedAt()
        );
    }
}