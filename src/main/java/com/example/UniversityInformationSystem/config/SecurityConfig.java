package com.example.UniversityInformationSystem.config;

import com.example.UniversityInformationSystem.security.JwtAuthenticationEntryPoint;
import com.example.UniversityInformationSystem.security.JwtAuthenticationFilter;
import com.example.UniversityInformationSystem.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;

    private JwtAuthenticationEntryPoint handler;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(handler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/swagger-ui/**","/v3/api-docs/**")
                .permitAll()
                .antMatchers("/auth/**")
                .permitAll()
                .antMatchers("/auth/secret/register/admin")
                .denyAll()
                .antMatchers(HttpMethod.GET,"/academician/**")
                .authenticated()
                .antMatchers(HttpMethod.POST,"/academician/**")
                .hasAuthority("Admin")
                .antMatchers(HttpMethod.DELETE,"/academician/**")
                .hasAuthority("Admin")
                .antMatchers(HttpMethod.PUT,"/course/**")
                .hasAuthority("Admin")
                .antMatchers(HttpMethod.DELETE,"/course/**")
                .hasAuthority("Admin")
                .antMatchers(HttpMethod.POST,"/course/add")
                .hasAuthority("Admin")
                .antMatchers(HttpMethod.POST,"/course/add/student/**")
                .hasAnyAuthority("Admin","Student")
                .antMatchers(HttpMethod.POST, "/course/add/academician/**")
                .hasAuthority("Admin")
                .antMatchers(HttpMethod.GET,"/course/all/students/**")
                .hasAnyAuthority("Admin","Academician")
                .antMatchers(HttpMethod.GET,"/course/**")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/faculty/**")
                .hasAuthority("Admin")
                .antMatchers(HttpMethod.GET,"/faculty/all/students/**")
                .hasAnyAuthority("Admin","Academician")
                .antMatchers(HttpMethod.GET,"/faculty/**")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/major/**")
                .hasAuthority("Admin")
                .antMatchers(HttpMethod.GET,"/major/all/students/**")
                .hasAnyAuthority("Admin","Academician")
                .antMatchers(HttpMethod.GET,"/major/**")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/student/**")
                .hasAuthority("Admin")
                .antMatchers(HttpMethod.DELETE,"/student/delete/**")
                .hasAuthority("Admin")
                .antMatchers(HttpMethod.DELETE,"/student/delete/course/**")
                .hasAnyAuthority("Admin","Student")
                .antMatchers(HttpMethod.GET,"/student/all")
                .hasAuthority("Admin")
                .antMatchers(HttpMethod.GET,"/student/**")
                .authenticated()
                .antMatchers(HttpMethod.POST,"/university/**")
                .hasAuthority("Admin")
                .antMatchers(HttpMethod.GET,"/university/all/students/**")
                .hasAnyAuthority("Admin","Academician")
                .antMatchers(HttpMethod.GET,"/university/**")
                .permitAll()
                .anyRequest()
                .denyAll();

        httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }



}
