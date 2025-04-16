package com.collegemanagement.college_management.controller;

import com.collegemanagement.college_management.model.LeaveRequest;
import com.collegemanagement.college_management.repository.LeaveRequestRepository;
import com.collegemanagement.college_management.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-requests")
public class LeaveController {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private EmailService emailService; // Injecting the EmailService

    // Endpoint to create a new leave request
    @PostMapping
    public ResponseEntity<LeaveRequest> createLeaveRequest(@RequestBody LeaveRequest leaveRequest) {
        leaveRequest.setStatus("Pending"); // Default status when the leave request is created
        LeaveRequest savedLeaveRequest = leaveRequestRepository.save(leaveRequest);

        // Send email notification to the student after the leave request is created
        emailService.sendLeaveApplicationEmail(savedLeaveRequest.getEmail(), savedLeaveRequest.getStudentName(),
                savedLeaveRequest.getReason(), savedLeaveRequest.getLeaveDate(), savedLeaveRequest.getReturnDate());

        // Send notification to the coordinator
        emailService.sendLeaveRequestToCoordinator("coordinator@example.com", savedLeaveRequest.getStudentName(),
                savedLeaveRequest.getReason(), savedLeaveRequest.getLeaveDate(), savedLeaveRequest.getReturnDate());

        return new ResponseEntity<>(savedLeaveRequest, HttpStatus.CREATED);
    }

    // Endpoint to get a specific leave request by ID
    @GetMapping("/{id}")
    public ResponseEntity<LeaveRequest> getLeaveRequestById(@PathVariable Long id) {
        return leaveRequestRepository.findById(id)
                .map(leaveRequest -> new ResponseEntity<>(leaveRequest, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint to get all leave requests
    @GetMapping
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    // Endpoint to delete a leave request by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeaveRequest(@PathVariable Long id) {
        if (leaveRequestRepository.existsById(id)) {
            leaveRequestRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to update a leave request by ID
    @PutMapping("/{id}")
    public ResponseEntity<LeaveRequest> updateLeaveRequest(@PathVariable Long id, @RequestBody LeaveRequest leaveRequestDetails) {
        return leaveRequestRepository.findById(id)
                .map(existingLeaveRequest -> {
                    existingLeaveRequest.setStudentName(leaveRequestDetails.getStudentName());
                    existingLeaveRequest.setEmail(leaveRequestDetails.getEmail());
                    existingLeaveRequest.setReason(leaveRequestDetails.getReason());
                    existingLeaveRequest.setLeaveDate(leaveRequestDetails.getLeaveDate());
                    existingLeaveRequest.setReturnDate(leaveRequestDetails.getReturnDate());
                    existingLeaveRequest.setStatus(leaveRequestDetails.getStatus()); // Update status field as well
                    LeaveRequest updatedLeaveRequest = leaveRequestRepository.save(existingLeaveRequest);
                    return new ResponseEntity<>(updatedLeaveRequest, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint to approve a leave request by ID
    @PutMapping("/{id}/approve")
    public ResponseEntity<String> approveLeaveRequest(@PathVariable Long id) {
        return leaveRequestRepository.findById(id)
                .map(leaveRequest -> {
                    // Send email to the student informing them of the approval
                    emailService.sendLeaveApprovalNotification(leaveRequest.getEmail(), leaveRequest.getStudentName(),
                            leaveRequest.getLeaveDate(), leaveRequest.getReturnDate());

                    // Send notification to coordinator about approval
                    emailService.sendLeaveRequestToCoordinator("coordinator@example.com", leaveRequest.getStudentName(),
                            "Leave Approved", leaveRequest.getLeaveDate(), leaveRequest.getReturnDate());

                    // Update leave request status to "Approved"
                    leaveRequest.setStatus("Approved");
                    leaveRequestRepository.save(leaveRequest);

                    return new ResponseEntity<>("Leave request approved.", HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>("Leave request not found.", HttpStatus.NOT_FOUND));
    }

    // Endpoint to reject a leave request by ID
    @PutMapping("/{id}/reject")
    public ResponseEntity<String> rejectLeaveRequest(@PathVariable Long id, @RequestParam String reason) {
        return leaveRequestRepository.findById(id)
                .map(leaveRequest -> {
                    // Send email to the student informing them of the rejection
                    emailService.sendLeaveRejectionNotification(leaveRequest.getEmail(), leaveRequest.getStudentName(),
                            reason, leaveRequest.getLeaveDate(), leaveRequest.getReturnDate());

                    // Send notification to coordinator about rejection
                    emailService.sendLeaveRequestToCoordinator("coordinator@example.com", leaveRequest.getStudentName(),
                            "Leave Rejected", leaveRequest.getLeaveDate(), leaveRequest.getReturnDate());

                    // Update leave request status to "Rejected"
                    leaveRequest.setStatus("Rejected");
                    leaveRequestRepository.save(leaveRequest);

                    return new ResponseEntity<>("Leave request rejected.", HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>("Leave request not found.", HttpStatus.NOT_FOUND));
    }
}
