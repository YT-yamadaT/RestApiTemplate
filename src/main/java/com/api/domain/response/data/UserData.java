package com.api.domain.response.data;

import lombok.Data;

/**
 * 画面に表示するユーザデータ
 * @author yamadaT
 *
 */
@Data
public class UserData {

	/**ユーザID*/
	private String userId;
	
	/**メールアドレス*/
	private String email;
	
	/**ユーザ名*/
	private String userName;
	
	/**ユーザのステータス*/
	private String status;
	
}
