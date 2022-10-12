package com.api.controller.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * ユーザIDを扱うFormクラス
 * @author yamadaT
 *
 */
@Data
public class UserIdForm implements Request{

	/**
	 * ユーザID
	 */
	@NotBlank
	private String userId;
	
}
