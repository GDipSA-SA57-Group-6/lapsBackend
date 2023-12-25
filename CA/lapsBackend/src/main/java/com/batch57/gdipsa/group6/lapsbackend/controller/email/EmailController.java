package com.batch57.gdipsa.group6.lapsbackend.controller.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam String text) {
        if (to == null || to.isEmpty()) {
            throw new IllegalArgumentException("邮件地址不能为空");
        }
        try {
            emailService.sendSimpleMessage(to, subject, text);
            return ResponseEntity.ok("邮件发送成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("邮件发送失败: " + e.getMessage());
        }
    }
}

