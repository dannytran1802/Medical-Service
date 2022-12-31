package com.example.restapi.service.impl;

import com.example.restapi.model.dto.EmailAccountDTO;
import com.example.restapi.model.dto.MailDTO;
import com.example.restapi.service.EmailService;
import com.example.restapi.utils.ContantUtil;
import com.example.restapi.utils.DateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    SpringTemplateEngine templateEngine;

    private Map<String, Object> properties =  new HashMap<>();

    @Override
    public boolean sendEmailForFogotPassword(EmailAccountDTO emailDTO) {
        try {
            Thread thread = new Thread(){
                public void run(){
                    try {
                        MimeMessage message = javaMailSender.createMimeMessage();
                        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
                        //enCoder base64
                        Date date = new Date();
                        MailDTO mailDTO = new MailDTO();
                        mailDTO.setTime(DateUtil.convertDateToString(date, "HH:mm:ss dd-MM-yyyy"));
                        mailDTO.setData(emailDTO);
                        mailDTO.setMinutes(30);
                        ObjectMapper objectMapper = new ObjectMapper();
                        String result = objectMapper.writeValueAsString(mailDTO);
                        String linkActive = ContantUtil.HOST_URL + "/api/auth/reset-pass?ref=" + encoderStringToBase64(result);

                        properties.put("fullname", emailDTO.getFullname());
                        properties.put("email", emailDTO.getEmail());
                        properties.put("link", linkActive);

                        Context context = new Context();
                        context.setVariables(properties);

                        String html = templateEngine.process("mail/forgot_password.html", context);

                        helper.setFrom("noreply@ambulance.vn");
                        helper.setTo(emailDTO.getEmail());
                        helper.setSubject("Yêu Cầu Cấp Lại Mật Khẩu Tại www.ambulance.vn");
                        helper.setText(html, true);

                        javaMailSender.send(message);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            };
            thread.start();

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean sendEmailForResetPassword(EmailAccountDTO emailDTO) {
        try {
            Thread thread = new Thread(){
                public void run(){
                    try {
                        MimeMessage message = javaMailSender.createMimeMessage();
                        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

                        properties.put("fullName", emailDTO.getFullname());
                        properties.put("email", emailDTO.getEmail());
                        properties.put("password", emailDTO.getPassword());

                        Context context = new Context();
                        context.setVariables(properties);

                        String html = templateEngine.process("mail/reset_password.html", context);

                        helper.setFrom("noreply@ambulance.vn");
                        helper.setTo(emailDTO.getEmail());
                        helper.setSubject("Yêu Cầu Cấp Lại Mật Khẩu Tại Ambulance");
                        helper.setText(html, true);

                        javaMailSender.send(message);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            };
            thread.start();

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean sendEmailForOtp(EmailAccountDTO emailDTO) {
        try {
            Thread thread = new Thread(){
                public void run(){
                    try {
                        MimeMessage message = javaMailSender.createMimeMessage();
                        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

                        properties.put("fullName", emailDTO.getUsername());
                        properties.put("otp", emailDTO.getOtp());

                        Context context = new Context();
                        context.setVariables(properties);

                        String html = templateEngine.process("mail/welcome_otp.html", context);

                        helper.setFrom("noreply@ambulance.vn");
                        helper.setTo(emailDTO.getEmail());
                        helper.setSubject("Mã Xác Nhận OTP Tại Ambulance");
                        helper.setText(html, true);

                        javaMailSender.send(message);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            };
            thread.start();

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public String encoderStringToBase64(String text) {
        Base64.Encoder encoder = Base64.getEncoder();
        String encodedString = encoder.encodeToString(text.getBytes(StandardCharsets.UTF_8) );
        return encodedString;
    }

    public static String decoderBase64ToString(String text) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedByteArray = decoder.decode(text);
        return new String(decodedByteArray);
    }

}
