package com.example.UniversityInformationSystem.security;

import com.example.UniversityInformationSystem.model.AcademicianModel;
import com.example.UniversityInformationSystem.model.AdminModel;
import com.example.UniversityInformationSystem.model.StudentModel;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${uis-app-secret}")
    private String APP_SECRET;

    @Value("${uis-expires-in}")
    private Long EXPIRES_IN;

    public String adminJwtToken(Authentication authentication){

        AdminModel adminModel = (AdminModel) authentication.getPrincipal();
        Date expireDate = new Date(new Date().getTime()+EXPIRES_IN);

        return Jwts.builder().setSubject(adminModel.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,APP_SECRET).compact();

    }
    public String academicianJwtToken(Authentication authentication){

        AcademicianModel academicianModel = (AcademicianModel) authentication.getPrincipal();
        Date expireDate = new Date(new Date().getTime()+EXPIRES_IN);

        return Jwts.builder().setSubject(academicianModel.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,APP_SECRET).compact();

    }

    public String studentJwtToken(Authentication authentication){

        StudentModel studentModel = (StudentModel) authentication.getPrincipal();
        Date expireDate = new Date(new Date().getTime()+EXPIRES_IN);

        return Jwts.builder().setSubject(studentModel.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,APP_SECRET).compact();

    }

    public String getUserEmailFromJwt(String token){

        Claims claims = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody();
        return claims.getSubject();

    }


    public boolean validateToken(String token){

        try{
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
            return !isTokenExpired(token);
        } catch (ExpiredJwtException e) {
            return false;
        } catch (UnsupportedJwtException e) {
            return false;
        } catch (MalformedJwtException e) {
            return false;
        } catch (SignatureException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }

    }

    private boolean isTokenExpired(String token) {

        Date expiration = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }

}
