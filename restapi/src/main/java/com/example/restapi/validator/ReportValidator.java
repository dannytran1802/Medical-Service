package com.example.restapi.validator;

import com.example.restapi.model.dto.ReportDTO;
import com.example.restapi.utils.DateUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

@Component
public class ReportValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return ReportDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
            ReportDTO reportDTO = (ReportDTO) target;

            if (reportDTO.getStartDate() != null && !reportDTO.getStartDate().trim().isEmpty() &&
                    reportDTO.getEndDate() != null && !reportDTO.getEndDate().trim().isEmpty()) {
                Date dateStart = DateUtil.convertStringToDate(reportDTO.getStartDate(), "yyyy-MM-dd");
                Date dateEnd = DateUtil.convertStringToDate(reportDTO.getEndDate(), "yyyy-MM-dd");
                int resultCheck = dateStart.compareTo(dateEnd);
                if (resultCheck > 0) {
                    errors.rejectValue("startDate", "report.startDate.valid", "report.startDate.valid");
                }
            }
        } catch (Exception e) {

        }
    }

}
