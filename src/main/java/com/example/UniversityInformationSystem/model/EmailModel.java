package com.example.UniversityInformationSystem.model;

import com.example.UniversityInformationSystem.model.roles.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.Email;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailModel {

    @Id
    private Long userId;

    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

}
