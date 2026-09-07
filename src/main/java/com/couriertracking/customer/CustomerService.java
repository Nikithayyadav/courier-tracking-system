package com.couriertracking.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final Map<String, String> otpStore =
            new ConcurrentHashMap<>();

    private final Random random = new Random();

    public CustomerResponse createCustomer(CustomerRequest request) {

        if (customerRepository.findByMobile(request.getMobile()).isPresent()) {
            throw new RuntimeException("Customer with this mobile number already exists");
        }

        Customer customer = new Customer();

        customer.setName(request.getName());
        customer.setMobile(request.getMobile());
        customer.setAddress(request.getAddress());
        customer.setActive(true);

        Customer savedCustomer =
                customerRepository.save(customer);

        return new CustomerResponse(
                savedCustomer.getId(),
                savedCustomer.getName(),
                savedCustomer.getMobile(),
                savedCustomer.getAddress(),
                savedCustomer.isActive(),
                savedCustomer.getCreatedAt(),
                savedCustomer.getUpdatedAt()
        );
    }
    public String generateOtp(CustomerLoginRequest request) {

        customerRepository.findByMobile(request.getMobile())
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        String otp = String.format(
                "%06d",
                random.nextInt(1000000)
        );

        otpStore.put(request.getMobile(), otp);

        return otp;
    }
    public CustomerResponse verifyOtp(VerifyOtpRequest request) {

        Customer customer = customerRepository
                .findByMobile(request.getMobile())
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        String storedOtp = otpStore.get(request.getMobile());

        if (storedOtp == null) {
            throw new RuntimeException("OTP not found or expired");
        }

        if (!storedOtp.equals(request.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        otpStore.remove(request.getMobile());

        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getMobile(),
                customer.getAddress(),
                customer.isActive(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }
}