package com.api.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

/**
 * 認証が成功した際のハンドラ
 * 
 * @author yamadaT
 *
 */
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	//有効期限(1h)
	private static final long EXPIRATION_TIME = 1000L * 60L * 60L;
	
	// JWT Algorithm
	private Algorithm algorithm;

	public JwtAuthenticationSuccessHandler(String secretKey) {
		Objects.requireNonNull(secretKey, "secret key must be not null");
		try {
			this.algorithm = Algorithm.HMAC256(secretKey);
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {

			e.printStackTrace();
		}
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		if (response.isCommitted()) {
			// TODO LOG
			return;
		}
		setToken(response, generateToken(authentication));
		// 200
		response.setStatus(HttpStatus.OK.value());
		// ログイン時のセッションの削除をする
		clearAuthenticationAttributes(request);

	}

	private String generateToken(Authentication auth) {
		//roleを取得する
		String[] authPrincipals = auth.getPrincipal().toString().split(",");
		String role = authPrincipals[authPrincipals.length - 1].replace(" Granted Authorities=", "").replaceAll("\\[", "").replaceAll("\\]", "");
		System.out.println("Role is : " + role);
		Date issuedAt = new Date();
		Date notBefore = new Date(issuedAt.getTime());
		Date expiresAt = new Date(issuedAt.getTime() + EXPIRATION_TIME);
		String token = JWT.create()
				.withClaim("authority", role)
				// 発行時間
				.withIssuedAt(issuedAt)
				// 有効期限の開始時間
				.withNotBefore(notBefore)
				// 有効期限の終了時間
				.withExpiresAt(expiresAt)
				// JWTの主体,ユーザIDがユニークな値のため使用
				.withSubject(auth.getName()).sign(this.algorithm);
		System.out.println(token);
		return token;
	}
	
	/**
	 * トークンをセットする
	 * @param response
	 * @param token
	 */
	private void setToken(HttpServletResponse response, String token) {
		response.setHeader("Authorization", String.format("Bearer %s", token));
	}

	/**
	 * Removes temporary authentication-related data which may have been stored in
	 * the session during the authentication process.
	 */
	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
}
