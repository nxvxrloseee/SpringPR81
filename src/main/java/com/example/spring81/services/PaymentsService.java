package com.example.spring81.services;

import com.example.spring81.config.ResourceNotFoundException;
import com.example.spring81.models.Payments;
import com.example.spring81.models.Status;
import com.example.spring81.models.UserModel;
import com.example.spring81.dto.PaymentFormDto;
import com.example.spring81.repositories.PaymentsRepository;
import com.example.spring81.repositories.StatusRepository;
import com.example.spring81.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentsService {

    @Autowired private PaymentsRepository paymentsRepository;
    @Autowired private UserModelRepository userRepository;
    @Autowired private StatusRepository statusRepository;

    public List<Payments> findAll() {
        return paymentsRepository.findAll();
    }

    public Payments findById(Long id) {
        return paymentsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found: " + id));
    }

    public PaymentFormDto getPaymentFormDtoById(Long id) {
        Payments payment = findById(id);
        PaymentFormDto dto = new PaymentFormDto();
        dto.setPaymentId(payment.getPaymentId());
        dto.setAmount(payment.getAmount());
        if (payment.getUser() != null) dto.setUserId(payment.getUser().getUserId());
        if (payment.getStatus() != null) dto.setStatusId(payment.getStatus().getStatusId());
        return dto;
    }

    private Payments mapDtoToEntity(PaymentFormDto dto, Payments payment) {
        UserModel user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + dto.getUserId()));
        Status status = statusRepository.findById(dto.getStatusId())
                .orElseThrow(() -> new ResourceNotFoundException("Status not found: " + dto.getStatusId()));

        payment.setUser(user);
        payment.setStatus(status);
        payment.setAmount(dto.getAmount());
        return payment;
    }

    @Transactional
    public Payments saveFromDto(PaymentFormDto dto) {
        Payments payment = mapDtoToEntity(dto, new Payments());
        payment.setCreatedAt(LocalDateTime.now()); // Устанавливаем при создании
        return paymentsRepository.save(payment);
    }

    @Transactional
    public Payments updateFromDto(Long id, PaymentFormDto dto) {
        Payments payment = findById(id);
        payment = mapDtoToEntity(dto, payment);
        // createdAt не обновляем
        return paymentsRepository.save(payment);
    }

    public void deleteById(Long id) {
        if (!paymentsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Payment not found with id: " + id);
        }
        paymentsRepository.deleteById(id);
    }
}