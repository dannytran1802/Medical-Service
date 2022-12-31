package com.example.restapi.controller;

import com.example.restapi.model.dto.PharmacyDTO;
import com.example.restapi.model.dto.RestReponseDTO;
import com.example.restapi.model.entity.Pharmacy;
import com.example.restapi.model.mapper.PharmacyMapper;
import com.example.restapi.service.AccountService;
import com.example.restapi.service.PharmacyService;
import com.example.restapi.utils.ValidatorUtil;
import com.example.restapi.validator.PharmacyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pharmacy")
public class PharmacyController {

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PharmacyMapper pharmacyMapper;

    @Autowired
    private PharmacyValidator pharmacyValidator;

    @Autowired
    private ValidatorUtil validatorUtil;

    @GetMapping("/list")
    public ResponseEntity<RestReponseDTO> list() {
        RestReponseDTO restResponse = new RestReponseDTO();
        List<Pharmacy> pharmacyList = pharmacyService.findAll();

        restResponse.ok(pharmacyMapper.toListDTO(pharmacyList));
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RestReponseDTO> findById(@PathVariable long id){
        RestReponseDTO restResponse = new RestReponseDTO();
        Pharmacy pharmacy = pharmacyService.findById(id);

        if (pharmacy == null) {
            restResponse.fail();
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }
        restResponse.ok(pharmacyMapper.toDTO(pharmacy));
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<RestReponseDTO> save(@RequestBody PharmacyDTO pharmacyDTO, BindingResult bindingResult){
        RestReponseDTO restResponse = new RestReponseDTO();

        pharmacyValidator.validate(pharmacyDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            restResponse.fail(validatorUtil.toErrors(bindingResult.getFieldErrors()));
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }

        Pharmacy pharmacy = pharmacyService.findById(pharmacyDTO.getId());

        if (pharmacy == null) {
            pharmacy = new Pharmacy();
        }
        pharmacy.setName(pharmacyDTO.getName());
        pharmacy.setAddress(pharmacyDTO.getAddress());
        pharmacy.setAccount(accountService.findById(pharmacyDTO.getAccountId()));
        pharmacy.setStatus(pharmacyDTO.isStatus());

        pharmacyService.save(pharmacy);

        restResponse.ok(pharmacyMapper.toDTO(pharmacy));
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<RestReponseDTO> delete(@PathVariable long id){
        RestReponseDTO restResponse = new RestReponseDTO();
        Pharmacy pharmacy = pharmacyService.findById(id);

        if (pharmacy == null) {
            restResponse.fail();
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }
        pharmacy.setStatus(false);
        pharmacyService.save(pharmacy);

        restResponse.ok(pharmacyMapper.toDTO(pharmacy));
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }
}
