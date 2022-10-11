package com.api.domain.response.data;

import lombok.Data;

/**
 * ログイン時に使用するユーザのデータクラス
 * @author yamadaT
 *
 */
@Data
public class LoginUserData {

	/**
	 * ユーザのID
	 */
	private String userId;
	
	/**
	 * ユーザ名
	 */
	private String userName;
	
	/**
	 * ユーザのステータス
	 */
	private String status;
	
	/**
	 * 権限情報
	 */
	private String role;
	
}
