package com.schemeai;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.*;
import java.util.List;
@Configuration
public class SecurityConfig {
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwt) throws Exception {
  return http.csrf(c->c.disable()).cors(c->c.configurationSource(corsConfigurationSource()))
   .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
   .authorizeHttpRequests(a->a.requestMatchers("/api/auth/**","/api/schemes","/api/schemes/**","/api/recommendations","/error").permitAll()
    .requestMatchers("/api/admin/**").hasRole("ADMIN").anyRequest().authenticated())
   .addFilterBefore(jwt,org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class).build();
 }
 @Bean CorsConfigurationSource corsConfigurationSource(){CorsConfiguration c=new CorsConfiguration();c.setAllowedOrigins(List.of("http://localhost:5500","http://127.0.0.1:5500","http://localhost:3000","http://127.0.0.1:5500"));c.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));c.setAllowedHeaders(List.of("*"));c.setExposedHeaders(List.of("Authorization"));UrlBasedCorsConfigurationSource s=new UrlBasedCorsConfigurationSource();s.registerCorsConfiguration("/**",c);return s;}
}