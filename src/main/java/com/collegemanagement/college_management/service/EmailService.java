package com.collegemanagement.college_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Login Notification
    public void sendLoginNotification(String toEmail, String userName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Login Alert");
        message.setText("Hello " + userName + ",\n\nYou have successfully logged into Edukita college portal.\n\nIf this wasn't you, please contact support.");
        mailSender.send(message);
    }

    // Leave Application Notification to Student
    public void sendLeaveApplicationEmail(String toEmail, String studentName, String reason, String leaveDate, String returnDate) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Leave Application Submitted");
        message.setText("Hello " + studentName + ",\n\n" +
                "Your leave application has been submitted successfully.\n\n" +
                "Reason: " + reason + "\n" +
                "Leave From: " + leaveDate + "\n" +
                "Return Date: " + returnDate + "\n\n" +
                "We’ll notify you once it’s reviewed.\n\nThanks,\nEduKita College");
        mailSender.send(message);
    }

    // Notify Coordinator of Leave Request
    public void sendLeaveRequestToCoordinator(String coordinatorEmail, String studentName, String reason, String leaveDate, String returnDate) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(coordinatorEmail);
        message.setSubject("Leave Application Submitted by " + studentName);
        message.setText("Dear Coordinator,\n\n" +
                "A leave application has been submitted by " + studentName + ".\n\n" +
                "Reason: " + reason + "\n" +
                "Leave From: " + leaveDate + "\n" +
                "Return Date: " + returnDate + "\n\n" +
                "Please review and take appropriate action.\n\nThanks,\nEduKita College");
        mailSender.send(message);
    }

    // Notify Student of Leave Approval
    public void sendLeaveApprovalNotification(String toEmail, String studentName, String leaveDate, String returnDate) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Leave Application Approved");
        message.setText("Hello " + studentName + ",\n\n" +
                "Your leave application has been approved.\n\n" +
                "Leave From: " + leaveDate + "\n" +
                "Return Date: " + returnDate + "\n\n" +
                "You are now officially on leave.\n\nThanks,\nEduKita College");
        mailSender.send(message);
    }

    // Notify Student of Leave Rejection
    public void sendLeaveRejectionNotification(String toEmail, String studentName, String reason, String leaveDate, String returnDate) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Leave Application Rejected");
        message.setText("Hello " + studentName + ",\n\n" +
                "Unfortunately, your leave application has been rejected.\n\n" +
                "Reason for rejection: " + reason + "\n" +
                "Leave From: " + leaveDate + "\n" +
                "Return Date: " + returnDate + "\n\n" +
                "If you have any questions, please contact the coordinator.\n\nThanks,\nEduKita College");
        mailSender.send(message);
    }
}
