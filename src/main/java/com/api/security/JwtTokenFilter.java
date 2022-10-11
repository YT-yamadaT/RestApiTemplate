package com.api.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.GenericFilterBean;

import com.api.domain.repository.UserRepository;
import com.api.domain.response.data.LoginUserData;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtTokenFilter extends GenericFilterBean {

	final private UserRepository userRepository;
	final private Algorithm algorithm;
	
	public JwtTokenFilter(String secretKey, UserRepository userRepository) {
		Objects.requireNonNull(secretKey, "secret key must be not null");
		this.userRepository = userRepository;
		try {
			this.algorithm = Algorithm.HMAC256(secretKey);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		//トークンの取り出し
		String token = resolveToken(request);
		if(token == null) {
			//取得できなかった
			chain.doFilter(request, response);
			return;
		}
		
		try {
			authentication(verifyToken(token));
			

		} catch (JWTVerificationException | DataAccessException e) {
			//認証失敗
			//TODO 
			System.out.println("認証失敗");
			System.out.println("IPアドレス: " + request.getLocalAddr());

			//TODO LOG
			SecurityContextHolder.clearContext();
			((HttpServletResponse) response).sendError(HttpStatus.UNAUTHORIZED.value(),
					HttpStatus.UNAUTHORIZED.getReasonPhrase());
		}
		chain.doFilter(request, response);
		
	}
	
	/**
	 * トークンを取得する
	 * 取得できなかった場合はNullを返却する
	 * @param request
	 * @return トークン
	 */
	private String resolveToken(ServletRequest request) {
		String token = ((HttpServletRequest) request).getHeader(SecurityConfig.JWT_HEADER);
		if(token == null || !token.startsWith("Bearer ")) {
			//トークンがあるか、改ざんされていないか
			return null;
		}
		
		//Bearer を取り除く
		return token.substring(7);
	}
	
	/**
	 * トークンの検証し、Principalに設定する
	 * @param token
	 * @return
	 */
	private DecodedJWT verifyToken(String token) {
		JWTVerifier verifier = JWT.require(algorithm).build();
		return verifier.verify(token);
	}
	
	private String authentication(DecodedJWT jwt) {
		// ユーザID取り出し
		String userId = String.valueOf(jwt.getSubject());		
		// ユーザ情報取得
		LoginUserData userData = userRepository.findOne(userId);
		// 権限の整形
		Collection<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(String.valueOf(userData.getRole())));
		// ユーザ情報の整形
		User user = new User(userData.getUserId(), "", authorities);
		// Principalの準備
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
		
		return userId;
	}
	
	
}
