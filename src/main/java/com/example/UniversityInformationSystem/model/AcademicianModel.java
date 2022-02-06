package com.example.UniversityInformationSystem.model;

import com.example.UniversityInformationSystem.model.roles.AcademicianRole;
import com.example.UniversityInformationSystem.model.roles.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AcademicianModel implements UserDetails {

    @Id
    @SequenceGenerator(name = "academician_id_seq", initialValue = 100000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "academician_id_seq")
    private Long academicianId;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String surname;

    @Email
    private String email;


    @NotNull
    @NotEmpty
    private String password;

    @Enumerated(EnumType.STRING)
    private AcademicianRole academicianRole = AcademicianRole.Assistant;

    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.Academician;

    private Boolean locked = false;
    private Boolean enabled = true;


    @OneToMany(fetch = FetchType.LAZY)
    private List<CourseModel> courseModelList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "majorId")
    private MajorModel majorModel;





    public AcademicianModel(Long academicianId, String name, String surname, String email,
                            String password, AcademicianRole academicianRole,
                            UserRole userRole) {

        this.academicianId = academicianId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.academicianRole = academicianRole;
        this.userRole = userRole;
    }

    public static AcademicianModel create(AcademicianModel academicianModel) {

        return new AcademicianModel(academicianModel.getAcademicianId(), academicianModel.getName(),
                academicianModel.getSurname(), academicianModel.getEmail(),academicianModel.getPassword(),
                AcademicianRole.Assistant, UserRole.Academician);

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(this.getUserRole().name());
        return Collections.singletonList(authority);
    }

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
