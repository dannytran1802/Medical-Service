package com.example.restapi.controller;

import com.example.restapi.model.dto.MessageManagerDTO;
import com.example.restapi.model.dto.NotificationRequestDTO;
import com.example.restapi.model.dto.RestReponseDTO;
import com.example.restapi.model.entity.Account;
import com.example.restapi.model.entity.MessageManager;
import com.example.restapi.model.mapper.MessageManagerMapper;
import com.example.restapi.service.AccountService;
import com.example.restapi.service.MessageManagerService;
import com.example.restapi.service.NotificationService;
import com.example.restapi.utils.ValidatorUtil;
import com.example.restapi.validator.MessageManagerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private MessageManagerService messageService;

    @Autowired
    private MessageManagerMapper messageMapper;

    @Autowired
    private MessageManagerValidator messageValidator;

    @Autowired
    private ValidatorUtil validatorUtil;

    @GetMapping("/list")
    public ResponseEntity<RestReponseDTO> getList() {
        RestReponseDTO restReponse = new RestReponseDTO();
        restReponse.ok(messageMapper.toListDTO(messageService.findAll()));
        return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RestReponseDTO> findById(@PathVariable long id) {
        RestReponseDTO restReponse = new RestReponseDTO();

        MessageManager message = messageService.findById(id);
        if (message == null) {
            restReponse.fail();
        } else {
            restReponse.ok(messageMapper.toDTO(message));
        }

        return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<RestReponseDTO> save(@RequestBody MessageManagerDTO messageDTO, BindingResult bindingResult) {
        RestReponseDTO restReponse = new RestReponseDTO();

        // validator
        messageValidator.validate(messageDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            restReponse.fail(validatorUtil.toErrors(bindingResult.getFieldErrors()));
            return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
        }

        MessageManager message = messageService.findById(messageDTO.getId());

        if (message == null) {
            message = new MessageManager();
        }

        message.setId(messageDTO.getId());
        message.setTitle(messageDTO.getTitle());
        message.setContent(messageDTO.getContent());
        message.setStatus(messageDTO.isStatus());

        if (message.isStatus()) {
            List<Account> accountList = accountService.findAll();
            if (accountList != null && !accountList.isEmpty()) {
                List<String> targetMultiDevice = new ArrayList<>();
                for (Account account : accountList) {
                    if (account.getToken() != null) {
                        targetMultiDevice.add(account.getToken());
                    }
                }

                UUID uuid = UUID.randomUUID();

                NotificationRequestDTO notificationRequestDto = new NotificationRequestDTO();
                notificationRequestDto.setTitle(uuid + "||MSG||" + message.getTitle());
                notificationRequestDto.setBody(message.getContent());
                notificationRequestDto.setTargetMultiDevice(targetMultiDevice);
                notificationService.sendMultiToDevice(notificationRequestDto);
            }
        }

        restReponse.ok(messageMapper.toDTO(messageService.save(message)));

        return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<RestReponseDTO> delete(@PathVariable long id) {
        RestReponseDTO restReponse = new RestReponseDTO();

        MessageManager message = messageService.findById(id);
        if (message == null) {
            restReponse.fail();
        } else {
            message.setStatus(false);
            messageService.save(message);
            restReponse.ok(messageMapper.toDTO(message));
        }

        return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
    }

    @PostMapping("/token")
    public String sendPnsToDevice(@RequestBody NotificationRequestDTO notificationRequestDto) {
        return notificationService.sendPnsToDevice(notificationRequestDto);
    }

    @PostMapping("/topic")
    public String sendPnsToTopic(@RequestBody NotificationRequestDTO notificationRequestDto) {
        return notificationService.sendPnsToTopic(notificationRequestDto);
    }

}
