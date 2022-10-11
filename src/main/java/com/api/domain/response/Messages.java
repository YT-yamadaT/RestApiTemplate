package com.api.domain.response;

/**
 * メッセージを出力するための定数クラスです
 * @author yamadaT
 *
 */
public final class Messages implements Response{
	
	private Messages() {}
	
	/**
	 * エラーメッセージ系の定数が入っているクラス
	 * DB_ERROR_SELECT
	 * DB_ERROR_UPDATE
	 * REQUEST_PARAM_ERROR
	 * ATTACK_DETECTION
	 * 
	 * @author yamadaT
	 *
	 */
	public static class ErrorMessages {
		public static final String DB_ERROR_SELECT = "エラーが発生しました";
		public static final String DB_ERROR_UPDATE = "更新に失敗しました";
		public static final String REQUEST_PARAM_ERROR = "リクエストの値が不正です";
		public static final String FAIL_REQUEST_INSTANCE = "エラーが発生しました";
		public static final String ATTACK_DETECTION = "攻撃を検知しました。ログに記録します";
		public static final String FORBIDDEN = "許可されていないURLです。ログに記録します";
		public static final String NO_LOGIN = "ログインしているユーザのみ使用可能です。ログインしてください";
	}
	
	/**
	 * 成功メッセージ系の定数が入っているクラス
	 * DB_SUCCESS_SELECT
	 * DB_SUCCESS_UPDATE
	 * @author yamadaT
	 *
	 */
	public static class SuccessMessages {
		public static final String DB_SUCCESS_SELECT = "正常に値を取得しました";
		public static final String DB_SUCCESS_UPDATE = "正常に値を更新しました";
	}
	
}
