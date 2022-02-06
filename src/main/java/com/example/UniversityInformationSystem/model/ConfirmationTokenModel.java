package com.example.UniversityInformationSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConfirmationTokenModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    private EmailModel emailModel;

    public ConfirmationTokenModel(String token, EmailModel emailModel) {
        this.token = token;
        this.emailModel = emailModel;
    }
}
