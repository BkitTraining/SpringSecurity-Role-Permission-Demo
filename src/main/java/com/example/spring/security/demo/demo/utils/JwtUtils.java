package com.example.spring.security.demo.demo.utils;

import com.example.spring.security.demo.demo.model.dto.AccountInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
@Log4j2
@RequiredArgsConstructor
public class JwtUtils {

  private static final String TOKEN_TYPE = "Bearer";
  private final ObjectMapper objectMapper;

  @Value("${application.jwtConfig.tokenSecret}")
  private String tokenSecret;

  @Value("${application.jwtConfig.tokenExpiredSeconds}")
  private Integer tokenExpiredSeconds;

  public AccountInfo getTokenInfo(String token) throws JsonProcessingException {
    Claims claims = Jwts.parser()
        .setSigningKey(tokenSecret)
        .parseClaimsJws(token)
        .getBody();
    final String accountInfoData = claims.getId();
    return objectMapper.readValue(accountInfoData, AccountInfo.class);
  }

  public String createAccountToken(String data) {
    Date current = new Date();
    Calendar expiredDate = Calendar.getInstance();
    expiredDate.setTime(current);
    expiredDate.add(Calendar.SECOND, tokenExpiredSeconds);
    return Jwts.builder()
        .setId(data)
        .setIssuedAt(current)
        .setExpiration(expiredDate.getTime())
        .signWith(SignatureAlgorithm.HS256, tokenSecret)
        .compact();
  }

  public boolean validateToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(authToken);
      return true;
    } catch (MalformedJwtException ex) {
      log.error("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      log.error("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      log.error("Unsupported JWT token");

    } catch (IllegalArgumentException ex) {
      log.error("JWT claims string is empty.");
    }
    return false;
  }
}
