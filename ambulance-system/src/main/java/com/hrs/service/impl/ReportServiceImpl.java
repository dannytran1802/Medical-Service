package com.hrs.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hrs.model.dto.ReportDTO;
import com.hrs.model.reponse.BookingResponse;
import com.hrs.model.reponse.DTOResponse;
import com.hrs.model.reponse.OrdersResponse;
import com.hrs.service.ReportService;
import com.hrs.utils.ConstantUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<OrdersResponse> getReportOrders(String token, ReportDTO reportDTO) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(token);
            HttpEntity<ReportDTO> entity = new HttpEntity<>(reportDTO, headers);
            String url = ConstantUtils.HOST_URL + "api/reports/order";
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
            ObjectMapper objectMapper = new ObjectMapper();
            String respData = objectMapper.writeValueAsString(response.getBody());

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type typeData = new TypeToken<DTOResponse<List<OrdersResponse>>>() {
            }.getType();
            DTOResponse<List<OrdersResponse>> dtoResponse = gson.fromJson(respData, typeData);
            return dtoResponse.getData();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<BookingResponse> getReportBooking(String token, ReportDTO reportDTO) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(token);
            HttpEntity<ReportDTO> entity = new HttpEntity<>(reportDTO, headers);
            String url = ConstantUtils.HOST_URL + "api/reports/booking";
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
            ObjectMapper objectMapper = new ObjectMapper();
            String respData = objectMapper.writeValueAsString(response.getBody());

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type typeData = new TypeToken<DTOResponse<List<BookingResponse>>>() {
            }.getType();
            DTOResponse<List<BookingResponse>> dtoResponse = gson.fromJson(respData, typeData);
            return dtoResponse.getData();
        } catch (Exception ex) {
            return null;
        }
    }
}
