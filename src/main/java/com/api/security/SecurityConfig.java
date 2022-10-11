package com.api.security;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.GenericFilterBean;

import com.api.domain.repository.UserRepository;

/**
 * セキュリティーについて設定するクラス
 * 
 * @author yamadaT
 *
 */
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	UserRepository userRepository;
	
	//TODO DBの項目名が一致しない場合は適宜修正する
	
	/** ユーザ情報と有効かどうかを取得するSQL*/
	private static final String USER_SQL = "SELECT user_id, password , CASE status WHEN '1' THEN true ELSE false END AS enabled FROM users WHERE user_id = ?";
	
	/** 権限情報を取得するSQL */
	private static final String ROLE_SQL = "SELECT user_id, role FROM users WHERE user_id = ?";
	
	//シークレットキー
    @Value("${security.secret-key:secret}")
    private String secretKey = "secret";
    
    //JWTを格納するヘッダー
    static final String JWT_HEADER = "Authorization";

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.mvcMatchers("/home").permitAll()// TODO セキュリティ設定
			.antMatchers("/h2-console/**")
            .permitAll()
			.anyRequest()
            .authenticated() //上記のパス以外はログインが必要
			.and()
			//Exception
			.exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint())
				.accessDeniedHandler(accessDeniedHandler())
				.and()
			//ログイン系
			.formLogin()
			.loginProcessingUrl("/login").permitAll()
				//TODO ログインに必要なパラメータ名を設定する
				.usernameParameter("userId")
				.passwordParameter("password")
			.successHandler(this.authenticationSuccessHandler())
			.failureHandler(this.authenticationFailureHandler())
			.and()
			//logout
			.logout()
			.logoutUrl("/logout")
			//ログアウト時は200ステータスを返却するように設定
			.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
			.and()
			// JWTには必要ないためCSRFの無効化
			.csrf()
			.disable()
			//AUTHORIZE
			.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
			//SESSION
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			//CORS設定
			.and()
			.cors()
			.configurationSource(this.corsFilter());
		
		http.headers().frameOptions().disable();
		return http.build();
	}
	
	@Bean
	UserDetailsManager userDetailsManager(DataSource dataSource) {
		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
		users.setUsersByUsernameQuery(USER_SQL);
		users.setAuthoritiesByUsernameQuery(ROLE_SQL);
		return users;
	}
	
	/**
	 * ログイン成功時の処理
	 * @return
	 */
	private AuthenticationSuccessHandler authenticationSuccessHandler() {
	    return new JwtAuthenticationSuccessHandler(secretKey);
	}
	
	/**
	 * ログイン失敗した際のステータスを返却する
	 * @return status
	 */
	private AuthenticationFailureHandler authenticationFailureHandler() {
		return new AuthenticationFailureHandler() {
			@Override
			public void onAuthenticationFailure(HttpServletRequest request,
					HttpServletResponse response,
					AuthenticationException exception) throws IOException, ServletException {
				response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
			}
		};
	}
	
	/**
	 * 未認証のユーザが認証が必要なAPIにアクセスしたときの処理
	 * @return
	 */
	AuthenticationEntryPoint authenticationEntryPoint() {
	    return new UnAuthorizedPoint();
	}
	
	AccessDeniedHandler accessDeniedHandler() {
	    return new SimpleAccessDeniedHandler();
	}

	/**
	 * jwtトークンの認証を行うフィルタを返す
	 * @return フィルタ
	 */
	private GenericFilterBean jwtFilter() {
		return new JwtTokenFilter(secretKey, userRepository);
	}
	
	
	/**
	 * CORS設定をする
	 * @return source
	 */
	private CorsConfigurationSource corsFilter() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);        // CORSリクエストでcookie情報の取得を許可するか
		//TODO 許可するOriginの設定を行う
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));   // CORSリクエストを許可するドメイン
		configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:3000"));
		configuration.setAllowedHeaders(Arrays.asList(  // CORSリクエストで受信を許可するヘッダー情報
				"Access-Control-Allow-Headers",
				"Access-Control-Allow-Origin",
				"Authorization"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));  // CORSリクエストを許可するHTTPメソッド
		//許可するheaderの設定
		configuration.setExposedHeaders(Arrays.asList("Authorization"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration); // CORSリクエストを許可するURL

		return source;
	}
}
