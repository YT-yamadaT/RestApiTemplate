package com.api.domain.repository;

import java.util.List;

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
	 *ユーザ情報を一件取得する
	 * 
	 * @param userId
	 * @return userData
	 */
	public UserData findOne(String userId);
	
}
