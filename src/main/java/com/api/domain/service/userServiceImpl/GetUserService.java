package com.api.domain.service.userServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.common.tools.DatabaseAccessTools;
import com.api.controller.form.Request;
import com.api.controller.form.UserIdForm;
import com.api.domain.repository.UserRepositoryImpl;
import com.api.domain.response.MessageResponse;
import com.api.domain.response.Messages;
import com.api.domain.response.Response;
import com.api.domain.response.data.UserData;
import com.api.domain.response.entity.UserEntity;
import com.api.domain.service.UserService;

/**
 * ユーザを全件取得するサービス
 * @author yamadaT
 */
@Service
public class GetUserService implements UserService{

	@Autowired
	private DatabaseAccessTools databaseAccessTools;
	
	@Autowired
	private UserRepositoryImpl userRepositoryImpl;
	
	@Override
	public Response execute(Request request) {
		if(!(request instanceof UserIdForm)) {
			return new MessageResponse(Messages.ErrorMessages.FAIL_REQUEST_INSTANCE);
		}
		
		UserIdForm form = (UserIdForm) request;
		
		UserEntity entity = new UserEntity();
		
		databaseAccessTools.selectAccess(
				() -> userRepositoryImpl.findOne(form.getUserId()),
				(res) -> entity.setUsers(new ArrayList<UserData>(Arrays.asList(res))),
				() -> entity.setUsers(null)
		);
		
		return entity;
	}

}
