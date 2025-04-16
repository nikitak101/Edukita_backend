package com.collegemanagement.college_management.model;

import jakarta.persistence.*;  // Using Jakarta Persistence instead of javax.persistence
import lombok.*;

@Entity
@Data // Generates getters, setters, toString, equals, hashCode, etc.
@NoArgsConstructor // Generates no-argument constructor
@AllArgsConstructor // Generates all-argument constructor
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentName;
    private String email;
    private String reason;
    private String leaveDate;
    private String returnDate;
    private String status;

}
