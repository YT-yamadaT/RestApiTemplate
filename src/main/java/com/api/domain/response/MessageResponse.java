package com.api.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * メッセージ単体のみ返したい場合のレスポンスです
 * 主にエラーが発生した際や、データの更新をする際に使用します
 * @author yamadaT
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse implements Response{

	/**メッセージ*/
	private String message;
	
}
