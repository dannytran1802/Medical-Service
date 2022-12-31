package com.example.restapi.controller;

import com.example.restapi.model.dto.AmbulanceDTO;
import com.example.restapi.model.dto.RestReponseDTO;
import com.example.restapi.model.entity.Ambulance;
import com.example.restapi.model.mapper.AmbulanceMapper;
import com.example.restapi.service.AccountService;
import com.example.restapi.service.AmbulanceService;
import com.example.restapi.utils.ValidatorUtil;
import com.example.restapi.validator.AmbulanceValidator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/ambulance")
public class AmbulanceController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AmbulanceService ambulanceService;

    @Autowired
    private AmbulanceMapper ambulanceMapper;

    @Autowired
    private AmbulanceValidator ambulanceValidator;


    @Autowired
    private ValidatorUtil validatorUtil;

    @GetMapping("/list")
    public ResponseEntity<RestReponseDTO> getList() {
        RestReponseDTO restReponse = new RestReponseDTO();
        restReponse.ok(ambulanceMapper.toListDTO(ambulanceService.findAll()));
        return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RestReponseDTO> findById(@PathVariable long id) {
        RestReponseDTO restReponse = new RestReponseDTO();

        Ambulance ambulance = ambulanceService.findById(id);
        if (ambulance == null) {
            restReponse.fail();
        } else {
            restReponse.ok(ambulanceMapper.toDTO(ambulance));
        }

        return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<RestReponseDTO> save(@RequestBody AmbulanceDTO ambulanceDTO, BindingResult bindingResult) {
        RestReponseDTO restReponse = new RestReponseDTO();

        // validator
        ambulanceValidator.validate(ambulanceDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            restReponse.fail(validatorUtil.toErrors(bindingResult.getFieldErrors()));
            return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
        }

        Ambulance ambulance = ambulanceService.findById(ambulanceDTO.getId());

        if (ambulance == null) {
            ambulance = new Ambulance();
        }

        ambulance.setId( ambulanceDTO.getId() );
        ambulance.setName( ambulanceDTO.getName() );
        ambulance.setAccount(accountService.findById(ambulanceDTO.getAccountId()));
        ambulance.setNumberPlate( ambulanceDTO.getNumberPlate() );
        ambulance.setStatus( ambulanceDTO.isStatus() );

//        if (productDTO.getAvatarMul() != null && !StringUtils.isEmpty(productDTO.getAvatarMul().getOriginalFilename())) {
//            String urlImage = uploadFileUtil.uploadFileResultFileName(productDTO.getAvatarMul());
//            product.setImage(urlImage);
//        }

        restReponse.ok(ambulanceMapper.toDTO(ambulanceService.save(ambulance)));

        return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<RestReponseDTO> delete(@PathVariable long id) {
        RestReponseDTO restReponse = new RestReponseDTO();

        Ambulance ambulance = ambulanceService.findById(id);
        if (ambulance == null) {
            restReponse.fail();
        } else {
            ambulance.setStatus(false);
            ambulanceService.save(ambulance);
            restReponse.ok(ambulanceMapper.toDTO(ambulance));
        }

        return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
    }
}
