package com.api.domain.service;

import com.api.controller.form.Request;
import com.api.domain.response.Response;
import com.api.domain.service.userServiceImpl.GetUsersService;

/**
 * ユーザサービスのインターフェース
 * ユーザサービスの各実装クラスはこのクラスをimplementsして使用します
 * 
 * @author yamadaT
 *
 */
public interface UserService {

	/**
	 * ユーザ機能に関する業務ロジックを実装する。詳細は各実装クラスを参照。
	 * {@link GetUsersService}
	 * @param request
	 * @return response
	 */
	public Response execute(Request request);
	
}
