package com.hrs.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hrs.model.reponse.DTOResponse;
import com.hrs.model.reponse.OrdersResponse;
import com.hrs.model.reponse.OrdersResponse;
import com.hrs.model.reponse.OrdersResponse;
import com.hrs.service.OrdersService;
import com.hrs.utils.ConstantUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<OrdersResponse> getList(String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(token);
            String url = ConstantUtils.HOST_URL + "api/orders/list";
            HttpEntity<String> entity = new HttpEntity<>(token, headers);
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
            ObjectMapper objectMapper = new ObjectMapper();
            String respData = objectMapper.writeValueAsString(response.getBody());

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type typeData = new TypeToken<DTOResponse<List<OrdersResponse>>>() {}.getType();
            DTOResponse<List<OrdersResponse>> dtoResponse = gson.fromJson(respData, typeData);
            return dtoResponse.getData();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<OrdersResponse> getListProgressPending(String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(token);
            String url = ConstantUtils.HOST_URL + "api/orders/find-progress";
            HttpEntity<String> entity = new HttpEntity<>(token, headers);
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
            ObjectMapper objectMapper = new ObjectMapper();
            String respData = objectMapper.writeValueAsString(response.getBody());

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type typeData = new TypeToken<DTOResponse<List<OrdersResponse>>>() {}.getType();
            DTOResponse<List<OrdersResponse>> dtoResponse = gson.fromJson(respData, typeData);
            return dtoResponse.getData();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public OrdersResponse getById(String token, long id) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(token);
            String url = ConstantUtils.HOST_URL + "api/orders/id/"+id;
            HttpEntity<String> entity = new HttpEntity<>(token, headers);
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
            ObjectMapper objectMapper = new ObjectMapper();
            String respData = objectMapper.writeValueAsString(response.getBody());

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type typeData = new TypeToken<DTOResponse<OrdersResponse>> () {}.getType();
            DTOResponse<OrdersResponse> dtoResponse = gson.fromJson(respData, typeData);
            return dtoResponse.getData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
