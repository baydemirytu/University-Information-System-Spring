package com.example.UniversityInformationSystem.model;

import com.example.UniversityInformationSystem.model.roles.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailModel {

    @Id
    private Long userId;

    @Email
    @NotNull
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToOne(fetch = FetchType.LAZY)
    private ConfirmationTokenModel confirmationTokenModel;

    public EmailModel(Long userId, String email, UserRole userRole) {
        this.userId = userId;
        this.email = email;
        this.userRole = userRole;
    }
}
