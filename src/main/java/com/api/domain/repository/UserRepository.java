package com.api.domain.repository;

import java.util.List;

import com.api.domain.response.data.LoginUserData;
import com.api.domain.response.data.UserData;

/**
 * データベースにアクセスするリポジトリー
 * @author yamadaT
 *
 */
public interface UserRepository {

	/**
	 * 全てのユーザを取得する
	 * 
	 * @return users
	 */
	public List<UserData> findAll();
	
	/**
	 * ログイン成功時にユーザ情報を取得する
	 * 
	 * @param userId
	 * @return userData
	 */
	public LoginUserData findOne(String userId);
	
}
