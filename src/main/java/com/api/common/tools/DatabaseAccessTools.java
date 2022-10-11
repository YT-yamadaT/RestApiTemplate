package com.api.common.tools;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;

/**
 * データベースアクセスに関するツールを提供します
 * 主にサービスクラスにて使用する
 * @author yamadaT
 *
 */
public class DatabaseAccessTools {

	
	/**
	 * 主にサービスにてfind系を使用する際に使用します
	 * <li>
	 * <ul>第一引数にDBにアクセスするメソッドを指定します</ul>
	 * <ul>第二引数にアクセスした結果をセットするメソッドを指定します</ul>
	 * <ul>第三引数にアクセスした際、Exception発生した時のメソッドを指定します</ul>
	 * </li>
	 * @param <T> (Response)　
	 * @param execute
	 * @param setter
	 * @param error
	 * @return entity
	 */
	public <T> T selectAccess(Supplier<T> execute, Consumer<T> setter, Runnable error) {
		T result;
		try {
			result = execute.get();
		}catch (DataAccessException e) {
			//TODO log
			System.out.println(e);
			error.run();
			Supplier<T> errorValue = () -> null;
			return errorValue.get();
		}
		setter.accept(result);
		return result;
	}
	
	/**
	 * 主にサービスにて更新系を使用する際に使用します
	 * <li>
	 * <ul>第一引数にDBにアクセスするメソッドを指定します</ul>
	 * <ul>第二引数に成功した際のResponseを指定します</ul>
	 * <ul>第三引数にExceprionが発生した際のResponseを指定します</ul>
	 * </li>
	 * 
	 * @param <T>
	 * @param execute
	 * @param success
	 * @param error
	 * @return
	 */
	public <T> T updateAccess(Runnable execute, Supplier<T> success, Supplier<T> error) {
		try {
			execute.run();
		}catch (JdbcUpdateAffectedIncorrectNumberOfRowsException e) {
			//TODO Log
			System.out.println(e);
			return error.get();
		}
		return success.get();
	}
}