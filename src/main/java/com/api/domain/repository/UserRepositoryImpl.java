package com.api.domain.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.api.domain.response.data.LoginUserData;
import com.api.domain.response.data.UserData;

@Repository
public class UserRepositoryImpl implements UserRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	
	/** 
	 * {@link LoginUserData}のマッパー
	 * DBからアクセスしてきた値をLoginUserDataに加工する
	 */
	private static RowMapper<LoginUserData> LoginUserMapper = BeanPropertyRowMapper.newInstance(LoginUserData.class);
	
	/** 
	 * {@link LoginUserData}のマッパー
	 * DBからアクセスしてきた値をLoginUserDataに加工する
	 */
	private static RowMapper<UserData> UserMapper = BeanPropertyRowMapper.newInstance(UserData.class);
	
	/**SQL ユーザ全件取得*/
	private static final String SQL_SELECT_ALL = "SELECT * FROM users_view";
	
	/**SQL ユーザ一件取得(ログイン時に使用するデータのみ)*/
	private static final String SQL_SELECT_ONE_LOGIN = "SELECT user_id, user_name, status, role FROM users WHERE user_id = :userId";
	
	@Override
	public List<UserData> findAll() {
		return jdbc.query(SQL_SELECT_ALL, Map.of(), UserMapper);
	}
	
	@Override
	public LoginUserData findOne(String userId) {
		Map<String, Object> params = Map.of(
				"userId", userId);
		return jdbc.queryForObject(SQL_SELECT_ONE_LOGIN, params, LoginUserMapper);
	}



}
