package com.example.restapi.model.mapper.impl;

import com.example.restapi.model.dto.AccountDTO;
import com.example.restapi.model.dto.PharmacyDTO;
import com.example.restapi.model.entity.Account;
import com.example.restapi.model.entity.Pharmacy;
import com.example.restapi.model.mapper.PharmacyMapper;
import com.example.restapi.service.AccountService;
import com.example.restapi.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PharmacyMapperImpl implements PharmacyMapper {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PharmacyService pharmacyService;

    @Override
    public PharmacyDTO toDTO(Pharmacy pharmacy) {

        if (pharmacy == null) {
            return null;
        }

        PharmacyDTO pharmacyDTO = new PharmacyDTO();
        pharmacyDTO.setId(pharmacy.getId());
        pharmacyDTO.setName(pharmacy.getName());
        pharmacyDTO.setAddress(pharmacy.getAddress());
        pharmacyDTO.setStatus(pharmacy.isStatus());

        Account account = pharmacy.getAccount();
        AccountDTO accountDTO = new AccountDTO();

        if (account != null){
            accountDTO.setId(account.getId());
            accountDTO.setFullName(account.getFullName());
            accountDTO.setEmail(account.getEmail());

            pharmacyDTO.setAccountDTO(accountDTO);
            pharmacyDTO.setAccountId(account.getId());
        }

        return pharmacyDTO;
    }

    @Override
    public List<PharmacyDTO> toListDTO(List<Pharmacy> pharmacies) {
        if (pharmacies == null) {
            return null;
        }

        List<PharmacyDTO> list = new ArrayList<>(pharmacies.size());
        for (Pharmacy pharmacy : pharmacies) {
            PharmacyDTO pharmacyDTO = toDTO(pharmacy);
            if (pharmacyDTO != null) {
                list.add(pharmacyDTO);
            }
        }

        return list;
    }

    @Override
    public Pharmacy toEntity(PharmacyDTO pharmacyDTO) {
        Pharmacy pharmacy = pharmacyService.findById(pharmacyDTO.getId());

        if (pharmacy == null){
            pharmacy = new Pharmacy();
        }
        pharmacy.setName(pharmacyDTO.getName());
        pharmacy.setAddress(pharmacyDTO.getAddress());
        pharmacy.setAccount(accountService.findById(pharmacyDTO.getAccountId()));
        pharmacy.setStatus(pharmacyDTO.isStatus());

        return pharmacy;
    }

}
