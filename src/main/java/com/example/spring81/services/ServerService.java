package com.example.spring81.services;

import com.example.spring81.config.ResourceNotFoundException;
import com.example.spring81.models.*;
import com.example.spring81.dto.ServerFormDto;
import com.example.spring81.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServerService {

    @Autowired private ServerModelRepository serverRepo;
    @Autowired private UserModelRepository userRepo;
    @Autowired private StatusRepository statusRepo;
    @Autowired private OSRepository osRepo;
    @Autowired private PlansRepository plansRepo;

    // R: Read (All)
    public List<Server> findAll() {
        return serverRepo.findAll();
    }

    // R: Read (One)
    public Server findById(Long id) {
        return serverRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Server not found with id: " + id));
    }


    public ServerFormDto getFormDtoById(Long id) {
        Server server = findById(id);

        ServerFormDto dto = new ServerFormDto();
        dto.setServerId(server.getServerId());
        dto.setServerName(server.getServerName());
        dto.setCountry(server.getCountry());
        dto.setCity(server.getCity());
        dto.setIpv4(server.getIpv4());
        dto.setIpv6(server.getIpv6());

        // Связанные сущности
        if (server.getUser() != null) { dto.setUserId(server.getUser().getUserId()); }
        if (server.getStatus() != null) { dto.setStatusId(server.getStatus().getStatusId()); }
        if (server.getOs() != null) { dto.setOsId(server.getOs().getOsId()); }
        if (server.getPlan() != null) { dto.setPlanId(server.getPlan().getPlanId()); }

        return dto;
    }

    // C: Create (Save from DTO)
    @Transactional
    public Server saveFromDto(ServerFormDto dto) {
        Server server = new Server();
        return updateServerModel(server, dto);
    }
    // U: Update (Update from DTO)
    @Transactional
    public Server updateFromDto(Long id, ServerFormDto dto) {
        Server existingServer = findById(id);
        return updateServerModel(existingServer, dto);
    }

    private Server updateServerModel(Server server, ServerFormDto dto) {
        UserModel user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + dto.getUserId()));
        Status status = statusRepo.findById(dto.getStatusId())
                .orElseThrow(() -> new ResourceNotFoundException("Status not found: " + dto.getStatusId()));
        OS os = osRepo.findById(dto.getOsId())
                .orElseThrow(() -> new ResourceNotFoundException("OS not found: " + dto.getOsId()));
        Plans plan = plansRepo.findById(dto.getPlanId())
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found: " + dto.getPlanId()));
        server.setServerName(dto.getServerName());
        server.setCountry(dto.getCountry());
        server.setCity(dto.getCity());
        server.setIpv4(dto.getIpv4());
        server.setIpv6(dto.getIpv6());

        server.setUser(user);
        server.setStatus(status);
        server.setOs(os);
        server.setPlan(plan);
        return serverRepo.save(server);
    }

    // D: Delete
    public void deleteById(Long id) {
        if (!serverRepo.existsById(id)) {
            throw new ResourceNotFoundException("Server not found: " + id);
        }
        serverRepo.deleteById(id);
    }
}