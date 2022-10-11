package com.api.domain.repository;

import com.api.domain.response.data.LoginUserData;

/**
 * データベースにアクセスするリポジトリー
 * @author yamadaT
 *
 */
public interface UserRepository {

	/**
	 * ログイン成功時にユーザ情報を取得する
	 * 
	 * @param userId
	 * @return データ
	 */
	public LoginUserData findOne(String userId);
	
}
