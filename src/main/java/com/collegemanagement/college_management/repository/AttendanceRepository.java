package com.collegemanagement.college_management.repository;

import com.collegemanagement.college_management.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, String> {
}
