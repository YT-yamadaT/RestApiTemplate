package com.api.domain.response.data;

import com.api.domain.response.Response;

import lombok.Data;

/**
 * 画面に表示するユーザデータ
 * @author yamadaT
 *
 */
@Data
public class UserData implements Response{

	/**ユーザID*/
	private String userId;
	
	/**メールアドレス*/
	private String email;
	
	/**ユーザ名*/
	private String userName;
	
	/**権限*/
	private String role;
	
	/**ユーザのステータス*/
	private String status;
	
}
