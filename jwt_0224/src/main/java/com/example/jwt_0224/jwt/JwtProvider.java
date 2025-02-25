package com.example.jwt_0224.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

//bean으로 등록하기 위해 Component사용.
@Component
public class JwtProvider {
  @Value("${custom.jwt.secretKey}")
  private String secretKeyCode;

  private SecretKey secretKey;

  public SecretKey getSecretKey() {
    if (secretKey == null) {
      String encoding = Base64.getEncoder().encodeToString(secretKeyCode.getBytes());
      secretKey = Keys.hmacShaKeyFor(encoding.getBytes());
    }
    return secretKey;
  }

  public String genToken(Map<String, Object> map, int seconds) {
    long now = new Date().getTime();
    Date accessTokenExpiresIn = new Date(now + 1000L * seconds);

    // JwtBuilder 생성.
    JwtBuilder jwtBuilder = Jwts.builder().subject("SIST").expiration(accessTokenExpiresIn);

    // Set방식은 중복된 값이 저장되지 않는다. (제거됨.),
    Set<String> keys = map.keySet(); // 반복자를 처리하기 위해 Set구조화.
    Iterator<String> it = keys.iterator(); // 반복자를 얻어냄. (while문 사용을 위함.)
    while (it.hasNext()) {
      // 키값을 얻어냄.
      String key = it.next();
      Object value = map.get(key);

      // MAP에다가 담아서 m_id:test123, m_pw:1234.. 이런식으로 담아서 저장해둠.
      jwtBuilder.claim(key, value);
    }
    // 만들어놓은 getSecretKey함수를 던져줌, compact라는 함수가 String으로 반환해줌.
    String jwt = jwtBuilder.signWith(getSecretKey()).compact();
    return jwt;
  }

  // 토큰이 만료되었는지 확인하는 함수
  public boolean verify(String token) {
    // value가 false가 되면 사용할수 없는 토큰이된다.
    boolean value = true;
    try {
      // verifyWith에는 KEY가 들어가야한다. parseSignedClaims는 token이 들어와야한다.
      Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token);
    } catch (Exception e) {
      // 유효기간이 만료되면, 예외 발생. (Value 값이 false인 경우)
      value = false;
    }
    return value;
  }

  // 토큰에 담긴 사용자 정보(claims)를 반환한다.
  public Map<String, Object> getClaims(String token) {
    // jwt.io에서 보이던 payload 정보가 반환이 됨.
    return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
  }
}
