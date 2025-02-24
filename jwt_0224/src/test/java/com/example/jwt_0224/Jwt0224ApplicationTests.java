package com.example.jwt_0224;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jwt_0224.jwt.JwtProvider;

import io.jsonwebtoken.security.Keys;

@SpringBootTest
class Jwt0224ApplicationTests {

	@Value("${custom.jwt.secretKey}")
	private String secretKey;

	@Test
	@DisplayName("시크릿키 확인용도.")
	void loadSecretKey() {
		assertThat(secretKey).isNotNull();
	}

	@Test
	@DisplayName("암호화알고리즘으로 시크릿KEY 암호화")
	void genBase64() {
		String encoding = Base64.getEncoder().encodeToString(secretKey.getBytes());
		SecretKey sKey = Keys.hmacShaKeyFor(encoding.getBytes());
		assertThat(sKey).isNotNull();
	}

	@Autowired
	private JwtProvider jwtProvider;

	@Test
	@DisplayName("JwtProvider를 통해 SecretKey객체를 얻기.")
	void testJwtProvider() {
		// sKey에 SecretKey를 준다.
		// 비어있으면 만들어서 준다.
		SecretKey sKey = jwtProvider.getSecretKey();
		assertThat(sKey).isNotNull();
	}

	@Test
	@DisplayName("동일한 SecretKey인지 확인.")
	void sameSecretKey() {
		SecretKey sKey1 = jwtProvider.getSecretKey();
		SecretKey sKey2 = jwtProvider.getSecretKey();

		// 객체는 주소값으로 비교하는게 가장 정확하다.
		assertThat(sKey1 == sKey2).isTrue();
	}

	@Test
	@DisplayName("엑세스 토큰 발급")
	void TokenTest() {
		Map<String, Object> claims = new HashMap<>();
		// value가 Object형이라 문자형 숫자형 다 저장이 가능함.
		claims.put("id", 1L);
		claims.put("m_name", "마루치");
		claims.put("m_email", "maru@korea.com");
		// 이렇게 하면 3시간 짜리 토큰이 만들어지는 결과가 나옴.
		String accessToken = jwtProvider.genToken(claims, 60 * 60 * 3);
		System.out.println("ACCESS-TOKEN:" + accessToken);

		assertThat(accessToken).isNotNull();

	}

	@Test
	@DisplayName("유효한 Token인지?(만료여부 확인)")
	void TokenValidTest() {
		Map<String, Object> claims = new HashMap<>();
		// value가 Object형이라 문자형 숫자형 다 저장이 가능함.
		claims.put("id", 1L);
		claims.put("m_name", "마루치");
		claims.put("m_email", "maru@korea.com");
		// seconds에 -1을 넣으면 만료를 뜻한다.
		String accessToken = jwtProvider.genToken(claims, -1);
		// String accessToken = jwtProvider.genToken(claims, 60 * 60);
		System.out.println("ACCESS-TOKEN:" + accessToken);

		// 거짓이 떨어지는지 아닌지 판별하기 위해 isFalse 함수를 사용.
		assertThat(jwtProvider.verify(accessToken)).isFalse();
	}

	@Test
	@DisplayName("Token에서 사용자정보(claims)를 확인")
	void TokenClaimsTest() {
		Map<String, Object> claims = new HashMap<>();
		// value가 Object형이라 문자형 숫자형 다 저장이 가능함.
		claims.put("id", 201L);
		claims.put("m_name", "을불");
		claims.put("m_email", "ub@korea.com");
		// 이렇게 하면 3시간 짜리 토큰이 만들어지는 결과가 나옴.
		String accessToken = jwtProvider.genToken(claims, 60 * 60);
		System.out.println("ACCESS-TOKEN:" + accessToken);

		assertThat(jwtProvider.verify(accessToken)).isTrue();

		// 토큰에 저장되어 있는 정보를 받는다.
		Map<String, Object> map = jwtProvider.getClaims(accessToken);
		System.out.println("Claims : " + map);
	}
}
