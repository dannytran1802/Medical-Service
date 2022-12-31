package com.hrs.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hrs.model.dto.AccountDTO;
import com.hrs.model.dto.LoginDTO;
import com.hrs.model.reponse.AccountResponse;
import com.hrs.model.reponse.DTOResponse;
import com.hrs.model.reponse.UserResponse;
import com.hrs.service.AccountService;
import com.hrs.utils.ConstantUtils;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getHome() {
        String url = ConstantUtils.HOST_URL;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    @Override
    public UserResponse login(LoginDTO loginDTO) {
        try {
            HttpHeaders headers = new HttpHeaders();
            MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
            map.add("username", loginDTO.getUsername());
            map.add("password", loginDTO.getPassword());
            map.add("grant_type", "password");
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("Authorization", "Basic cmVzdGFwcDpwYXNzd29yZA==");
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
            String url = ConstantUtils.HOST_URL + "oauth/token";
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, request, Object.class);
            ObjectMapper mapper = new ObjectMapper();
            UserResponse result = mapper.convertValue(response.getBody(), UserResponse.class);

            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<AccountResponse> getList(String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(token);
            headers.add("Authorization", "Basic cmVzdGFwcDpwYXNzd29yZA==");
            String url = ConstantUtils.HOST_URL + "api/accounts/";
            HttpEntity<String> entity = new HttpEntity<>(token, headers);
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
            ObjectMapper objectMapper = new ObjectMapper();
            String respData = objectMapper.writeValueAsString(response.getBody());
            DTOResponse result = objectMapper.readValue(respData, DTOResponse.class);
            List<AccountResponse> resultList = (List<AccountResponse>) result.getData();

            return resultList;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public AccountResponse getById(String token, long id) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(token);
            String url = ConstantUtils.HOST_URL + "api/accounts/id/"+id;
            HttpEntity<String> entity = new HttpEntity<>(token, headers);
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
            ObjectMapper objectMapper = new ObjectMapper();
            String respData = objectMapper.writeValueAsString(response.getBody());
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type typeData = new TypeToken<DTOResponse<AccountResponse>> () {}.getType();
            DTOResponse<AccountResponse> dtoResponse = gson.fromJson(respData, typeData);
            return dtoResponse.getData();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public String save(AccountDTO accountDTO, String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(token);
            HttpEntity<AccountDTO> entity = new HttpEntity<>(accountDTO, headers);
            String url = ConstantUtils.HOST_URL + "api/accounts/save";
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            return response.getBody();
        } catch (Exception ex) {
            return null;
        }
    }

}
