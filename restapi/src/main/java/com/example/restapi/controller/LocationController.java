package com.example.restapi.controller;

import com.example.restapi.model.dto.LocationDTO;
import com.example.restapi.model.dto.RestReponseDTO;
import com.example.restapi.model.entity.Account;
import com.example.restapi.model.entity.Location;
import com.example.restapi.model.mapper.LocationMapper;
import com.example.restapi.service.AccountService;
import com.example.restapi.service.LocationService;
import com.example.restapi.utils.ValidatorUtil;
import com.example.restapi.validator.LocationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private LocationValidator locationValidator;


    @Autowired
    private ValidatorUtil validatorUtil;

    @GetMapping("/list")
    public ResponseEntity<RestReponseDTO> getList() {
        RestReponseDTO restReponse = new RestReponseDTO();
        restReponse.ok(locationMapper.toListDTO(locationService.findAll()));
        return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RestReponseDTO> findById(@PathVariable long id) {
        RestReponseDTO restReponse = new RestReponseDTO();

        Location location = locationService.findById(id);
        if (location == null) {
            restReponse.fail();
        } else {
            restReponse.ok(locationMapper.toDTO(location));
        }

        return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
    }

    @PostMapping("/save")
        public ResponseEntity<RestReponseDTO> save(@RequestBody LocationDTO locationDTO, BindingResult bindingResult) {
        RestReponseDTO restReponse = new RestReponseDTO();

        // validator
        locationValidator.validate(locationDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            restReponse.fail(validatorUtil.toErrors(bindingResult.getFieldErrors()));
            return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
        }

        Location location = locationService.findById(locationDTO.getAccountId());

        if (location == null) {
            location = new Location();
        }

        location.setLongitude( locationDTO.getLongitude() );
        location.setAccount(accountService.findById(locationDTO.getAccountId()));
        location.setLatitude( locationDTO.getLatitude());

        restReponse.ok(locationMapper.toDTO(locationService.save(location)));

        return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
    }

    @PostMapping("/status")
    public ResponseEntity<RestReponseDTO> updateStatus(@RequestBody LocationDTO locationDTO, BindingResult bindingResult) {
        RestReponseDTO restReponse = new RestReponseDTO();

        Location location = locationService.findByAccountId(locationDTO.getAccountId());
        if (location == null) {
            Account account = accountService.findById(locationDTO.getAccountId());

            location = new Location();
            location.setAccount(account);
        }

        location.setLongitude(locationDTO.getLongitude());
        location.setLatitude(locationDTO.getLatitude());
        location.setStatus(locationDTO.isStatus());
        locationService.save(location);

        restReponse.ok(locationMapper.toDTO(location));

        return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
    }

}
