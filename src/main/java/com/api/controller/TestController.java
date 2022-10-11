package com.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.domain.response.Response;

@RestController
public class TestController {

	@GetMapping("/")
	public void test() throws Exception {

	}
	
	/**
	 * 401 Unauthorizedで返却する
	 * @param response
	 * @return response
	 */
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public Response Unauthorized(Response response) {
		return response;
	}
	
	/**
	 * 400 BadRequestで返却する
	 * @param response
	 * @return response
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Response BadRequest(Response response) {
		return response;
	}
}
