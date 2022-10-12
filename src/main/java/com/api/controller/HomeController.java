package com.api.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.common.tools.AuthorityTools;
import com.api.controller.form.UserIdForm;
import com.api.domain.response.MessageResponse;
import com.api.domain.response.Messages;
import com.api.domain.response.Response;
import com.api.domain.service.UserService;

@RestController
@RequestMapping("/user")
public class HomeController {
	
	@Autowired
	Map<String, UserService> userService;
	
	@Autowired
	AuthorityTools authorityTools;

	@GetMapping("/all")
	public Response getUsers(Principal principal) {
		if(principal == null) {
			this.unauthorized(new MessageResponse(Messages.ErrorMessages.NO_LOGIN));
		}
		
		if(!authorityTools.isAdmin(principal)) {
			this.forbidden(new MessageResponse(Messages.ErrorMessages.FORBIDDEN));
		}
		
		return userService.get("getUsersService").execute(null);
	}
	
	@GetMapping("")
	public Response getUser(Principal principal) {
		if(principal == null) {
			this.unauthorized(new MessageResponse(Messages.ErrorMessages.NO_LOGIN));
		}
		
		UserIdForm form = new UserIdForm();
		form.setUserId(principal.getName());
		
		return userService.get("getUserService").execute(form);
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
	
	/**
	 * 403 Forbiddenで返却する
	 * @param response
	 * @return response
	 */
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public Response forbidden(Response response) {
		return response;
	}
	
}
