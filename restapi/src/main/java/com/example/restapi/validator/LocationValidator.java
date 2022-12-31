package com.example.restapi.validator;

import com.example.restapi.model.dto.AmbulanceDTO;
import com.example.restapi.model.dto.LocationDTO;
import com.example.restapi.model.entity.Ambulance;
import com.example.restapi.service.AmbulanceService;
import com.example.restapi.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LocationValidator implements Validator {
    @Autowired
    LocationService locationService;

    @Override
    public boolean supports(Class<?> aClass) {
        return LocationDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LocationDTO locationDTO = (LocationDTO) target;
        // validator name
        if(locationDTO.getAccountDTO() == null){
            errors.rejectValue("account", "location.account.blank",
                    "location.account.blank");
        }

        // validator number_plate
//        if(ambulanceDTO.getNumberPlate()==null){
//            errors.rejectValue("numberPlate", "ambulance.numberPlate.blank",
//                    "ambulance.numberPlate.blank");
//        } else {
//            Ambulance ambulance = ambulanceService.findByNumberPlate(ambulanceDTO.getNumberPlate());
//            if (ambulance != null && ambulanceDTO.getId() != ambulance.getId()) {
//                errors.rejectValue("numberPlate", "ambulance.numberPlate.exist" ,"ambulance.numberPlate.exist");
//            }
        }
    }

