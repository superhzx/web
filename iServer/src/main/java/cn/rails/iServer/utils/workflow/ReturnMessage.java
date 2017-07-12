package cn.rails.iServer.utils.workflow;

public class ReturnMessage {
    //ID
	private String ID;
	//是否成功
	private Boolean IsSuccess=false;
	//返回信息
	private Object Message;
	//具体结果
	private Object Result;
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public Boolean getIsSuccess() {
		return IsSuccess;
	}
	public void setIsSuccess(Boolean isSuccess) {
		IsSuccess = isSuccess;
	}
	public Object getMessage() {
		return Message;
	}
	public void setMessage(Object message) {
		Message = message;
	}
	public Object getResult() {
		return Result;
	}
	public void setResult(Object result) {
		Result = result;
	}
	
	/*
	 * 
	 */	
	public ReturnMessage(String iD, Boolean isSuccess, Object message,
			Object result) {
		super();
		ID = iD;
		IsSuccess = isSuccess;
		Message = message;
		Result = result;
	}
	
	
	public ReturnMessage() {
		super();
	}
	
	
}
