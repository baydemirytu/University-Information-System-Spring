package com.example.UniversityInformationSystem.security;

import com.example.UniversityInformationSystem.model.AcademicianModel;
import com.example.UniversityInformationSystem.model.AdminModel;
import com.example.UniversityInformationSystem.model.StudentModel;
import com.example.UniversityInformationSystem.service.AcademicianService;
import com.example.UniversityInformationSystem.service.AdminService;
import com.example.UniversityInformationSystem.service.StudentService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@Getter
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private StudentService studentService;
    @Autowired
    private AcademicianService academicianService;
    @Autowired
    private AdminService adminService;

    private UserDetails user = null;;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            String jwtToken = extractJwtFromRequest(request);
            if(StringUtils.hasText(jwtToken) && jwtTokenProvider.validateToken(jwtToken)){
                String email = jwtTokenProvider.getUserEmailFromJwt(jwtToken);


                if(academicianService.getAcademicianByEmail(email)!=null){
                    user = academicianService.getAcademicianByEmail(email);
                }
                else if(studentService.getStudentByEmail(email)!=null){
                    user = studentService.getStudentByEmail(email);
                }
                else if(adminService.getAdminByEmail(email)!=null){

                    user = adminService.getAdminByEmail(email);
                }

                if(user != null){

                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);

                }
                else{
                    System.out.println("filter sıkıntı");
                }
            }
        }catch (Exception e){
            System.out.println(this.getClass().toString() + e.getMessage());
            return;
        }
        filterChain.doFilter(request,response);
    }


    private String extractJwtFromRequest(HttpServletRequest request) {

        String bearer = request.getHeader("Authorization");
        if(StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")){
            return bearer.substring("Bearer".length()+1);
        }
        return null;
    }
}
