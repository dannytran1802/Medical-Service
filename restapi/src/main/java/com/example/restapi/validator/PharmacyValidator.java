package com.example.restapi.validator;

import com.example.restapi.model.dto.PharmacyDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PharmacyValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PharmacyDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PharmacyDTO pharmacyDTO = (PharmacyDTO) target;

        try{
            // validator name
            if(StringUtils.isEmpty(pharmacyDTO.getName())){
                errors.rejectValue("name", "pharmacy.name.blank",
                        "pharmacy.name.blank");
            }

            // validator address
            if(StringUtils.isEmpty(pharmacyDTO.getAddress())){
                errors.rejectValue("address", "pharmacy.address.blank",
                        "pharmacy.address.blank");
            }
        } catch (Exception e){
            errors.rejectValue("msg", "pharmacy.error",
                    "pharmacy.error");
        }

    }

}
