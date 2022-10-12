package com.api.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.api.common.tools.AuthorityTools;
import com.api.common.tools.DatabaseAccessTools;

/**
 * 主にAutowiredを使用するツールなどをBeanを登録する
 * @author yamadaT
 *
 */
@Configuration
public class WebConfig {
	
	
	/**
	 * 使用するパスワードエンコーダーを指定
	 * @return {@link PasswordEncoder}
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	/**
	 * データベースにアクセスする際に使用するメソッドの追加
	 * @return {@link DatabaseAccessTools}
	 */
	@Bean
	DatabaseAccessTools databaseAccessTools() {
		return new DatabaseAccessTools();
	}
	
	/**
	 * 権限に関する処理をする際に使用するビーンの追加
	 * 
	 * @return {@Link AuthorityTools}
	 */
	@Bean
	AuthorityTools authorityTools() {
		return new AuthorityTools();
	}
	
}
