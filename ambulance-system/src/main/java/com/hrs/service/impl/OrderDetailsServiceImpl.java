package com.hrs.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hrs.model.reponse.DTOResponse;
import com.hrs.model.reponse.OrderDetailsResponse;
import com.hrs.model.reponse.OrderDetailsResponse;
import com.hrs.service.OrderDetailsService;
import com.hrs.utils.ConstantUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {
    private RestTemplate restTemplate = new RestTemplate();
    
    @Override
    public List<OrderDetailsResponse> getListByOrder(String token, long ordersId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(token);
            String url = ConstantUtils.HOST_URL + "api/orderDetails/list/" + ordersId;
            HttpEntity<String> entity = new HttpEntity<>(token, headers);
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
            ObjectMapper objectMapper = new ObjectMapper();
            String respData = objectMapper.writeValueAsString(response.getBody());

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type typeData = new TypeToken<DTOResponse<List<OrderDetailsResponse>>>() {}.getType();
            DTOResponse<List<OrderDetailsResponse>> dtoResponse = gson.fromJson(respData, typeData);
            return dtoResponse.getData();
        } catch (Exception ex) {
            return null;
        }
    }

}
