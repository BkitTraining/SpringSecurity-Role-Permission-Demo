package com.example.spring.security.demo.demo.security;

import com.example.spring.security.demo.demo.security.handler.CustomAccessDenied;
import com.example.spring.security.demo.demo.security.handler.RestAuthenticationEntryPoint;
import com.example.spring.security.demo.demo.security.service.CustomUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

  private final CustomUserDetailService customUserDetailService;
  private final JwtAuthenticationFilter jwtRequestFilter;
  private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
  private final CustomAccessDenied customAccessDenied;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf().disable()
        .exceptionHandling()
        .authenticationEntryPoint(restAuthenticationEntryPoint)
        .accessDeniedHandler(customAccessDenied)
        .and()
        .authorizeRequests()
        .anyRequest().permitAll()
        .and()
        .userDetailsService(customUserDetailService);
    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    return http.build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring()
        .antMatchers("/resources/**", "/resources/static/**", "/resources/templates/**");
  }

}
