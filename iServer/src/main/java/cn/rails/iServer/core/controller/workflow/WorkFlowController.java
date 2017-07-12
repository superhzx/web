package cn.rails.iServer.core.controller.workflow;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rails.iServer.core.service.workflow.WorkFlowService;
import cn.rails.iServer.utils.workflow.WorkFlowException;

/**
 * <p>Title      : 中国铁道科学研究院[]</p>
 * <p>Description: [部门管理]</p>
 * <p>Copyright  : Copyright (c) 2017</p>
 * <p>Company    : 铁科院电子所</p>
 * <p>Department : 信息中心</p>
 * @author       : sunyh
 * @version      : 1.0
 * @date         ：2017-02-16
 */

@Controller
@RequestMapping(value="/test", produces = "text/html;charset=UTF-8")
public class WorkFlowController {
	@Autowired
	private WorkFlowService workFlowService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpSession httpSession;
	//测试流程发起
	@RequestMapping("/testStart")
	@ResponseBody
	public String testStart() {
		String result="";
		try {
			result=workFlowService.StartWorkflowWithXmlData("OA_Authority", "administrator", false, "", "");
			System.out.println(result);
		} catch (WorkFlowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	@RequestMapping("/testSubmit")
	@ResponseBody
	public String testSubmit() {
		String result = "";
		try {
			//String workItemIds=workFlowService.getWorkItem("administrator", "10", "1", "OA_Authority", "1", "");
			result=workFlowService.submitWorkItem("administrator", "191c1d2a-44d1-46cd-8e93-227bf793c1d4", "Activity11", "", "", "同意");
			//System.out.println(workItemIds);
		} catch (WorkFlowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping("/StartWorkflowWithXmlData")
	@ResponseBody
	public String StartWorkflowWithXmlData() {
		String result="";
		String workflowCode = request.getParameter("workflowCode");
		String userCode =  request.getParameter("userCode");
		String isfinishStart = request.getParameter("finishStart");
		boolean finishStart = false;
		if(isfinishStart.equals("true")){
			finishStart =true;
		}
		String xmlData = "";
		String instanceName="";
		try {
			result=workFlowService.StartWorkflowWithXmlData(workflowCode, userCode, finishStart, xmlData, instanceName);
		} catch (WorkFlowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping("/adjustWorkflow")
	@ResponseBody
	public String adjustWorkflow() {
		String result = "";
		String workItemId= request.getParameter("workItemId"); 
		String userCode= request.getParameter("userCode");
		String commentText= request.getParameter("commentText"); 
		String participant= request.getParameter("participant");
		try {
			result=workFlowService.adjustWorkflow(workItemId,userCode,commentText,participant);
		} catch (WorkFlowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping("/finishInstance")
	@ResponseBody
	public String finishInstance(){
		String result = "";
		String userCode= request.getParameter("userCode");
		String instanceId= request.getParameter("instanceId"); 
		try {
			result=workFlowService.finishInstance(userCode,instanceId);
		} catch (WorkFlowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping("/submitWorkItem")
	@ResponseBody
	public String submitWorkItem(){
		String result = "";
		String userCode= request.getParameter("userCode");
		String workItemIds= request.getParameter("workItemIds"); 
		String activityCode= request.getParameter("activityCode"); 
		String participant= request.getParameter("participant"); 
		String signature= request.getParameter("signature"); 
		String commentText= request.getParameter("commentText"); 
		try {
			result=workFlowService.submitWorkItem(userCode, workItemIds, activityCode, participant, signature, commentText);
		} catch (WorkFlowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	@RequestMapping("/returnItem")
	@ResponseBody
	public String returnItem(){
		String result = "";
		String userCode= request.getParameter("userCode");
		String workItemId= request.getParameter("workItemId"); 
		String commentText= request.getParameter("commentText"); 
		String type= request.getParameter("type"); 
		String activityCode= request.getParameter("activityCode"); 
		String signature= request.getParameter("signature"); 
		try {
			result=workFlowService.returnItem(userCode, workItemId, commentText, type, activityCode, signature);
		} catch (WorkFlowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	@RequestMapping("/getNextNode")
	@ResponseBody
	public String getNextNode(){
		String result = "";
		String workItemId= request.getParameter("workItemId"); 
		try {
			result=workFlowService.getNextNode("191c1d2a-44d1-46cd-8e93-227bf793c1d4");
		} catch (WorkFlowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	@RequestMapping("/getWorkItem")
	@ResponseBody
	public String getWorkItem(){
		String result = "";
		String userCode= request.getParameter("userCode"); 
		String pageSize= request.getParameter("pageSize"); 
		String pageIndex= request.getParameter("pageIndex"); 
		String workflowCode= request.getParameter("workflowCode"); 
		String state= request.getParameter("state"); 
		String activityCodes= request.getParameter("activityCodes"); 
		try {
			result=workFlowService.getWorkItem("administrator","10","1","OA_Authority","1","","0076470c-93e8-4236-89b5-4885d873b5a9");
		} catch (WorkFlowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
