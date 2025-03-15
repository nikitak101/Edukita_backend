package com.collegemanagement.college_management.controller;

import com.collegemanagement.college_management.model.Attendance;
import com.collegemanagement.college_management.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "http://localhost:3000") // Allow frontend access
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // GET Attendance for a specific student
    @GetMapping("/{rollNo}")
    public ResponseEntity<Map<String, String>> getAttendance(@PathVariable String rollNo) {
        Map<String, String> attendance = attendanceService.getAttendanceByRollNo(rollNo);
        if (attendance.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(attendance);
    }

    // POST Attendance for a student (Admin or Faculty)
    @PostMapping("/save")
    public ResponseEntity<String> saveAttendance(@RequestBody Attendance attendance) {
        attendanceService.saveOrUpdateAttendance(attendance);
        return ResponseEntity.ok("Attendance record saved successfully.");
    }
}
