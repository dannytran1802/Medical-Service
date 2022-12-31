package com.example.restapi.validator;

import com.example.restapi.model.dto.AmbulanceDTO;
import com.example.restapi.model.entity.Ambulance;
import com.example.restapi.service.AmbulanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AmbulanceValidator implements Validator {
    @Autowired
    AmbulanceService ambulanceService;

    @Override
    public boolean supports(Class<?> aClass) {
        return AmbulanceDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AmbulanceDTO ambulanceDTO = (AmbulanceDTO) target;
        // validator name
        if(StringUtils.isEmpty(ambulanceDTO.getName())){
            errors.rejectValue("name", "ambulance.name.blank",
                    "ambulance.name.blank");
        }

        // validator number_plate
        if(StringUtils.isEmpty(ambulanceDTO.getNumberPlate())){
            errors.rejectValue("numberPlate", "ambulance.numberPlate.blank",
                    "ambulance.numberPlate.blank");
        } else {
            Ambulance ambulance = ambulanceService.findByNumberPlate(ambulanceDTO.getNumberPlate());
            if (ambulance != null && ambulanceDTO.getId() != ambulance.getId()) {
                errors.rejectValue("numberPlate", "ambulance.numberPlate.exist" ,"ambulance.numberPlate.exist");
            }
        }
    }
}
