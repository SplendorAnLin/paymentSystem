package com.yl.pay.pos.interfaces.P500001;

public class CmbcTransException extends Exception {

private static final long serialVersionUID = -91572399070580792L;
	
	public static String SECRET_KEY_ERROR="secret.key.error";
	
	public static String FIELD_NOT_DEFINED_IN_8583="field.not.define.in.8583";   
    
	public static String ERROR_PARSE_ITEM_LENGTH = "error.parse.item.length";
    public static String ERROR_DATA_TYPE ="error.data.type";
    public static String ERROR_DATA_LENGTH ="error.data.type";
    public static String ERROR_HESSIAN_REQUEST="error.hessian_request";
    
    
    public static String ERROR_ENCODING_CCB8583="error.encoding.ccb8583";
	
	public CmbcTransException(String msg, Throwable ex){
		super(msg, ex);
	}
	
	public CmbcTransException(String msg) {
		super(msg);
	}

	public CmbcTransException(Throwable ex) {
		super(ex);
	}

}
