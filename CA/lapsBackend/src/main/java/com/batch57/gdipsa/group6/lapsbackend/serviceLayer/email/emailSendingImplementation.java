package com.batch57.gdipsa.group6.lapsbackend.serviceLayer.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.batch57.gdipsa.group6.lapsbackend.model.enumLayer.APPLICATION_STATUS;

@Service
public class emailSendingImplementation {

    @Autowired
    private JavaMailSender emailSender;

    public void sendHtmlMessage(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("zoud@u.nus.edu"); // 替换为您的发件人地址
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // 第二个参数为 true 表示发送 HTML

            emailSender.send(message);
        } catch (MessagingException e) {
            // 处理异常
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }

    public void sendVacationResultEmail(String to, String employeeName, String reviewedComment, APPLICATION_STATUS status) {
        String subject = "Notification of results of leave applications";
        String htmlContent = (status==APPLICATION_STATUS.REJECTED?generateRejectedVacationEmail(employeeName, reviewedComment):generateApprovedVacationEmail(employeeName));
        sendHtmlMessage(to, subject, htmlContent);
    }

    public void sendLeaveRequestNotificationEmail(String to, String managerName, String employeeName) {
        String subject = "Notification of Employee's Request for Leave of Absence";
        String htmlContent = generateLeaveRequestNotificationEmail(managerName, employeeName);
        sendHtmlMessage(to, subject, htmlContent);
    }

    public static String generateRejectedVacationEmail(String employeeName, String reviewedComment) {
        return "<!DOCTYPE html>"
                + "<html><head>" + formalHolidayRelatedEmailStyle + "</head><body>"
                + "<div class='email-container'>"
                + "<div class='header'>Vacation Request Notification</div>"
                + "<div class='body'>"
                + "<p>Dear " + employeeName + ",</p>"
                + "<p>Sorry, your vacation request has been <strong>REJECTED</strong>. The reason is:</p>"
                + "<blockquote>" + reviewedComment + "</blockquote>"
                + "<p>Please log in to the system to see the details.</p>"
                + "</div>"
                + "<div class='footer'>This is an automated message. Please do not reply.</div>"
                + "</div></body></html>";
    }

    private static String generateApprovedVacationEmail(String employeeName) {
        return "<!DOCTYPE html>"
                + "<html><head>" + formalHolidayRelatedEmailStyle + "</head><body>"
                + "<div class='email-container'>"
                + "<div class='header'>Vacation Request Approved</div>"
                + "<div class='body'>"
                + "<p>Dear " + employeeName + ",</p>"
                + "<p>Congratulations, your vacation request has been <strong>APPROVED</strong>.</p>"
                + "<p>Please log in to the system to see the details.</p>"
                + "</div>"
                + "<div class='footer'>This is an automated message. Please do not reply.</div>"
                + "</div></body></html>";
    }

    public static String generateLeaveRequestNotificationEmail(String managerName, String employeeName) {
        return "<!DOCTYPE html>"
                + "<html><head>" + formalHolidayRelatedEmailStyle + "</head><body>"
                + "<div class='email-container'>"
                + "<div class='header'>Leave of Absence Request Notification</div>"
                + "<div class='body'>"
                + "<p>Dear " + managerName + ",</p>"
                + "<p>Employee <strong>" + employeeName + "</strong> has sent a request for leave of absence.</p>"
                + "<p>Please log in to the system to see the details and take necessary actions.</p>"
                + "</div>"
                + "<div class='footer'>This is an automated message. Please do not reply.</div>"
                + "</div></body></html>";
    }


    private static String formalHolidayRelatedEmailStyle = "<style> .email-container { font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; } .header { background-color: #4CAF50; color: white; padding: 10px; text-align: center; border-radius: 5px 5px 0 0; } .body { padding: 20px; text-align: left; } .footer { text-align: center; font-size: 0.8em; padding: 15px; background-color: #f0f0f0; border-radius: 0 0 5px 5px; } </style>";

}
