package com.zy.common.exception;

public class DexcoderException extends Exception {

	/**
	 * @Fields
	 */
	private static final long serialVersionUID = 3103374001254469988L;

	private String message = null;

	private String code = null;

	public DexcoderException(String message) {
		super(message);
		this.message = message;
	}

	public DexcoderException(Throwable e) {
		super(e);
	}

	public DexcoderException(String code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	/**
	 * 返回异常信息
	 * 
	 * @return
	 * @author dada
	 */
	public String getMessage() {
		if (this.message != null) {
			return this.message;
		} else {
			return "未知的错误";
		}
	}

	/**
	 * 转化为字符串的方法
	 * 
	 * @author dada
	 */
	public String toString() {
		String s = getClass().getName();
		String message = getMessage();
		return (code != null) ? (code + "-" + s + ": " + message) : s + ": " + message;
	}

}
