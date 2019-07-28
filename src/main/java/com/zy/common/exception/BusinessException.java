package com.zy.common.exception;

import com.zy.common.utils.StringUtils;

public class BusinessException extends RuntimeException {
	private String title="业务出现异常";
	private String codePrefix = "B_";
	private String type = "error";
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 错误代码
     */
    private String errorCode = null;
    /**
     * 可替换变量,相当于占位符
     */
    private String[] variables = null;
 
    public BusinessException(String errorCode) {
        this.errorCode = errorCode;
    }
 
    public BusinessException(String errorCode, String[] variables) {
        this.errorCode = errorCode;
        this.variables = StringUtils.cloneStrings(variables);
    }
 
    public BusinessException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }
 
    public BusinessException(String errorCode, String errorMessage,
       String[] variables) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.variables = StringUtils.cloneStrings(variables);
    }
 
    public BusinessException(String errorCode, Throwable t) {
        super(t);
        this.errorCode = errorCode;
    }
 
    public BusinessException(String errorCode, String message, Throwable t) {
        super(message, t);
        this.errorCode = errorCode;
    }
 
    public BusinessException(String errorCode, String[] variables, Throwable t) {
        super(t);
        this.errorCode = errorCode;
        this.variables = StringUtils.cloneStrings(variables);
    }
 
    public BusinessException(String errorCode, String errorMessage,
       String[] variables, Throwable t) {
        super(errorMessage, t);
        this.errorCode = errorCode;
        this.variables = StringUtils.cloneStrings(variables);
    }
 
    public String getErrorCode() {
        return this.errorCode;
    }
 
    public String[] getVariable() {
        return StringUtils.cloneStrings(this.variables);
    }
    
	public String getMessage() {
		if (super.getMessage() != null) {
			return super.getMessage();
		} else {
			return "未知的错误";
		}
	}

    /**
     * 异常堆栈增加错误代码和绑定变量
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        String errorCode = getErrorCode();
        if(!errorCode.contains(getCodePrefix())){
        	errorCode = getCodePrefix()+errorCode;
        }
        sb.append("错误代码:").append(errorCode).append("");
        if (variables != null && variables.length != 0) {
       sb.append(",\"绑定变量:");
       for (int i = 0; i < variables.length; i++) {
           sb.append(variables[i]+"-");
       }
       sb.append("\"");
        }
        sb.append("\",\""+super.toString());
        return sb.toString();
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
