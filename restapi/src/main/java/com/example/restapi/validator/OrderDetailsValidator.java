package com.example.restapi.validator;

import com.example.restapi.model.dto.OrderDetailsDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class OrderDetailsValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return OrderDetailsDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        OrderDetailsDTO orderDetailsDTO = (OrderDetailsDTO) target;

        if (StringUtils.isEmpty(orderDetailsDTO.getPrice())) {
            errors.rejectValue("price", "orderDetail.price.blank",
                    "orderDetail.price.blank");
        }

        if (StringUtils.isEmpty(orderDetailsDTO.getQuantity())) {
            errors.rejectValue("quantity", "orderDetail.quantity.blank",
                    "orderDetail.quantity.blank");
        }
    }
}
