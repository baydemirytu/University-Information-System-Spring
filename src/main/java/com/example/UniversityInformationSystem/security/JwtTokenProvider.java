package com.example.UniversityInformationSystem.security;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {


    private final String APP_SECRET = "UniversityInfSystem";

    private final Long EXPIRES_IN = Integer.toUnsignedLong(604800);

    public String generateJwtToken(Authentication authentication){

        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
        Date expireDate = new Date(new Date().getTime()+EXPIRES_IN);

        return Jwts.builder().setSubject(Long.toString(userDetails.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,APP_SECRET).compact();

    }

    public Long getUserIdFromJwt(String token){

        Claims claims = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());

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
