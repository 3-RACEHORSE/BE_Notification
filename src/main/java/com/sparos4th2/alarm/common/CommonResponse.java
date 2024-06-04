package com.sparos4th2.alarm.common;

import com.sparos4th2.alarm.common.exception.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class CommonResponse<T> {
	private String message;
	private T data;
	private Error error;

	public CommonResponse(String message, T data, Error error) {
		this.message = message;
		this.data = data;
		this.error = error;
	}

	public static <T> CommonResponse<T> success(T data) {
		return new CommonResponse<>(null, data, null);
	}

	public static <T> CommonResponse<T> fail(ResponseStatus code, String message) {
		return new CommonResponse<>(message, null, new Error(code));
	}

	public static <T> CommonResponse<T> fail(ResponseStatus code, T data, String message) {
		return new CommonResponse<>(message, data, new Error(code));
	}
	@Getter
	@AllArgsConstructor
	static class Error {
		private ResponseStatus code;
	}

}
