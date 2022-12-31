package com.example.restapi.model.mapper;

import com.example.restapi.model.dto.PharmacyDTO;
import com.example.restapi.model.entity.Pharmacy;

import java.util.List;

public interface PharmacyMapper {

    // Map Entity to DTO
    PharmacyDTO toDTO(Pharmacy pharmacy);

    List<PharmacyDTO> toListDTO(List<Pharmacy> pharmacys);

    // Map DTO to Entity
    Pharmacy toEntity(PharmacyDTO pharmacyDTO);
    
}
