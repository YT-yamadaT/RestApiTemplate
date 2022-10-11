package com.api.domain.service.userServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.common.tools.DatabaseAccessTools;
import com.api.domain.repository.UserRepositoryImpl;
import com.api.domain.request.Request;
import com.api.domain.response.Response;
import com.api.domain.response.entity.UserEntity;
import com.api.domain.service.UserService;

/**
 * ユーザを全件取得するサービス
 * @author yamadaT
 */
@Service
public class GetUsersService implements UserService{

	@Autowired
	private DatabaseAccessTools databaseAccessTools;
	
	@Autowired
	private UserRepositoryImpl userRepositoryImpl;
	
	@Override
	public Response execute(Request request) {
		UserEntity entity = new UserEntity();
		
		databaseAccessTools.selectAccess(
				() -> userRepositoryImpl.findAll(),
				(res) -> entity.setUsers(res),
				() -> entity.setUsers(null)
		);
		
		return entity;
	}

}
