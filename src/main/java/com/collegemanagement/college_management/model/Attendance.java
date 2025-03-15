package com.collegemanagement.college_management.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
@Table(name = "attendance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    @Id
    @Column(name = "roll_no", unique = true, nullable = false)
    private String rollNo;

    @ElementCollection
    @CollectionTable(name = "student_attendance", joinColumns = @JoinColumn(name = "roll_no"))
    @MapKeyColumn(name = "subject")
    @Column(name = "status")
    private Map<String, String> subjects; // Example: {"COA": "PRESENT", "DS": "ABSENT"}
}
