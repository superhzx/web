package cn.rails.iServer.core.service.workflow.serviceImpl;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.encoding.XMLType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rails.iServer.core.service.workflow.WorkFlowService;
import cn.rails.iServer.utils.workflow.SymmetricEncoder;
import cn.rails.iServer.utils.workflow.WorkFlowConstants;
import cn.rails.iServer.utils.workflow.WorkFlowException;

/**
 * <p>Title      : 中国铁道科学研究院流程管理平台[流程管理]</p>
 * <p>Description: [H3BPM 流程相关接口]</p>
 * <p>Copyright  : Copyright (c) 2017</p>
 * <p>Company    : 铁科院电子所</p>
 * <p>Department : 信息中心</p>
 * @author       : sunyh
 * @version      : 1.0
 * @date         ：2017-02-07
 */
@Service
@Transactional
public class WorkFlowServiceImpl implements WorkFlowService{
	String key="ae125efkk4454eef";
	/**
	 * 启动流程
	 * @param workflowCode 流程编码
	 * @param userAlias 用户编码	
	 * @param finishStart 是否结束第一个审批节点
	 * @param xmlData xml格式数据 点击接口定义具体查看用法：…… <XmlDataList><is_outside>1</is_outside></XmlDataList>
	 * @throws WorkFlowException 流程异常
	 */
	@Override
	public String StartWorkflowWithXmlData(String workflowCode,String userAlias, boolean finishStart, 
			String xmlData ,String instanceName)throws WorkFlowException {
		String result = WorkFlowConstants.OPERATION_RETURN_NULL;
		String finish="false";
		if(finishStart==false){
			finish="false";
		}
		if(finishStart==true){
			finish="true";
		}
        try { 
        	org.apache.axis.client.Service axisService = new org.apache.axis.client.Service();
        	Call call = (Call) axisService.createCall();  
			call.setTargetEndpointAddress(new java.net.URL(WorkFlowConstants.URL_SERVICE_BPM));
			call.setUseSOAPAction(true);  
		    call.setReturnType(new QName(WorkFlowConstants.XML_SCHEMA,"string"));  
		    call.setOperationName(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "StartWorkflowWithXmlData"));  
		    call.setSOAPActionURI(WorkFlowConstants.URL_SERVICE_OPERATION + "StartWorkflowWithXmlData"); 
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "workflowCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "userAlias"), XMLType.XSD_STRING, ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "finishStart"), XMLType.XSD_BOOLEAN, ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "xmlData"), XMLType.XSD_STRING, ParameterMode.IN);	
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "instanceName"), XMLType.XSD_STRING, ParameterMode.IN);	
		    result = (String) call.invoke(new Object[]{workflowCode,userAlias,finish,xmlData,instanceName});
		    result=SymmetricEncoder.Decrypt(result,key);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 转发流程（设置某个工作任务转办A->B，B提交后直接往下走）
	 * @param workItemId 工作实例ID
	 * @param userCode 操作者编码	
	 * @param commentText 转发意见
	 * @param participant 代办人编码
	 * @throws WorkFlowException 流程异常
	 * @return boolean 是否转发成功
	 */
	@Override
	public String adjustWorkflow(String workItemId, String userCode,String commentText,String participant) 
			throws WorkFlowException {
		String result = WorkFlowConstants.OPERATION_RETURN_NULL;
		org.apache.axis.client.Service axisService = new org.apache.axis.client.Service();
		try {
			Call call = (Call) axisService.createCall();  
			call.setTargetEndpointAddress(new java.net.URL(WorkFlowConstants.URL_SERVICE_BPM));
			call.setUseSOAPAction(true);  
		    call.setReturnType(new QName(WorkFlowConstants.XML_SCHEMA,"string"));  
		    call.setOperationName(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "AdjustWorkflow"));  
		    call.setSOAPActionURI(WorkFlowConstants.URL_SERVICE_OPERATION + "AdjustWorkflow");
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "workItemId"), XMLType.XSD_STRING, ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "userCode"), XMLType.XSD_STRING, ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "commentText"), XMLType.XSD_STRING, ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "participant"), XMLType.XSD_STRING, ParameterMode.IN);
		    result = (String) call.invoke(new Object[]{workItemId,userCode,commentText,participant}); 
		    result=SymmetricEncoder.Decrypt(result,key);
		} catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 结束流程
	 * @param userCode 操作者编码	
	 * @param instanceId 流程实例ID
	 * @throws WorkFlowException 流程异常
	 * @return boolean 是否成功
	 */
	@Override
	public String finishInstance(String userCode, String instanceId)throws WorkFlowException {
		String result = WorkFlowConstants.OPERATION_RETURN_NULL;
		org.apache.axis.client.Service axisService = new org.apache.axis.client.Service();
        try { 
        	Call call = (Call) axisService.createCall();  
			call.setTargetEndpointAddress(new java.net.URL(WorkFlowConstants.URL_SERVICE_BPM));
			call.setUseSOAPAction(true);  
		    call.setReturnType(new QName(WorkFlowConstants.XML_SCHEMA,"string"));  
		    call.setOperationName(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "FinishInstance"));  
		    call.setSOAPActionURI(WorkFlowConstants.URL_SERVICE_OPERATION + "FinishInstance"); 
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "userCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "instanceId"), XMLType.XSD_STRING, ParameterMode.IN);
		    result = (String) call.invoke(new Object[]{userCode,instanceId}); 
		    result=SymmetricEncoder.Decrypt(result,key);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 提交流程
	 * @param userCode 用户编码
	 * @param workItemIds 工作任务节点ID，用逗号分隔	
	 * @param activityCode 指定激活的节点（不能为空）
	 * @param participant 代办人编码,若为空取流程默认配置
	 * @param signature 签章信息
	 * @param commentText 审批意见
	 * @throws WorkFlowException 流程异常
	 * @return boolean 是否转发成功
	 */
	@Override
	public String submitWorkItem(String userCode, String workItemIds,String activityCode, String participant,
			String signature, String commentText) throws WorkFlowException {
		String result = WorkFlowConstants.OPERATION_RETURN_NULL;
		org.apache.axis.client.Service axisService = new org.apache.axis.client.Service();
        try { 
        	Call call = (Call) axisService.createCall();  
			call.setTargetEndpointAddress(new java.net.URL(WorkFlowConstants.URL_SERVICE_BPM));
			call.setUseSOAPAction(true);  
		    call.setReturnType(new QName(WorkFlowConstants.XML_SCHEMA,"string"));  
		    call.setOperationName(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "SubmitWorkItem"));  
		    call.setSOAPActionURI(WorkFlowConstants.URL_SERVICE_OPERATION + "SubmitWorkItem"); 
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "userCode"), XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "workItemIds"), XMLType.XSD_STRING, ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "activityCode"), XMLType.XSD_STRING, ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "participant"), XMLType.XSD_STRING, ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "signature"), XMLType.XSD_STRING, ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "commentText"), XMLType.XSD_STRING, ParameterMode.IN);
		    result = (String) call.invoke(new Object[]{userCode,workItemIds,activityCode,participant,signature,commentText}); 
		    result=SymmetricEncoder.Decrypt(result,key);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 驳回流程
	 * @param userCode 用户编码
	 * @param workItemId 工作任务节点ID，用逗号分隔	
	 * @param commentText 审批意见
	 * @param type 驳回类型（1：驳回到起始节点 2：驳回到上个节点 3：驳回到指定节点）
	 * @param activityCode 当type为3时，驳回到指定节点，其他类型为空
	 * @param signature 签章信息
	 * @param participant 取回任务的参与者（默认参与者为上一步完成者，多人参与逗号分隔）
	 * @throws WorkFlowException 流程异常
	 * @return boolean 是否转发成功
	 */
	@Override
	public String returnItem(String userCode,String workItemId,String commentText,String type,String activityCode,String signature)throws WorkFlowException {
		String result = WorkFlowConstants.OPERATION_RETURN_NULL;
		org.apache.axis.client.Service axisService = new org.apache.axis.client.Service();	
        try { 
        	Call call = (Call) axisService.createCall();  
			call.setTargetEndpointAddress(new java.net.URL(WorkFlowConstants.URL_SERVICE_BPM));
			call.setUseSOAPAction(true);  
		    call.setReturnType(new QName(WorkFlowConstants.XML_SCHEMA,"string"));  
		    call.setOperationName(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "RetrieveWorkItem"));  
		    call.setSOAPActionURI(WorkFlowConstants.URL_SERVICE_OPERATION + "RetrieveWorkItem"); 
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "userCode"), XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "workItemId"), XMLType.XSD_STRING, ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "commentText"), XMLType.XSD_STRING, ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "type"), XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "activityCode"), XMLType.XSD_STRING, ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "signature"), XMLType.XSD_STRING, ParameterMode.IN);
		    result = (String) call.invoke(new Object[]{userCode,workItemId,commentText,type,activityCode,signature}); 
		    result=SymmetricEncoder.Decrypt(result,key);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取下一节点信息
	 * @param workItemId 工作任务节点ID
	 * @throws WorkFlowException 流程异常
	 */
	@Override
	public String getNextNode(String workItemId)throws WorkFlowException {
		String result = WorkFlowConstants.OPERATION_RETURN_NULL;
        try { 
        	org.apache.axis.client.Service axisService = new org.apache.axis.client.Service();
        	Call call = (Call) axisService.createCall();  
			call.setTargetEndpointAddress(new java.net.URL(WorkFlowConstants.URL_SERVICE_BPM));
			call.setUseSOAPAction(true);  
		    call.setReturnType(new QName(WorkFlowConstants.XML_SCHEMA,"string"));  
		    call.setOperationName(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "GetNextNode"));  
		    call.setSOAPActionURI(WorkFlowConstants.URL_SERVICE_OPERATION + "GetNextNode"); 
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "workItemId"),XMLType.XSD_STRING,ParameterMode.IN);
		    result = (String) call.invoke(new Object[]{workItemId}); 
		    result=SymmetricEncoder.Decrypt(result,key);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取用户的任务列表
	 * @param userCode 用户编码(为空则查询所有用户)
	 * @param pageSize 每页显示数据量(pageSize，pageIndex，同时为0显示全部)
	 * @param pageIndex 当前页码，从1开始(pageSize，pageIndex，同时为0显示全部)，
	 * @param workflowCode 流程编码（为空则显示全部）
	 * @param state 类型（1待办，2已办）
	 * @param activityCodes 流程节点（为空则显示全部）
	 * @param instanceId 流程实例Id
	 * @throws WorkFlowException 流程异常
	 */
	@Override
	public String getWorkItem(String userCode,String pageSize,String pageIndex,String workflowCode,
			String state,String activityCodes,String instanceId)throws WorkFlowException {
		String result = WorkFlowConstants.OPERATION_RETURN_NULL;
        try { 
        	org.apache.axis.client.Service axisService = new org.apache.axis.client.Service();
        	Call call = (Call) axisService.createCall();  
			call.setTargetEndpointAddress(new java.net.URL(WorkFlowConstants.URL_SERVICE_BPM));
			call.setUseSOAPAction(true);  
		    call.setReturnType(new QName(WorkFlowConstants.XML_SCHEMA,"string"));  
		    call.setOperationName(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "GetWorkItem"));  
		    call.setSOAPActionURI(WorkFlowConstants.URL_SERVICE_OPERATION + "GetWorkItem"); 
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "userCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "pageSize"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "pageIndex"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "workflowCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "state"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "activityCodes"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "instanceId"),XMLType.XSD_STRING,ParameterMode.IN);
		    result = (String) call.invoke(new Object[]{userCode,pageSize,pageIndex,workflowCode,state,activityCodes,instanceId}); 
		    result=SymmetricEncoder.Decrypt(result,key);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 添加部门
	 * @param OUCode 部门编码，要确保唯一
	 * @param OUName 部门名称
	 * @param ParentOUCode 部门上一级code
	 * @param SortKeyx 排序码，只能是数字，用来排列组织架构的顺序
	 * @throws WorkFlowException 流程异常
	 */
	@Override
	public String AddDepartment(String OUCode, String OUName,
			String ParentOUCode, String SortKeyx) throws WorkFlowException {
		String result = WorkFlowConstants.OPERATION_RETURN_NULL;
        try { 
        	org.apache.axis.client.Service axisService = new org.apache.axis.client.Service();
        	Call call = (Call) axisService.createCall();  
			call.setTargetEndpointAddress(new java.net.URL(WorkFlowConstants.URL_SERVICE_BPM));
			call.setUseSOAPAction(true);  
		    call.setReturnType(new QName(WorkFlowConstants.XML_SCHEMA,"string"));  
		    call.setOperationName(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "AddDepartment"));  
		    call.setSOAPActionURI(WorkFlowConstants.URL_SERVICE_OPERATION + "AddDepartment"); 
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "OUCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "OUName"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "ParentOUCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "SortKeyx"),XMLType.XSD_STRING,ParameterMode.IN);
		    result = (String) call.invoke(new Object[]{OUCode,OUName,ParentOUCode,SortKeyx}); 
		    result=SymmetricEncoder.Decrypt(result,key);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 更新部门
	 * @param OUCode 部门编码，要确保唯一
	 * @param OUName 部门名称
	 * @param ParentOUCode 部门上一级code
	 * @param SortKeyx 排序码，只能是数字，用来排列组织架构的顺序
	 * @throws WorkFlowException 流程异常
	 */
	@Override
	public String UpdateDepartment(String OUCode, String OUName,
			String ParentOUCode, String SortKeyx) throws WorkFlowException {
		String result = WorkFlowConstants.OPERATION_RETURN_NULL;
        try { 
        	org.apache.axis.client.Service axisService = new org.apache.axis.client.Service();
        	Call call = (Call) axisService.createCall();  
			call.setTargetEndpointAddress(new java.net.URL(WorkFlowConstants.URL_SERVICE_BPM));
			call.setUseSOAPAction(true);  
		    call.setReturnType(new QName(WorkFlowConstants.XML_SCHEMA,"string"));  
		    call.setOperationName(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "UpdateDepartment"));  
		    call.setSOAPActionURI(WorkFlowConstants.URL_SERVICE_OPERATION + "UpdateDepartment"); 
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "OUCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "OUName"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "ParentOUCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "SortKeyx"),XMLType.XSD_STRING,ParameterMode.IN);
		    result = (String) call.invoke(new Object[]{OUCode,OUName,ParentOUCode,SortKeyx}); 
		    result=SymmetricEncoder.Decrypt(result,key);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 删除部门
	 * @param OUCode 部门编码，要确保唯一
	 * @throws WorkFlowException 流程异常
	 */
	@Override
	public String DeleteDepartment(String OUCode) throws WorkFlowException {
		String result = WorkFlowConstants.OPERATION_RETURN_NULL;
        try { 
        	org.apache.axis.client.Service axisService = new org.apache.axis.client.Service();
        	Call call = (Call) axisService.createCall();  
			call.setTargetEndpointAddress(new java.net.URL(WorkFlowConstants.URL_SERVICE_BPM));
			call.setUseSOAPAction(true);  
		    call.setReturnType(new QName(WorkFlowConstants.XML_SCHEMA,"string"));  
		    call.setOperationName(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "DeleteDepartment"));  
		    call.setSOAPActionURI(WorkFlowConstants.URL_SERVICE_OPERATION + "DeleteDepartment"); 
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "OUCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    result = (String) call.invoke(new Object[]{OUCode}); 
		    result=SymmetricEncoder.Decrypt(result,key);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 添加用户
	 * @param userCode 用户编码
	 * @param name 用户名称
	 * @param departCode 用户所在部门编码, 用户所在机构Code如果为空则添加到总公司下面
	 * @throws WorkFlowException 流程异常
	 */
	@Override
	public String AddUser(String userCode, String name, String departCode)
			throws WorkFlowException {
		String result = WorkFlowConstants.OPERATION_RETURN_NULL;
        try { 
        	org.apache.axis.client.Service axisService = new org.apache.axis.client.Service();
        	Call call = (Call) axisService.createCall();  
			call.setTargetEndpointAddress(new java.net.URL(WorkFlowConstants.URL_SERVICE_BPM));
			call.setUseSOAPAction(true);  
		    call.setReturnType(new QName(WorkFlowConstants.XML_SCHEMA,"string"));  
		    call.setOperationName(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "AddUser"));  
		    call.setSOAPActionURI(WorkFlowConstants.URL_SERVICE_OPERATION + "AddUser"); 
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "userCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "name"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "departCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    result = (String) call.invoke(new Object[]{userCode,name,departCode}); 
		    result=SymmetricEncoder.Decrypt(result,key);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 更新用户
	 * @param userCode 用户编码
	 * @param name 用户名称
	 * @param departCode 用户所在部门编码, 用户所在机构Code如果为空则添加到总公司下面
	 * @throws WorkFlowException 流程异常
	 */
	@Override
	public String UpdateUser(String userCode, String name, String departCode)
			throws WorkFlowException {
		String result = WorkFlowConstants.OPERATION_RETURN_NULL;
        try { 
        	org.apache.axis.client.Service axisService = new org.apache.axis.client.Service();
        	Call call = (Call) axisService.createCall();  
			call.setTargetEndpointAddress(new java.net.URL(WorkFlowConstants.URL_SERVICE_BPM));
			call.setUseSOAPAction(true);  
		    call.setReturnType(new QName(WorkFlowConstants.XML_SCHEMA,"string"));  
		    call.setOperationName(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "UpdateUser"));  
		    call.setSOAPActionURI(WorkFlowConstants.URL_SERVICE_OPERATION + "UpdateUser"); 
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "userCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "name"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "departCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    result = (String) call.invoke(new Object[]{userCode,name,departCode}); 
		    result=SymmetricEncoder.Decrypt(result,key);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 删除用户
	 * @param userCode 用户编码
	 * @throws WorkFlowException 流程异常
	 */
	@Override
	public String DeleteUser(String userCode) throws WorkFlowException {
		String result = WorkFlowConstants.OPERATION_RETURN_NULL;
        try { 
        	org.apache.axis.client.Service axisService = new org.apache.axis.client.Service();
        	Call call = (Call) axisService.createCall();  
			call.setTargetEndpointAddress(new java.net.URL(WorkFlowConstants.URL_SERVICE_BPM));
			call.setUseSOAPAction(true);  
		    call.setReturnType(new QName(WorkFlowConstants.XML_SCHEMA,"string"));  
		    call.setOperationName(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "DeleteUser"));  
		    call.setSOAPActionURI(WorkFlowConstants.URL_SERVICE_OPERATION + "DeleteUser"); 
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "userCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    result = (String) call.invoke(new Object[]{userCode}); 
		    result=SymmetricEncoder.Decrypt(result,key);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 添加职务（业务角色）
	 * @param jobCode 职务编码，要确保唯一
	 * @param jobName 职务名称名称
	 * @throws WorkFlowException 流程异常
	 */
	@Override
	public String AddJob(String jobCode, String jobName)
			throws WorkFlowException {
		String result = WorkFlowConstants.OPERATION_RETURN_NULL;
        try { 
        	org.apache.axis.client.Service axisService = new org.apache.axis.client.Service();
        	Call call = (Call) axisService.createCall();  
			call.setTargetEndpointAddress(new java.net.URL(WorkFlowConstants.URL_SERVICE_BPM));
			call.setUseSOAPAction(true);  
		    call.setReturnType(new QName(WorkFlowConstants.XML_SCHEMA,"string"));  
		    call.setOperationName(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "AddJob"));  
		    call.setSOAPActionURI(WorkFlowConstants.URL_SERVICE_OPERATION + "AddJob"); 
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "jobCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "jobName"),XMLType.XSD_STRING,ParameterMode.IN);
		    result = (String) call.invoke(new Object[]{jobCode,jobName}); 
		    result=SymmetricEncoder.Decrypt(result,key);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 更新职务（业务角色）
	 * @param jobCode 职务编码，要确保唯一
	 * @param jobName 职务名称名称
	 * @throws WorkFlowException 流程异常
	 */
	@Override
	public String UpdateJob(String jobCode, String jobName)
			throws WorkFlowException {
		String result = WorkFlowConstants.OPERATION_RETURN_NULL;
        try { 
        	org.apache.axis.client.Service axisService = new org.apache.axis.client.Service();
        	Call call = (Call) axisService.createCall();  
			call.setTargetEndpointAddress(new java.net.URL(WorkFlowConstants.URL_SERVICE_BPM));
			call.setUseSOAPAction(true);  
		    call.setReturnType(new QName(WorkFlowConstants.XML_SCHEMA,"string"));  
		    call.setOperationName(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "UpdateJob"));  
		    call.setSOAPActionURI(WorkFlowConstants.URL_SERVICE_OPERATION + "UpdateJob"); 
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "jobCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "jobName"),XMLType.XSD_STRING,ParameterMode.IN);
		    result = (String) call.invoke(new Object[]{jobCode,jobName}); 
		    result=SymmetricEncoder.Decrypt(result,key);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 删除职务（业务角色）
	 * @param jobCode 职务编码，要确保唯一
	 * @throws WorkFlowException 流程异常
	 */
	@Override
	public String DeleteJob(String jobCode) throws WorkFlowException {
		String result = WorkFlowConstants.OPERATION_RETURN_NULL;
        try { 
        	org.apache.axis.client.Service axisService = new org.apache.axis.client.Service();
        	Call call = (Call) axisService.createCall();  
			call.setTargetEndpointAddress(new java.net.URL(WorkFlowConstants.URL_SERVICE_BPM));
			call.setUseSOAPAction(true);  
		    call.setReturnType(new QName(WorkFlowConstants.XML_SCHEMA,"string"));  
		    call.setOperationName(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "DeleteJob"));  
		    call.setSOAPActionURI(WorkFlowConstants.URL_SERVICE_OPERATION + "DeleteJob"); 
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "jobCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    result = (String) call.invoke(new Object[]{jobCode}); 
		    result=SymmetricEncoder.Decrypt(result,key);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 添加岗位（组织角色）
	 * @param postCode 岗位编码，要确保唯一
	 * @param postName 岗位名称
	 * @param jobCode 对应职务code
	 * @param unitCode 所属组织的code如果为空则添加到总公司下面
	 * @throws WorkFlowException 流程异常
	 */
	@Override
	public String AddPost(String postCode, String postName, String jobCode,
			String unitCode) throws WorkFlowException {
		String result = WorkFlowConstants.OPERATION_RETURN_NULL;
        try { 
        	org.apache.axis.client.Service axisService = new org.apache.axis.client.Service();
        	Call call = (Call) axisService.createCall();  
			call.setTargetEndpointAddress(new java.net.URL(WorkFlowConstants.URL_SERVICE_BPM));
			call.setUseSOAPAction(true);  
		    call.setReturnType(new QName(WorkFlowConstants.XML_SCHEMA,"string"));  
		    call.setOperationName(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "AddPost"));  
		    call.setSOAPActionURI(WorkFlowConstants.URL_SERVICE_OPERATION + "AddPost"); 
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "postCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "postName"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "jobCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "unitCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    result = (String) call.invoke(new Object[]{postCode,postName,jobCode,unitCode}); 
		    result=SymmetricEncoder.Decrypt(result,key);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 更新岗位（组织角色）
	 * @param postCode 岗位编码，要确保唯一
	 * @param postName 岗位名称
	 * @param jobCode 对应职务code
	 * @param unitCode 所属组织的code如果为空则添加到总公司下面
	 * @throws WorkFlowException 流程异常
	 */
	@Override
	public String Updatepost(String postCode, String postName, String jobCode,
			String unitCode) throws WorkFlowException {
		String result = WorkFlowConstants.OPERATION_RETURN_NULL;
        try { 
        	org.apache.axis.client.Service axisService = new org.apache.axis.client.Service();
        	Call call = (Call) axisService.createCall();  
			call.setTargetEndpointAddress(new java.net.URL(WorkFlowConstants.URL_SERVICE_BPM));
			call.setUseSOAPAction(true);  
		    call.setReturnType(new QName(WorkFlowConstants.XML_SCHEMA,"string"));  
		    call.setOperationName(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "Updatepost"));  
		    call.setSOAPActionURI(WorkFlowConstants.URL_SERVICE_OPERATION + "Updatepost"); 
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "postCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "postName"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "jobCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "unitCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    result = (String) call.invoke(new Object[]{postCode,postName,jobCode,unitCode}); 
		    result=SymmetricEncoder.Decrypt(result,key);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 删除岗位（组织角色）
	 * @param postCode 岗位编码，要确保唯一
	 * @throws WorkFlowException 流程异常
	 */
	@Override
	public String DeletePost(String postCode) throws WorkFlowException {
		String result = WorkFlowConstants.OPERATION_RETURN_NULL;
        try { 
        	org.apache.axis.client.Service axisService = new org.apache.axis.client.Service();
        	Call call = (Call) axisService.createCall();  
			call.setTargetEndpointAddress(new java.net.URL(WorkFlowConstants.URL_SERVICE_BPM));
			call.setUseSOAPAction(true);  
		    call.setReturnType(new QName(WorkFlowConstants.XML_SCHEMA,"string"));  
		    call.setOperationName(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "DeletePost"));  
		    call.setSOAPActionURI(WorkFlowConstants.URL_SERVICE_OPERATION + "DeletePost"); 
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "postCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    result = (String) call.invoke(new Object[]{postCode}); 
		    result=SymmetricEncoder.Decrypt(result,key);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 更新岗位下所有用户
	 * @param postCode 岗位编码
	 * @param userCodes 用户Code集合，多个用户用英文','隔开 
	 * @throws WorkFlowException 流程异常
	 */
	@Override
	public String UpdatePostUsers(String postCode, String userCodes)
			throws WorkFlowException {
		String result = WorkFlowConstants.OPERATION_RETURN_NULL;
        try { 
        	org.apache.axis.client.Service axisService = new org.apache.axis.client.Service();
        	Call call = (Call) axisService.createCall();  
			call.setTargetEndpointAddress(new java.net.URL(WorkFlowConstants.URL_SERVICE_BPM));
			call.setUseSOAPAction(true);  
		    call.setReturnType(new QName(WorkFlowConstants.XML_SCHEMA,"string"));  
		    call.setOperationName(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "UpdatePostUsers"));  
		    call.setSOAPActionURI(WorkFlowConstants.URL_SERVICE_OPERATION + "UpdatePostUsers"); 
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "postCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "userCodes"),XMLType.XSD_STRING,ParameterMode.IN);
		    result = (String) call.invoke(new Object[]{postCode,userCodes}); 
		    result=SymmetricEncoder.Decrypt(result,key);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}

	
	/**
	 * 跟据流程获取当前活动实例（活动节点的信息）
	 * @param userCode 用户编码
	 * @param instanceId 工作实例ID 
	 * @throws WorkFlowException 流程异常
	 */
	@Override
	public String GetActiveInstanceInfo(String userCode, String instanceId) {
		String result = WorkFlowConstants.OPERATION_RETURN_NULL;
        try { 
        	org.apache.axis.client.Service axisService = new org.apache.axis.client.Service();
        	Call call = (Call) axisService.createCall();  
			call.setTargetEndpointAddress(new java.net.URL(WorkFlowConstants.URL_SERVICE_BPM));
			call.setUseSOAPAction(true);  
		    call.setReturnType(new QName(WorkFlowConstants.XML_SCHEMA,"string"));  
		    call.setOperationName(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "GetActiveInstanceInfo"));  
		    call.setSOAPActionURI(WorkFlowConstants.URL_SERVICE_OPERATION + "GetActiveInstanceInfo"); 
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "userCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "instanceId"),XMLType.XSD_STRING,ParameterMode.IN);
		    result = (String) call.invoke(new Object[]{userCode,instanceId}); 
		    result=SymmetricEncoder.Decrypt(result,key);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}

	
	/**
	 * 取消当前活动实例
	 * @param userCode 用户编码
	 * @param instanceId 工作实例ID 
	 * @throws WorkFlowException 流程异常
	 */
	@Override
	public String CancleInstance(String userCode, String instanceId) {
		String result = WorkFlowConstants.OPERATION_RETURN_NULL;
        try { 
        	org.apache.axis.client.Service axisService = new org.apache.axis.client.Service();
        	Call call = (Call) axisService.createCall();  
			call.setTargetEndpointAddress(new java.net.URL(WorkFlowConstants.URL_SERVICE_BPM));
			call.setUseSOAPAction(true);  
		    call.setReturnType(new QName(WorkFlowConstants.XML_SCHEMA,"string"));  
		    call.setOperationName(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "CancleInstance"));  
		    call.setSOAPActionURI(WorkFlowConstants.URL_SERVICE_OPERATION + "CancleInstance"); 
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "userCode"),XMLType.XSD_STRING,ParameterMode.IN);
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "instanceId"),XMLType.XSD_STRING,ParameterMode.IN);
		    result = (String) call.invoke(new Object[]{userCode,instanceId}); 
		    result=SymmetricEncoder.Decrypt(result,key);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取流程模板，查看流程进行到哪一步
	 * @param instanceId 工作实例ID 
	 * @throws WorkFlowException 流程异常
	 */
	@Override
	public String GetInstanceLog(String instanceId) {
		//instanceId = "fdde7b4e-8878-41d4-89e7-e7041619ba69";
		String result = WorkFlowConstants.OPERATION_RETURN_NULL;
        try { 
        	org.apache.axis.client.Service axisService = new org.apache.axis.client.Service();
        	Call call = (Call) axisService.createCall();  
			call.setTargetEndpointAddress(new java.net.URL(WorkFlowConstants.URL_SERVICE_BPM));
			call.setUseSOAPAction(true);  
		    call.setReturnType(new QName(WorkFlowConstants.XML_SCHEMA,"string"));  
		    call.setOperationName(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "GetInstanceLog"));  
		    call.setSOAPActionURI(WorkFlowConstants.URL_SERVICE_OPERATION + "GetInstanceLog"); 
		    call.addParameter(new QName(WorkFlowConstants.URL_SERVICE_OPERATION, "instanceId"),XMLType.XSD_STRING,ParameterMode.IN);
		    result = (String) call.invoke(new Object[]{instanceId}); 
		    result=SymmetricEncoder.Decrypt(result,key);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
