//package com.ems.entity;
//
//import jakarta.persistence.*;
//import jakarta.xml.bind.annotation.XmlAccessType;
//import jakarta.xml.bind.annotation.XmlAccessorType;
//import jakarta.xml.bind.annotation.XmlRootElement;
//
//import java.time.LocalDate;
//@XmlRootElement(name = "Employee")
//@XmlAccessorType(XmlAccessType.FIELD)
//@Entity
//public class Employee {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String name;
//    private String email;
//    private String department;
//    private String dateOfJoining;
//
//    public Long getId()
//    {
//        return id;
//    }
//    public void setId(Long id)
//    {
//        this.id = id;
//    }
//
//    public String getName()
//    {
//        return name;
//    }
//    public void setName(String name)
//    {
//        this.name = name;
//    }
//
//    public String getEmail()
//    {
//        return email;
//    }
//    public void setEmail(String email)
//    {
//        this.email = email;
//    }
//
//    public String getDepartment() {
//        return department;
//    }
//    public void setDepartment(String department)
//    {
//        this.department = department;
//    }
//
//    public String getDateOfJoining()
//    {
//        return dateOfJoining;
//    }
//    public void setDateOfJoining(String dateOfJoining)
//    {
//        this.dateOfJoining = dateOfJoining;
//    }
//}

package com.ems.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.xml.bind.annotation.*;
import lombok.*;

@XmlRootElement(name = "Employee")
@XmlAccessorType(XmlAccessType.FIELD)

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "Department cannot be empty")
    private String department;

    @NotBlank(message = "Date of Joining cannot be empty")
    private String dateOfJoining;
}