package com.hrs.controller;

import com.hrs.model.dto.LoginDTO;
import com.hrs.model.dto.ReportDTO;
import com.hrs.model.dto.ReportValueDTO;
import com.hrs.model.reponse.BookingResponse;
import com.hrs.model.reponse.OrdersResponse;
import com.hrs.service.ReportService;
import com.hrs.utils.DateUtils;
import com.hrs.utils.FormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    DateUtils dateUtils;

    @GetMapping("/order")
    public  String reportOrders(Model model, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            model.addAttribute("loginDTO", new LoginDTO());
            model.addAttribute("error", "error");
            return "login";
        }
        ReportDTO reportDTO = new ReportDTO();
        Date date = new Date();
        String dateStr = dateUtils.convertDateToString("yyyy-MM-dd", date);
        reportDTO.setStartDate(dateStr);
        reportDTO.setEndDate(dateStr);

        List<OrdersResponse> ordersList = reportService.getReportOrders(token, reportDTO);
        setReportDTO(model, ordersList, null);

        model.addAttribute("ordersList", ordersList);
        model.addAttribute("reportDTO", reportDTO);
        model.addAttribute("errors", new HashMap<>());
        return "report_orders";
    }

    @GetMapping("/order/search")
    public String searchOrders(Model model, HttpSession session, ReportDTO reportDTO) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            model.addAttribute("loginDTO", new LoginDTO());
            model.addAttribute("error", "error");
            return "login";
        }
        String strDate = reportDTO.getStartDate();
        String enDate = reportDTO.getEndDate();
        reportDTO.setStartDate(strDate);
        reportDTO.setEndDate(enDate);
        List<OrdersResponse> ordersList = reportService.getReportOrders(token, reportDTO);
        setReportDTO(model, ordersList, null);

        model.addAttribute("ordersList", ordersList);
        return "report_orders";
    }

    @GetMapping("/booking")
    public  String reportBooking(Model model, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            model.addAttribute("loginDTO", new LoginDTO());
            model.addAttribute("error", "error");
            return "login";
        }
        ReportDTO reportDTO = new ReportDTO();
        Date date = new Date();
        String dateStr = dateUtils.convertDateToString("yyyy-MM-dd", date);
        reportDTO.setStartDate(dateStr);
        reportDTO.setEndDate(dateStr);

        List<BookingResponse> bookingList = reportService.getReportBooking(token, reportDTO);
        setReportDTO(model, null, bookingList);

        model.addAttribute("bookingList", bookingList);
        model.addAttribute("reportDTO", reportDTO);
        model.addAttribute("errors", new HashMap<>());
        return "report_booking";
    }

    @GetMapping("/booking/search")
    public String searchBooking(Model model, HttpSession session, ReportDTO reportDTO) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            model.addAttribute("loginDTO", new LoginDTO());
            model.addAttribute("error", "error");
            return "login";
        }
        String strDate = reportDTO.getStartDate();
        String enDate = reportDTO.getEndDate();
        reportDTO.setStartDate(strDate);
        reportDTO.setEndDate(enDate);
        List<BookingResponse> bookingList = reportService.getReportBooking(token, reportDTO);
        setReportDTO(model, null, bookingList);

        model.addAttribute("bookingList", bookingList);
        return "report_booking";
    }

    public void setReportDTO(Model model, List<OrdersResponse> orders, List<BookingResponse> booking) {
        try {
            ReportValueDTO reportValueDTO = new ReportValueDTO();
            if (!CollectionUtils.isEmpty(orders)) {
                reportValueDTO.setOrderTotal(orders.size());

                List<OrdersResponse> ordersPending = orders.stream()
                        .filter(object -> object.getProgress().equals("PENDING"))
                        .collect(Collectors.toList());
                reportValueDTO.setOrderPending(ordersPending.size());

                List<OrdersResponse> ordersApproved = orders.stream()
                        .filter(object -> object.getProgress().equals("APPROVED"))
                        .collect(Collectors.toList());
                reportValueDTO.setOrderApproved(ordersApproved.size());

                double totalPrice = orders.stream().mapToDouble(i -> FormatUtil.formatNumber(i.getTotalCost())).sum();
                reportValueDTO.setTotalPrice(FormatUtil.formatNumber(totalPrice));
            } else if (!CollectionUtils.isEmpty(booking)) {
                reportValueDTO.setOrderTotal(booking.size());

                List<BookingResponse> bookingPending = booking.stream()
                        .filter(object -> object.getProgress().equals("PENDING"))
                        .collect(Collectors.toList());
                reportValueDTO.setOrderPending(bookingPending.size());

                List<BookingResponse> bookingApproved = booking.stream()
                        .filter(object -> object.getProgress().equals("APPROVED"))
                        .collect(Collectors.toList());
                reportValueDTO.setOrderApproved(bookingApproved.size());

                List<BookingResponse> bookingCancel = booking.stream()
                        .filter(object -> object.getProgress().equals("CANCEL"))
                        .collect(Collectors.toList());
                reportValueDTO.setOrderCancel(bookingCancel.size());
            }
            else {
                reportValueDTO.setOrderTotal(0);
                reportValueDTO.setOrderPending(0);
                reportValueDTO.setOrderApproved(0);
                reportValueDTO.setOrderCancel(0);
                reportValueDTO.setTotalPrice("0.0");
            }
            model.addAttribute("reportValueDTO", reportValueDTO);
        } catch (Exception ex) {
        }
    }

}
