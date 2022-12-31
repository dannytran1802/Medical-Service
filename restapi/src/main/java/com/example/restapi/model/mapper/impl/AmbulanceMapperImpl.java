package com.example.restapi.model.mapper.impl;

import com.example.restapi.model.dto.AccountDTO;
import com.example.restapi.model.dto.AmbulanceDTO;
import com.example.restapi.model.entity.Account;
import com.example.restapi.model.entity.Ambulance;
import com.example.restapi.model.mapper.AmbulanceMapper;
import com.example.restapi.service.AccountService;
import com.example.restapi.service.AmbulanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AmbulanceMapperImpl implements AmbulanceMapper {

    @Autowired
    private AmbulanceService ambulanceService;

    @Autowired
    private AccountService accountService;

    @Override
    public AmbulanceDTO toDTO(Ambulance ambulance) {
        if (ambulance == null) {
            return null;
        }
        AmbulanceDTO ambulanceDTO = new AmbulanceDTO();

        ambulanceDTO.setId( ambulance.getId() );
        ambulanceDTO.setName(ambulance.getName());
        ambulanceDTO.setNumberPlate(ambulance.getNumberPlate());
        ambulanceDTO.setStatus(ambulance.isStatus());

        AccountDTO accountDTO = new AccountDTO();
        Account account = ambulance.getAccount();
        if (account != null) {
            accountDTO.setId(account.getId());
            accountDTO.setFullName(account.getFullName());
            accountDTO.setEmail(account.getEmail());
            ambulanceDTO.setAccountDTO(accountDTO);
            ambulanceDTO.setAccountId(account.getId());
        }
        return ambulanceDTO;
    }

    @Override
    public List<AmbulanceDTO> toListDTO(List<Ambulance> ambulances) {
        if (ambulances == null) {
            return null;
        }
        List<AmbulanceDTO> list = new ArrayList<>(ambulances.size());
        for (Ambulance ambulance : ambulances) {
            AmbulanceDTO ambulanceDTO = toDTO(ambulance);
            if (ambulanceDTO != null) {
                list.add(ambulanceDTO);
            }
        }

        return list;
    }

    @Override
    public Ambulance toEntity(AmbulanceDTO ambulanceDTO) {
        Ambulance ambulance = ambulanceService.findById(ambulanceDTO.getId());

        if (ambulance == null){
            ambulance = new Ambulance();
        }
        ambulance.setName(ambulanceDTO.getName());
        ambulance.setStatus(ambulanceDTO.isStatus());
        ambulance.setNumberPlate(ambulanceDTO.getNumberPlate());
        ambulance.setAccount(accountService.findById(ambulanceDTO.getAccountId()));

        return ambulance;
    }
}
