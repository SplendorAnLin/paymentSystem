package com.yl.online.gateway.enums;

import com.yl.online.gateway.ExceptionMessages;

public enum ResponseCodeChange {

	SUCCESS("I0000", "0000", "SUCCESS"),
	UNKOWN_ERROR("I0043", ExceptionMessages.UNKOWN_ERROR, "UNKOWN_ERROR"),
	USERPAYING("I0042",ExceptionMessages.USERPAYING, "USERPAYING"),
	NOTENOUGH("I0033",ExceptionMessages.NOTENOUGH, "NOTENOUGH"),
	AUTH_CODE_INVALID("I0032",ExceptionMessages.AUTH_CODE_INVALID, "AUTH_CODE_INVALID")
	;
	private String insideRespCode;
	private String outsideRespCode;
	private String outsideRespMsg;

	private ResponseCodeChange(String insideRespCode, String outsideRespCode, String outsideRespMsg) {
		this.insideRespCode = insideRespCode;
		this.outsideRespCode = outsideRespCode;
		this.outsideRespMsg = outsideRespMsg;
	}
	
	public static ResponseCodeChange getOutsideRespCode(String insideRespCode) {
		ResponseCodeChange[] responseCodeChanges = ResponseCodeChange.values();
		for (ResponseCodeChange responseCodeChange : responseCodeChanges) {
			if (responseCodeChange.getInsideRespCode().equals(insideRespCode)) {
				return responseCodeChange;
			}
		}
		return UNKOWN_ERROR;
	}

	public String getInsideRespCode() {
		return insideRespCode;
	}

	public void setInsideRespCode(String insideRespCode) {
		this.insideRespCode = insideRespCode;
	}

	public String getOutsideRespCode() {
		return outsideRespCode;
	}

	public void setOutsideRespCode(String outsideRespCode) {
		this.outsideRespCode = outsideRespCode;
	}

	public String getOutsideRespMsg() {
		return outsideRespMsg;
	}

	public void setOutsideRespMsg(String outsideRespMsg) {
		this.outsideRespMsg = outsideRespMsg;
	}

}
