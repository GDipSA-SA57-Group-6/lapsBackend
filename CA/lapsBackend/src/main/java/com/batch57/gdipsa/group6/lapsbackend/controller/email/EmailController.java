package com.batch57.gdipsa.group6.lapsbackend.controller.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.batch57.gdipsa.group6.lapsbackend.model.enumLayer.APPLICATION_STATUS;
import com.batch57.gdipsa.group6.lapsbackend.serviceLayer.email.emailSendingImplementation;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private emailSendingImplementation emailService;

    /**
     * 发送简单的邮件消息
     *
     * @param to      接收者的邮件地址
     * @param subject 邮件主题
     * @param text    邮件内容
     * @return 响应实体
     */
    @PostMapping("/send")
    public ResponseEntity<String> sendSimpleEmail(
            @RequestParam String to, 
            @RequestParam String subject,
            @RequestParam String htmlcontent) {
        if (to == null || to.isEmpty()) {
            throw new IllegalArgumentException("邮件地址不能为空");
        }
        try {
            emailService.sendHtmlMessage(to, subject, htmlcontent);
            return ResponseEntity.ok("邮件发送成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("邮件发送失败: " + e.getMessage());
        }
    }

    @PostMapping("/sendVacationResultEmail")
    public ResponseEntity<String> sendVacationResultEmail(
            @RequestParam String to,
            @RequestParam String employeeName,
            @RequestParam String reviewedComment,
            @RequestParam APPLICATION_STATUS status) {
        String subject = "Notification of results of leave applications";
        String htmlContent = (status==APPLICATION_STATUS.CANCELLED?generateRejectedVacationEmail(employeeName, reviewedComment):generateApprovedVacationEmail(employeeName));
        return sendSimpleEmail(to, subject, htmlContent);
    }

    @PostMapping("/sendLeaveRequestNotificationEmail")
    public ResponseEntity<String> sendLeaveRequestNotificationEmail(
            @RequestParam String to,
            @RequestParam String managerName,
            @RequestParam String employeeName) {
        String subject = "Notification of Employee's Request for Leave of Absence";
        String htmlContent = generateLeaveRequestNotificationEmail(managerName, employeeName);
        return sendSimpleEmail(to, subject, htmlContent);
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

