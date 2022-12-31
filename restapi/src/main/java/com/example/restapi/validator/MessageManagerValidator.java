package com.example.restapi.validator;

import com.example.restapi.model.dto.MessageManagerDTO;
import com.example.restapi.service.MessageManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MessageManagerValidator implements Validator {
    @Autowired
    MessageManagerService messageService;

    @Override
    public boolean supports(Class<?> aClass) {
        return MessageManagerDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MessageManagerDTO messageDTO = (MessageManagerDTO) target;
        // validator title
        if(StringUtils.isEmpty(messageDTO.getTitle())){
            errors.rejectValue("title", "message.title.blank",
                    "message.title.blank");
        }
        // validator content
        if(StringUtils.isEmpty(messageDTO.getContent())){
            errors.rejectValue("content", "message.content.blank",
                    "message.content.blank");
        }
    }
}
