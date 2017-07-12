package cn.rails.iServer.core.controller.workflow;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rails.iServer.core.entity.BusinessConfig;
import cn.rails.iServer.core.entity.Gtasks;
import cn.rails.iServer.core.service.system.BusinessConfigService;
import cn.rails.iServer.core.service.workflow.WorkFlowService;
import cn.rails.iServer.utils.workflow.WorkFlowException;

import com.alibaba.fastjson.JSON;
/**
 * 
 * @author hzx
 * @date 2017年4月19日
 * @description 待办任务控制层
 */
@RequestMapping(value ="/gtasks",produces="text/html;charset=UTF-8")
@Controller
public class GtasksController{
	
	//绑定时间类型特殊处理
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
	}
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpSession httpSession;
	@Autowired
	private WorkFlowService workFlowService;
	@Autowired
	private BusinessConfigService businessConfigService;
	
	@RequestMapping("/list")
	@ResponseBody
	public String list(String conditionJson) throws UnsupportedEncodingException {
		// 获取分页及排序相关信息
		String page = request.getParameter("page");// 当前页
		String rows = request.getParameter("rows");// 行数
		
		Map<String, Object> map = new HashMap<String, Object>();
		String loginUserCode = request.getParameter("loginUserCode");
		//loginUserCode="administrator";
		String result=""; 
		try {
			result = workFlowService.getWorkItem(loginUserCode, rows, page, "", "1", "", "");
		} catch (WorkFlowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s1 = JSON.parseObject(result).getString("Result");
		String total = JSON.parseObject(s1).getString("total");
		String totalPage = JSON.parseObject(s1).getString("pageTotal");
		List<Gtasks> glist = new ArrayList<Gtasks>();
		Gtasks gtasks = null;
		List<Object> list = JSON.parseObject(s1).getJSONArray("userTodoList");
		
		for (int i = 0; i < list.size(); i++) {
			gtasks = new Gtasks();
			String re = list.get(i).toString();
			String instance = JSON.parseObject(re).getString("InstanceId");
			String instanceName = JSON.parseObject(re).getString("InstanceName");
			String workflowCode = JSON.parseObject(re).getString("WorkflowCode");
			BusinessConfig b = businessConfigService.queryByCode(workflowCode);
			if(b!=null){
				gtasks.setReqUrl(b.getUrl());
			}
			
			gtasks.setInstanceName(instanceName);
			gtasks.setInstanceId(instance);
			gtasks.setWorkflowCode(workflowCode);
			glist.add(gtasks);
		}
		
		map.put("total", totalPage);// 总页数
		map.put("rows", glist);// 总行数
		map.put("page", page);// 第几页
		map.put("rowNum", rows);// 共几页
		map.put("records", total);//总条数
		return JSON.toJSONString(map);
	}
	
	@RequestMapping("/GetInstanceLog")
	@ResponseBody
	public String GetInstanceLog(String conditionJson)  {
		//Map<String, Object> map = new HashMap<String, Object>();
		String InstanceId = request.getParameter("InstanceId");
		String result=""; //返回的全部
		String resultMes=""; //返回的Result
		try {
			result = workFlowService.GetInstanceLog(InstanceId);
			resultMes = JSON.parseObject(result).getString("Result");	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultMes;
	}
}
