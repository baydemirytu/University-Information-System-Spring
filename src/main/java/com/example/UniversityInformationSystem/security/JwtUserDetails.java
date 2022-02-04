package com.example.UniversityInformationSystem.security;

import com.example.UniversityInformationSystem.model.AcademicianModel;
import com.example.UniversityInformationSystem.model.AdminModel;
import com.example.UniversityInformationSystem.model.StudentModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class JwtUserDetails implements UserDetails {

    private Long id;
    private String username;
    private String surname;
    private String password;
    private String title;
    private Collection<? extends GrantedAuthority> authorities;

    //Constructor for student and admins
    private JwtUserDetails(Long id, String username, String surname, String password,
                          Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.surname=surname;
        this.password = password;
        this.authorities = authorities;
    }

    //Constructor for academician
    private JwtUserDetails(Long id, String username, String surname, String password, String title,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.surname=surname;
        this.password = password;
        this.title=title;
        this.authorities = authorities;
    }

    //for students
    public static JwtUserDetails create(StudentModel studentModel){

        List<GrantedAuthority> authoritiesList = new ArrayList<>();
        authoritiesList.add(new SimpleGrantedAuthority("Student"));
        return new JwtUserDetails(studentModel.getStudentId(), studentModel.getName(),
                studentModel.getSurname(), studentModel.getPassword(), authoritiesList);

    }
    //for academicians
    public static JwtUserDetails create(AcademicianModel academicianModel){

        List<GrantedAuthority> authoritiesList = new ArrayList<>();
        authoritiesList.add(new SimpleGrantedAuthority("Academician"));
        return new JwtUserDetails(academicianModel.getAcademicianId(), academicianModel.getName(),
                academicianModel.getSurname(), academicianModel.getPassword(),academicianModel.getTitle(), authoritiesList);

    }
    //for admins
    public static JwtUserDetails create(AdminModel adminModel){

        List<GrantedAuthority> authoritiesList = new ArrayList<>();
        authoritiesList.add(new SimpleGrantedAuthority("Admin"));
        return new JwtUserDetails(adminModel.getAdminId(), adminModel.getName(),
                adminModel.getSurname(), adminModel.getPassword(), authoritiesList);

    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
