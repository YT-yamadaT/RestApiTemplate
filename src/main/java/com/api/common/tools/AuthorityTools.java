package com.api.common.tools;

import java.security.Principal;

import org.springframework.security.core.Authentication;

/**
 * 権限に関することを実行するクラス
 * 
 * @author yamadaT
 *
 */
public class AuthorityTools {

	private static final String AUTHORITY_ADMIN = "ADMIN";
	
	private static final String AUTHORITY_GENERAL = "GENERAL";
	
	/**
	 * Principalを渡して現在ログインしているユーザの権限を取得します
	 * @param principal
	 * @return 権限
	 */
	public String convertAuthorityToString(Principal principal) {
		Authentication auth = (Authentication) principal;
		String authority = auth.getAuthorities().toString().replace("[", "").replace("]", "");
		return authority;
	}
	
	/**
	 * 権限が管理者かどうか判断する
	 * Principalを渡して、権限を加工してから判別します
	 * 
	 * @param principal
	 * @return 成功の可否
	 */
	public boolean isAdmin(Principal principal) {
		String authority = this.convertAuthorityToString(principal);
		return authority.equals(AUTHORITY_ADMIN);
	}
	
	/**
	 * 権限が管理者かどうか判断する
	 * 文字列にて一致するかで判別する
	 * 
	 * @param authority
	 * @return　成功の可否
	 */
	public boolean isAdmin(String authority) {
		return authority.equals(AUTHORITY_ADMIN);
	}
	
	/**
	 * 権限が一般かどうか判断する
	 * Principalを渡して、権限を加工してから判別します
	 * 
	 * @param principal
	 * @return　成功の可否
	 */
	public boolean isGeneral(Principal principal) {
		String authority = this.convertAuthorityToString(principal);
		return authority.equals(AUTHORITY_GENERAL);
	}
	
	/**
	 * 権限が一般かどうか確認する
	 * 文字列にて一致するかで判別する
	 * 
	 * @param authority
	 * @return　成功の可否
	 */
	public boolean isGeneral(String authority) {
		return authority.equals(AUTHORITY_GENERAL);
	}
	
}
