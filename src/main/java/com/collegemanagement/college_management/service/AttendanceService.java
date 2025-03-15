package com.collegemanagement.college_management.service;

import com.collegemanagement.college_management.model.Attendance;
import com.collegemanagement.college_management.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    // Fetch attendance by roll number
    public Map<String, String> getAttendanceByRollNo(String rollNo) {
        Optional<Attendance> attendance = attendanceRepository.findById(rollNo);
        return attendance.map(Attendance::getSubjects).orElse(Collections.emptyMap());
    }

    // Save or update attendance
    public void saveOrUpdateAttendance(Attendance attendance) {
        attendanceRepository.save(attendance);
    }
}
