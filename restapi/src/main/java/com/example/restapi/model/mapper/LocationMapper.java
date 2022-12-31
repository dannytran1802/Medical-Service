package com.example.restapi.model.mapper;

import com.example.restapi.model.dto.LocationDTO;
import com.example.restapi.model.entity.Location;

import java.util.List;

public interface LocationMapper {

    // Map Entity to DTO
    LocationDTO toDTO(Location location);

    List<LocationDTO> toListDTO(List<Location> locations);

    // Map DTO to Entity
    Location toEntity(LocationDTO locationDTO);

}
