package com.zy.common.exception;

public class ApplicationException extends BusinessException {
	private String title="应用出现异常";
	private String codePrefix = "A_";
	private String type = "error";

	private static final long serialVersionUID = -7055064026384970196L;

	public ApplicationException(String errorCode) {
    	super(errorCode);
    }
 
    public ApplicationException(String errorCode, String[] variables) {
    	super(errorCode, variables);
    }
 
    public ApplicationException(String errorCode, String errorMessage) {
       super(errorCode, errorMessage);
    }
 
    public ApplicationException(String errorCode, String errorMessage,
       String[] variables) {
    	super(errorCode, errorMessage, variables);
    }
 
    public ApplicationException(String errorCode, Throwable t) {
        super(errorCode, t);
    }
 
    public ApplicationException(String errorCode, String message, Throwable t) {
        super(errorCode, message, t);
    }
 
    public ApplicationException(String errorCode, String[] variables, Throwable t) {
      super(errorCode, variables, t);
    }
 
    public ApplicationException(String errorCode, String errorMessage,
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
    public String toString() {
    	return super.toString();
    }
}
