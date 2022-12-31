package com.example.restapi.model.mapper.impl;

import com.example.restapi.model.dto.AccountDTO;
import com.example.restapi.model.dto.LocationDTO;
import com.example.restapi.model.entity.Account;
import com.example.restapi.model.entity.Location;
import com.example.restapi.model.mapper.LocationMapper;
import com.example.restapi.service.AccountService;
import com.example.restapi.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LocationMapperImpl implements LocationMapper {

    @Autowired
    private LocationService locationService;

    @Autowired
    private AccountService accountService;

    @Override
    public LocationDTO toDTO(Location location) {
        if (location == null) {
            return null;
        }
        LocationDTO locationDTO = new LocationDTO();

        locationDTO.setId( location.getId() );
        locationDTO.setLatitude(location.getLatitude());
        locationDTO.setLongitude(location.getLongitude());

        AccountDTO accountDTO = new AccountDTO();
        Account account = location.getAccount();
        if (account != null) {
            accountDTO.setId(account.getId());
            accountDTO.setFullName(account.getFullName());
            accountDTO.setEmail(account.getEmail());
            locationDTO.setAccountDTO(accountDTO);
            locationDTO.setAccountId(account.getId());
        }
        return locationDTO;
    }

    @Override
    public List<LocationDTO> toListDTO(List<Location> locations) {
        if (locations == null) {
            return null;
        }
        List<LocationDTO> list = new ArrayList<>(locations.size());
        for (Location location : locations) {
            LocationDTO locationDTO = toDTO(location);
            if (locationDTO != null) {
                list.add(locationDTO);
            }
        }

        return list;
    }

    @Override
    public Location toEntity(LocationDTO locationDTO) {
        Location location = locationService.findById(locationDTO.getId());

        if (location == null){
            location = new Location();
        }
        location.setLatitude(locationDTO.getLatitude());
        location.setLongitude(locationDTO.getLongitude());
        location.setAccount(accountService.findById(locationDTO.getAccountId()));

        return location;
    }
}
