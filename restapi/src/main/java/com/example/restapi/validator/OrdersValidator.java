package com.example.restapi.validator;

import com.example.restapi.model.dto.OrdersDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class OrdersValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return OrdersDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OrdersDTO ordersDTO = (OrdersDTO) target;

        try{
            // validator name
            if(StringUtils.isEmpty(ordersDTO.getAddress())){
                errors.rejectValue("address", "orders.address.blank",
                        "orders.address.blank");
            }
        } catch (Exception e){
            errors.rejectValue("msg", "orders.error",
                    "orders.error");
        }
    }
}
