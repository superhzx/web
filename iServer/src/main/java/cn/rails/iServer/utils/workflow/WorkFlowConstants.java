package cn.rails.iServer.utils.workflow;
import java.util.Properties;

import cn.rails.iServer.utils.ConfigUtil;

/**
 * 流程相关常量
 * */
public class WorkFlowConstants {
	public static Properties properties = ConfigUtil.properties;
		
	/**流程服务URL*/
	public static final String URL_SERVICE_BPM = properties.getProperty("URL_SERVICE_BPM");
	
	/**流程服务IP*/
	public static final String IP_SERVICE_BPM = properties.getProperty("IP_SERVICE_BPM");
	
	/**流程服务XMLSchema*/
	public static final String XML_SCHEMA = properties.getProperty("XML_SCHEMA");
	
	/**流程服务Operation URL*/
	public static final String URL_SERVICE_OPERATION = properties.getProperty("URL_SERVICE_OPERATION");
	
	/**流程服务返回值 没有返回结果*/
	public static final String OPERATION_RETURN_NULL = properties.getProperty("OPERATION_RETURN_NULL");
	
	/**审批流程编码(测试)*/
	public static final String WORKFLOWCODE_TEST = properties.getProperty("WORKFLOWCODE_TEST");

}
