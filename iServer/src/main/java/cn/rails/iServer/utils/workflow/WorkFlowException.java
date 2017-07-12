package cn.rails.iServer.utils.workflow;

/**
 * <p>Title      : 中国铁道科学研究院业务流程管理平台[流程管理]</p>
 * <p>Description: [流程管理 异常]</p>
 * <p>Copyright  : Copyright (c) 2015</p>
 * <p>Company    : 铁科院电子所</p>
 * <p>Department : 信息中心</p>
 * @author       : sunyh
 * @version      : 1.0
 * @date         ：2017-02-06
 */
public class WorkFlowException extends Exception {

	private static final long serialVersionUID = 993119849701990353L;

	public WorkFlowException() {
    	super();
    }

    public WorkFlowException(String message) {
    	super(message);
    }

    public WorkFlowException(String message, Throwable cause) {
        super(message, cause);
    }

    public WorkFlowException(Throwable cause) {
        super(cause);
    }
}
