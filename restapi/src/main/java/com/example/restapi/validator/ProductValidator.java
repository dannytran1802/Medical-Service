package com.example.restapi.validator;

import com.example.restapi.model.dto.ProductDTO;
import com.example.restapi.utils.ValidatorUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProductValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
            ProductDTO productDTO = (ProductDTO) target;

            if (ValidatorUtil.isEmpty(productDTO.getName())) {
                errors.rejectValue("name", "product.name.blank",
                        "product.name.blank");
            }

            if (productDTO.getPrice() == null || productDTO.getPrice().trim().isEmpty()) {
                errors.rejectValue("price", "product.price.blank",
                        "product.price.blank");
            } else {
                if (productDTO.getPrice().contains(",")) {
                    productDTO.setPrice(productDTO.getPrice().replace(",", ""));
                }
                if (!ValidatorUtil.isNumeric(productDTO.getPrice())) {
                    errors.rejectValue("price", "product.price.number",
                            "product.name.number");
                }
            }
        } catch (Exception e){
            errors.rejectValue("msg", "product.error",
                    "product.error");
        }
    }

}
