package com.example.spring.security.demo.demo.security;

import com.example.spring.security.demo.demo.model.dto.AccountInfo;
import com.example.spring.security.demo.demo.model.dto.PermissionInfo;
import com.example.spring.security.demo.demo.model.dto.RoleInfo;
import com.example.spring.security.demo.demo.utils.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Component
@AllArgsConstructor
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtils jwtUtils;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String jwt = getJwtFromRequest(request);
    logger.info("run jwt filter");
    if (StringUtils.hasText(jwt) && jwtUtils.validateToken(jwt)) {
      UserDetails userDetails = getUserByToken(jwt);
      logger.info("create userDetails at filter with jwt " + jwt);
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          userDetails, null, userDetails.getAuthorities());
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } else {
      logger.error("cannot get user from token");
    }

    filterChain.doFilter(request, response);
  }

  private String getJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  private UserDetails getUserByToken(String jwt) throws JsonProcessingException {
    AccountInfo accountInfo = jwtUtils.getTokenInfo(jwt);
    List<String> privileges = new ArrayList<>();
    List<RoleInfo> roles = accountInfo.getRoles();
    List<PermissionInfo> permissions = new ArrayList<>();
    roles.forEach(i -> {
      privileges.add(i.getName());
      permissions.addAll(i.getPermissions());
    });
    permissions.forEach(i -> privileges.add(i.getName()));
    List<GrantedAuthority> springAuthorities = new ArrayList<>();
    for (String privilege : privileges) {
      springAuthorities.add(new SimpleGrantedAuthority(privilege));
    }
    return new User(accountInfo.getEmail(), null, springAuthorities);
  }

}
