package com.collegemanagement.college_management.repository;

import com.collegemanagement.college_management.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    // You can add custom queries here if needed
}
