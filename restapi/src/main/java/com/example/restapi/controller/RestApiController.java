package com.example.restapi.controller;

import com.example.restapi.model.dto.RestReponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class RestApiController {

    @GetMapping("/test")
    public ResponseEntity<RestReponseDTO> testApi() {
        RestReponseDTO restReponse = new RestReponseDTO();
        Date date = new Date();
        restReponse.ok(date);
        return new ResponseEntity<RestReponseDTO>(restReponse, HttpStatus.OK);
    }

}
