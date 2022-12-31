package com.hrs.service.impl;

import com.hrs.model.dto.DashboardDTO;
import com.hrs.model.reponse.AmbulanceResponse;
import com.hrs.model.reponse.BookingResponse;
import com.hrs.model.reponse.OrdersResponse;
import com.hrs.model.reponse.PharmacyResponse;
import com.hrs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    AmbulanceService ambulanceService;

    @Autowired
    BookingService bookingService;

    @Autowired
    PharmacyService pharmacyService;

    @Autowired
    OrdersService ordersService;

    @Override
    public DashboardDTO showDashboard(HttpSession session) {
        int totalAmbulance = 0;
        int totalBooking = 0;
        int totalPharmacy = 0;
        int totalOrder = 0;
        List<BookingResponse> bookingResponseListPending = new ArrayList<>();
        List<OrdersResponse> ordersResponses = new ArrayList<>();
        try {
            String token = "";
            if (session.getAttribute("token") != null) {
                token = session.getAttribute("token").toString();
                List<AmbulanceResponse> ambulanceResponseList = ambulanceService.getList(token);
                if(!CollectionUtils.isEmpty(ambulanceResponseList)) {
                totalAmbulance = ambulanceResponseList.size();
                }

                List<BookingResponse> bookingResponseList = bookingService.getList(token);
                if(!CollectionUtils.isEmpty(bookingResponseList)) {
                    totalBooking = bookingResponseList.size();
                }

                List<PharmacyResponse> pharmacyResponseList = pharmacyService.getList(token);
                if(!CollectionUtils.isEmpty(pharmacyResponseList)) {
                    totalPharmacy = pharmacyResponseList.size();
                }

                List<OrdersResponse> orderResponseList = ordersService.getList(token);
                if(!CollectionUtils.isEmpty(orderResponseList)) {
                    totalOrder = orderResponseList.size();
                }

                bookingResponseListPending = bookingService.getListProgressPending(token);
                ordersResponses = ordersService.getListProgressPending(token);
            }
        } catch (Exception ex) {

        }

        DashboardDTO dashboard = new DashboardDTO();
        dashboard.setTotalAmbulance(totalAmbulance);
        dashboard.setTotalBooking(totalBooking);
        dashboard.setTotalPharmacy(totalPharmacy);
        dashboard.setTotalOrder(totalOrder);
        dashboard.setBookingResponseList(bookingResponseListPending);
        dashboard.setOrdersResponseList(ordersResponses);

        return dashboard;
    }

}
