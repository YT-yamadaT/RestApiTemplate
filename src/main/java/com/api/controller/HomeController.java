package com.api.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.domain.response.MessageResponse;
import com.api.domain.response.Messages;
import com.api.domain.response.Response;
import com.api.domain.service.UserService;

@RestController
public class HomeController {
	
	@Autowired
	Map<String, UserService> userService;

	@GetMapping
	public Response getUsers(Principal principal) {
		if(principal == null) {
			this.unauthorized(new MessageResponse(Messages.ErrorMessages.NO_LOGIN));
		}
		return userService.get("getUsersService").execute(null);
	}
	
	/**
	 * 401 Unauthorizedで返却する
	 * @param response
	 * @return response
	 */
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public Response unauthorized(Response response) {
		return response;
	}
	
	/**
	 * 400 BadRequestで返却する
	 * @param response
	 * @return response
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Response badRequest(Response response) {
		return response;
	}
}
