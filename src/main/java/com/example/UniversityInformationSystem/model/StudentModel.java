package com.example.UniversityInformationSystem.model;


import com.example.UniversityInformationSystem.model.roles.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @NotEmpty
    @NotNull
    private String name;

    @NotNull
    @NotEmpty
    private String surname;

    @Email
    private String email;

    @NotEmpty
    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.Student;

    private Boolean locked = false;
    private Boolean enabled = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "majorId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MajorModel majorModel;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CourseModel> courseModelList;

    //constructor
    private StudentModel(Long studentId, String name, String surname, String email, String password, UserRole userRole) {
        this.studentId = studentId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    //Static method for construction
    public static StudentModel create(StudentModel studentModel){

        return new StudentModel(studentModel.getStudentId(), studentModel.getName(),
                studentModel.getSurname(),studentModel.getEmail(), studentModel.getPassword(), UserRole.Student);
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(this.getUserRole().name());
        return Collections.singletonList(authority);    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }





}










