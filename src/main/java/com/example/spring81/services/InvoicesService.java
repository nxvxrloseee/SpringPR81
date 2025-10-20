package com.example.spring81.services;

import com.example.spring81.config.ResourceNotFoundException;
import com.example.spring81.models.Invoices;
import com.example.spring81.models.Status;
import com.example.spring81.models.UserModel;
import com.example.spring81.dto.InvoiceFormDto;
import com.example.spring81.repositories.InvoicesRepository;
import com.example.spring81.repositories.StatusRepository;
import com.example.spring81.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvoicesService {

    @Autowired private InvoicesRepository invoicesRepository;
    @Autowired private UserModelRepository userRepository;
    @Autowired private StatusRepository statusRepository;

    public List<Invoices> findAll() {
        return invoicesRepository.findAll();
    }

    public Invoices findById(Long id) {
        return invoicesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found: " + id));
    }

    public InvoiceFormDto getInvoiceFormDtoById(Long id) {
        Invoices invoice = findById(id);
        InvoiceFormDto dto = new InvoiceFormDto();
        dto.setInvoiceId(invoice.getInvoiceId());
        dto.setAmount(invoice.getAmount());
        dto.setPeriodStart(invoice.getPeriodStart());
        dto.setPeriodEnd(invoice.getPeriodEnd());
        if (invoice.getUser() != null) dto.setUserId(invoice.getUser().getUserId());
        if (invoice.getStatus() != null) dto.setStatusId(invoice.getStatus().getStatusId());
        return dto;
    }

    private Invoices mapDtoToEntity(InvoiceFormDto dto, Invoices invoice) {
        if (dto.getPeriodStart().isAfter(dto.getPeriodEnd())) {
            throw new IllegalArgumentException("Дата начала периода не может быть позже даты окончания.");
        }

        UserModel user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + dto.getUserId()));
        Status status = statusRepository.findById(dto.getStatusId())
                .orElseThrow(() -> new ResourceNotFoundException("Status not found: " + dto.getStatusId()));

        invoice.setUser(user);
        invoice.setStatus(status);
        invoice.setAmount(dto.getAmount());
        invoice.setPeriodStart(dto.getPeriodStart());
        invoice.setPeriodEnd(dto.getPeriodEnd());
        return invoice;
    }

    @Transactional
    public Invoices saveFromDto(InvoiceFormDto dto) {
        Invoices invoice = mapDtoToEntity(dto, new Invoices());
        invoice.setCreatedAt(LocalDateTime.now()); // Устанавливаем при создании
        return invoicesRepository.save(invoice);
    }

    @Transactional
    public Invoices updateFromDto(Long id, InvoiceFormDto dto) {
        Invoices invoice = findById(id);
        invoice = mapDtoToEntity(dto, invoice);
        // createdAt не обновляем
        return invoicesRepository.save(invoice);
    }

    public void deleteById(Long id) {
        if (!invoicesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Invoice not found with id: " + id);
        }
        invoicesRepository.deleteById(id);
    }
}