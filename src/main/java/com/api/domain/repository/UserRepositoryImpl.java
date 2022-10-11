package com.api.domain.repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.api.domain.response.data.LoginUserData;

@Repository
public class UserRepositoryImpl implements UserRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	
	/** 
	 * {@link LoginUserData}のマッパー
	 * DBからアクセスしてきた値をDiaryDataに加工する
	 */
	private static RowMapper<LoginUserData> LoginUserMapper = BeanPropertyRowMapper.newInstance(LoginUserData.class);
	
	private static final String SQL_SELECT_ONE = "SELECT user_id, user_name, status, role FROM users WHERE user_id = :userId";
	
	@Override
	public LoginUserData findOne(String userId) {
		Map<String, Object> params = Map.of(
				"userId", userId);
		return jdbc.queryForObject(SQL_SELECT_ONE, params, LoginUserMapper);
	}

}
