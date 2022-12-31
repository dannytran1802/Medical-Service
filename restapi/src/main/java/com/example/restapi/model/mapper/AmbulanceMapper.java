package com.example.restapi.model.mapper;

import com.example.restapi.model.dto.AmbulanceDTO;
import com.example.restapi.model.entity.Ambulance;

import java.util.List;

public interface AmbulanceMapper {

    // Map Entity to DTO
    AmbulanceDTO toDTO(Ambulance ambulance);

    List<AmbulanceDTO> toListDTO(List<Ambulance> ambulances);

    // Map DTO to Entity
    Ambulance toEntity(AmbulanceDTO ambulanceDTO);

}
