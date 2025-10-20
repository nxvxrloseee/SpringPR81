package com.example.spring81.services;

import com.example.spring81.config.ResourceNotFoundException;
import com.example.spring81.models.Plans;
import com.example.spring81.models.Status;
import com.example.spring81.dto.PlansFormDto;
import com.example.spring81.repositories.PlansRepository;
import com.example.spring81.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlansService {

    @Autowired private PlansRepository plansRepository;
    @Autowired private StatusRepository statusRepository;

    public List<Plans> findAll() {
        return plansRepository.findAll();
    }

    public Plans findById(Long id) {
        return plansRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found with id: " + id));
    }


    public PlansFormDto getPlanFormDtoById(Long id) {
        Plans plan = findById(id);
        PlansFormDto dto = new PlansFormDto();
        dto.setPlanId(plan.getPlanId());
        dto.setPlanName(plan.getPlanName());
        dto.setCpuCores(plan.getCpuCores());
        dto.setRamGb(plan.getRamGb());
        dto.setSsdGb(plan.getSsdGb());
        dto.setPricePerDay(plan.getPricePerDay());
        if (plan.getStatus() != null) {
            dto.setStatusId(plan.getStatus().getStatusId());
        }
        return dto;
    }

    @Transactional
    public Plans saveFromDto(PlansFormDto dto) {
        // Находим связанный Status
        Status status = statusRepository.findById(dto.getStatusId())
                .orElseThrow(() -> new ResourceNotFoundException("Status not found for ID: " + dto.getStatusId()));

        Plans plan = new Plans();
        plan.setPlanName(dto.getPlanName());
        plan.setCpuCores(dto.getCpuCores());
        plan.setRamGb(dto.getRamGb());
        plan.setSsdGb(dto.getSsdGb());
        plan.setPricePerDay(dto.getPricePerDay());
        plan.setStatus(status);

        return plansRepository.save(plan);
    }

    @Transactional
    public Plans updateFromDto(Long id, PlansFormDto dto) {

        Plans plan = findById(id);

        Status status = statusRepository.findById(dto.getStatusId())
                .orElseThrow(() -> new ResourceNotFoundException("Status not found for ID: " + dto.getStatusId()));

        plan.setPlanName(dto.getPlanName());
        plan.setCpuCores(dto.getCpuCores());
        plan.setRamGb(dto.getRamGb());
        plan.setSsdGb(dto.getSsdGb());
        plan.setPricePerDay(dto.getPricePerDay());
        plan.setStatus(status);

        return plansRepository.save(plan);
    }

    public void deleteById(Long id) {
        if (!plansRepository.existsById(id)) {
            throw new ResourceNotFoundException("Plan not found with id: " + id);
        }
        plansRepository.deleteById(id);
    }
}