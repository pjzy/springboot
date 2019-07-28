package com.zy.common.exception;

public class SystemException extends BusinessException {

	private static final long serialVersionUID = 1553893994266866103L;
	private String title = "系统出现异常";
	private String codePrefix = "S_";
	private String type = "error";

    public SystemException(String errorCode) {
    	super(errorCode);
    }
 
    public SystemException(String errorCode, String[] variables) {
    	super(errorCode, variables);
    }
 
    public SystemException(String errorCode, String errorMessage) {
       super(errorCode, errorMessage);
    }
 
    public SystemException(String errorCode, String errorMessage,
       String[] variables) {
    	super(errorCode, errorMessage, variables);
    }
 
    public SystemException(String errorCode, Throwable t) {
        super(errorCode, t);
    }
 
    public SystemException(String errorCode, String message, Throwable t) {
        super(errorCode, message, t);
    }
 
    public SystemException(String errorCode, String[] variables, Throwable t) {
      super(errorCode, variables, t);
    }
 
    public SystemException(String errorCode, String errorMessage,
       String[] variables, Throwable t) {
    	super(errorCode, errorMessage, variables, t);
    }
    public String getTitle(){
    	return title;
    }

	public String getCodePrefix() {
		return codePrefix;
	}

	public String getType() {
		return type;
	}
}
