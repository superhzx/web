package cn.rails.iServer.core.service.workflow;

import cn.rails.iServer.utils.workflow.WorkFlowException;


/**
 * <p>Title      : 中国铁道科学研究院流程管理平台[流程管理]</p>
 * <p>Description: [H3BPM 流程相关接口]</p>
 * <p>Copyright  : Copyright (c) 2017</p>
 * <p>Company    : 铁科院电子所</p>
 * <p>Department : 信息中心</p>
 * @author       : sunyh
 * @version      : 1.0
 * @date         ：2017-02-06
 */
public interface WorkFlowService {
	/**
	 * 启动流程
	 * @param workflowCode 流程编码
	 * @param userAlias 用户编码
	 * @param finishStart 是否结束第一个审批节点
	 * @param xmlData xml格式数据 点击接口定义具体查看用法：…… <XmlDataList><contract_importance>1</contract_importance><is_outside>1</cgwis_outsidelmc></XmlDataList>
	 * @throws WorkFlowException 流程异常
	 */
	public String StartWorkflowWithXmlData(String workflowCode,String userAlias,
			boolean finishStart,String xmlData,String instanceName) throws WorkFlowException;
	
	/**
	 * 转发流程（设置某个工作任务转办A->B，B提交后直接往下走）
	 * @param workItemId 工作实例ID
	 * @param userCode 操作者编码	
	 * @param commentText 转发意见
	 * @param participant 代办人编码
	 * @throws WorkFlowException 流程异常
	 * @return boolean 是否转发成功
	 */
	public String adjustWorkflow(String workItemId, String userCode,
			String commentText, String participant) throws WorkFlowException;
	
	/**
	 * 结束流程
	 * @param userCode 操作者编码	
	 * @param instanceId 流程实例ID
	 * @throws WorkFlowException 流程异常
	 * @return boolean 是否成功
	 */
	public String finishInstance(String userCode, String instanceId)
			throws WorkFlowException;
	
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
	public String submitWorkItem(String userCode, String workItemIds,
			String activityCode, String participant, String signature,
			String commentText) throws WorkFlowException;

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
	public String returnItem(String userCode, String workItemId, String commentText,
			String type, String activityCode, String signature)
			throws WorkFlowException;
	/**
	 * 获取下一节点信息
	 * @param workItemId 工作任务节点ID
	 * @throws WorkFlowException 流程异常
	 */
	public String getNextNode(String workItemId) throws WorkFlowException;

	/**
	 * 获取用户的任务列表
	 * @param userCode 用户编码(为空则查询所有用户)
	 * @param pageSize 每页显示数据量(pageSize，pageIndex，同时为0显示全部)
	 * @param pageIndex 当前页码，从1开始(pageSize，pageIndex，同时为0显示全部)，
	 * @param workflowCode 流程编码（为空则显示全部）
	 * @param state 类型（1待办，2已办）
	 * @param activityCodes 流程节点（为空则显示全部）
	 * @throws WorkFlowException 流程异常
	 */
	public String getWorkItem(String userCode, String pageSize, String pageIndex,
			String workflowCode, String state, String activityCodes,String instanceId)
			throws WorkFlowException;

	/**
	 * 添加部门
	 * @param OUCode 部门编码，要确保唯一
	 * @param OUName 部门名称
	 * @param ParentOUCode 部门上一级code
	 * @param SortKeyx 排序码，只能是数字，用来排列组织架构的顺序
	 * @throws WorkFlowException 流程异常
	 */
	public String AddDepartment(String OUCode, String OUName, String ParentOUCode,
			String SortKeyx)
			throws WorkFlowException;
	
	/**
	 * 更新部门
	 * @param OUCode 部门编码，要确保唯一
	 * @param OUName 部门名称
	 * @param ParentOUCode 部门上一级code
	 * @param SortKeyx 排序码，只能是数字，用来排列组织架构的顺序
	 * @throws WorkFlowException 流程异常
	 */
	public String UpdateDepartment(String OUCode, String OUName, String ParentOUCode,
			String SortKeyx)
			throws WorkFlowException;
	
	/**
	 * 删除部门
	 * @param OUCode 部门编码，要确保唯一
	 * @throws WorkFlowException 流程异常
	 */
	public String DeleteDepartment(String OUCode)
			throws WorkFlowException;
	
	/**
	 * 添加用户
	 * @param userCode 用户编码
	 * @param name 用户名称
	 * @param departCode 用户所在部门编码, 用户所在机构Code如果为空则添加到总公司下面
	 * @throws WorkFlowException 流程异常
	 */
	public String AddUser(String userCode, String name, String departCode)
			throws WorkFlowException;
	
	/**
	 * 更新用户
	 * @param userCode 用户编码
	 * @param name 用户名称
	 * @param departCode 用户所在部门编码, 用户所在机构Code如果为空则添加到总公司下面
	 * @throws WorkFlowException 流程异常
	 */
	public String UpdateUser(String userCode, String name, String departCode)
			throws WorkFlowException;
	
	/**
	 * 删除用户
	 * @param userCode 用户编码
	 * @throws WorkFlowException 流程异常
	 */
	public String DeleteUser(String userCode)
			throws WorkFlowException;
	
	/**
	 * 添加职务（业务角色）
	 * @param jobCode 职务编码，要确保唯一
	 * @param jobName 职务名称名称
	 * @throws WorkFlowException 流程异常
	 */
	public String AddJob(String jobCode, String jobName)
			throws WorkFlowException;
	
	/**
	 * 更新职务（业务角色）
	 * @param jobCode 职务编码，要确保唯一
	 * @param jobName 职务名称名称
	 * @throws WorkFlowException 流程异常
	 */
	public String UpdateJob(String jobCode, String jobName)
			throws WorkFlowException;
	
	/**
	 * 删除职务（业务角色）
	 * @param jobCode 职务编码，要确保唯一
	 * @throws WorkFlowException 流程异常
	 */
	public String DeleteJob(String jobCode)
			throws WorkFlowException;
	/**
	 * 添加岗位（组织角色）
	 * @param postCode 岗位编码，要确保唯一
	 * @param postName 岗位名称
	 * @param jobCode 对应职务code
	 * @param unitCode 所属组织的code如果为空则添加到总公司下面
	 * @throws WorkFlowException 流程异常
	 */
	public String AddPost(String postCode, String postName, String jobCode, String unitCode)
			throws WorkFlowException;
	
	/**
	 * 更新岗位（组织角色）
	 * @param postCode 岗位编码，要确保唯一
	 * @param postName 岗位名称
	 * @param jobCode 对应职务code
	 * @param unitCode 所属组织的code如果为空则添加到总公司下面
	 * @throws WorkFlowException 流程异常
	 */
	public String Updatepost(String postCode, String postName, String jobCode, String unitCode)
			throws WorkFlowException;
	
	/**
	 * 删除岗位（组织角色）
	 * @param postCode 岗位编码，要确保唯一
	 * @throws WorkFlowException 流程异常
	 */
	public String DeletePost(String postCode)
			throws WorkFlowException;
	/**
	 * 更新岗位下所有用户
	 * @param postCode 岗位编码
	 * @param userCodes 用户Code集合，多个用户用英文','隔开 
	 * @throws WorkFlowException 流程异常
	 */
	public String UpdatePostUsers(String postCode, String userCodes)
			throws WorkFlowException;

	
	/**
	 * 跟据流程获取当前活动实例（活动节点的信息）
	 * @param userCode 用户编码
	 * @param instanceId 工作实例ID 
	 * @throws WorkFlowException 流程异常
	 */
	public String GetActiveInstanceInfo(String userCode, String instanceId);
	
	/**
	 * 取消当前活动实例（活动节点的信息）
	 * @param userCode 用户编码
	 * @param instanceId 工作实例ID 
	 * @throws WorkFlowException 流程异常
	 */
	public String CancleInstance(String userCode, String instanceId);
	
	
	/**
	 * 获取流程模板，查看流程进行到哪一步
	 * @param instanceId 工作实例ID 
	 * @throws WorkFlowException 流程异常
	 */
	public String GetInstanceLog(String instanceId);
}
