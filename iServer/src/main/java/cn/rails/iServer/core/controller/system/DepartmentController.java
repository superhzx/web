
package cn.rails.iServer.core.controller.system;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rails.iServer.core.entity.Department;
import cn.rails.iServer.core.service.system.DepartmentService;
import cn.rails.iServer.core.service.system.UserService;
import cn.rails.iServer.core.service.workflow.WorkFlowService;
import cn.rails.iServer.utils.Common;
import cn.rails.iServer.utils.UUIDHexGenerator;
import cn.rails.iServer.utils.Constant.Message;
import cn.rails.iServer.utils.Constant.StatusCode;
import cn.rails.iServer.utils.page.Order;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;
import cn.rails.iServer.utils.workflow.WorkFlowException;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author hzx
 * @date 2017年3月20日
 * @description 部门控制层
 */
@RequestMapping(value ="/departments",produces="text/html;charset=UTF-8")
@Controller
public class DepartmentController {
	@Autowired
	private DepartmentService service;
	@Autowired
	private UserService userService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpSession httpSession;
	@Autowired
	private WorkFlowService workFlowService;

	/**
	 * 获取部门列表信息
	 * @author hzx
	 */
	@RequestMapping(value="/listByPage")
	@ResponseBody
	public String listByPage(String conditionJson) throws UnsupportedEncodingException {
		//获取分页及排序相关信息
		int page = Integer.parseInt(request.getParameter("page"));// 当前页
		int rows = Integer.parseInt(request.getParameter("rows"));// 行数
		String orderString = request.getParameter("sidx");// 排序字段
		String orderBy = request.getParameter("sord");// 行数
		Paramter par = new Paramter();
		par.setMap((Map<String, Object>) JSON.parseObject(conditionJson));
		
		// 设置排序
		Order order = new Order();
		order.setClumn(orderString);
		if (orderBy.equals("desc")) {
			order.setFalg(1);
		} else {
			order.setFalg(2);
		}
		par.addOrder(order);
		//查询条件
		String likeSql=" ";
		
		String parentId=request.getParameter("pid");// 父节点
		if(parentId!=null&&!parentId.equals("null")){
			parentId= URLDecoder.decode(parentId,"UTF-8");
			par.addCondition("parentCode", parentId);
			//likeSql+=(" and pid = '"+pid+"'");
		}
		
		String name= request.getParameter("name");
		if(name!=null&&!name.equals("null")&&name!=""){
			name= URLDecoder.decode(name,"UTF-8");
			likeSql+=" and name like '%"+name+"%' ";
		}
		
		par.setAndSql(likeSql);
		
		PageTion data = service.listByPage(page, rows, par);
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 设置页面展示相关信息
		map.put("total", data.getTotal());// 总页数
		map.put("rows", data.getList());// 总行数
		map.put("page", data.getPageNo());// 第几页
		map.put("rowNum", data.getPageSize());// 第几行
		map.put("records", data.getNum());//总条数
		return JSON.toJSONString(map);
	}

	/**
	 * 保存部门信息
	 * @param t
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/save")
	@ResponseBody
	public String save(Department t) throws ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String token=request.getHeader("token");
			if(t.getId().length()!=32){
				t.setId(UUIDHexGenerator.getUUID());
				service.save(t);
				map.put("status_code", StatusCode.CORRECT.getStatusCode());
				map.put("msg", Message.SUCCESS.getMsg());
				map.put("projectInfo", t);
				//同步到BPM
/*				t.setSynFlag("2");//新增失败
				String result = workFlowService.AddDepartment(t.getCode(), t.getName(), t.getParentCode(), "1");
				if(JSON.parseObject(result).getString("IsSuccess").equals("true")){
					t.setSynFlag("1");//同步成功
				}
				service.update(t);*/
				
			}else {
				service.update(t);
				map.put("status_code", StatusCode.CORRECT.getStatusCode());
				map.put("msg", Message.SUCCESS.getMsg());
				map.put("projectInfo", t);
				
				//同步到BPM
/*				if(t.getSynFlag() == null|| t.getSynFlag() == ""|| t.getSynFlag() == "2"){//未同步、新增失败
					String result = workFlowService.AddDepartment(t.getCode(), t.getName(), t.getParentCode(), "1");
					if(JSON.parseObject(result).getString("IsSuccess").equals("true")){
						t.setSynFlag("1");//同步成功
					}
				}else{
					t.setSynFlag("3");//更新失败
					String result = workFlowService.UpdateDepartment(t.getCode(), t.getName(), t.getParentCode(), "1");
					if(JSON.parseObject(result).getString("IsSuccess").equals("true")){
						t.setSynFlag("1");//同步成功
					}
				}
				service.update(t);*/
			}
		} catch (Exception e) {
			return JSON.toJSONString(Common.getTipMsg("ex", 2));
		}
		return JSON.toJSONString(map);
	}

	/**
	 * 删除部门信息
	 * @author hzx
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Department t = new Department();
		try {
			t.setId(id);
			service.delete(t);
			map.put("msg", Message.DELETESUCCESS.getMsg());
			map.put("status_code", StatusCode.CORRECT.getStatusCode());
			
			//同步到BPM
			/*String result = workFlowService.DeleteDepartment(t.getCode());*/
			
		} catch (Exception e) {
			map.put("msg", Message.DELETEFAIL.getMsg());
			map.put("status_code", StatusCode.ERROR.getStatusCode());
		}
		return JSON.toJSONString(map);
	}
	
	/**
	 * 获取部门树节点信息
	 * @author hzx
	 */
	@RequestMapping("/getTreeData")
	@ResponseBody
	public String getTreeData() {
		List<Department> pList = null;
		Paramter par = new Paramter();
		Order order = new Order();
		order.setClumn("parentCode");
		par.addOrder(order);
		
		//查询条件
		String firstCode = request.getParameter("firstCode");
		if(firstCode!=null && firstCode!=""){
			if(firstCode.equals("10000001")){//根节点时只显示一级
				par.setAndSql(" and (code = '"+firstCode+"' or parentCode = '"+firstCode+"')");
			}else{
				String codes = getLowDeptIds(firstCode);
				par.setAndSql(" and code in "+codes+"");
			}
		}
		
//		String returnValue = "[";
		List<Map<String, String>> treeData = new ArrayList<>();
		pList = service.queryByCondition(par);
		for (int i = 0; i < pList.size(); i++) {
			Map<String, String> node = new HashMap<>();
			node.put("id", pList.get(i).getCode());
			node.put("name", pList.get(i).getName());
			node.put("deptId", pList.get(i).getId());
			node.put("pId", pList.get(i).getParentCode());
			treeData.add(node);
		}
//		returnValue = returnValue.substring(0, returnValue.length() - 1);
//		returnValue += "]";
		return JSON.toJSONString(treeData);
	}
	
	
	
	/**
	 *获取下拉框数据
	 * @author hzx
	 */
	@RequestMapping(value="/getSelectData.do")
	@ResponseBody
	public String getSelectData() {
		try {
			List<Department> pList=null;
			String returnValue="<option value=''>&nbsp;</option>";
			pList=service.queryByCondition("","");
			for(int i=0;i<pList.size();i++){
				returnValue+="<option value='"+pList.get(i).getCode()+"'>"+pList.get(i).getName()+"</option> ";
			}
			return returnValue;
		} catch (Exception e) {
			return "<option value=''>&nbsp;</option>";
		}
	}
	
	/**
	 * 获取下拉框数据
	 * 对接VUE
	 */
	@RequestMapping(value="/getSelectInfo")
	@ResponseBody
	public String getSelectInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Department> pList=null;
			pList=service.queryByCondition("","");
			map.put("plist", pList);
			map.put("status", "1");
			map.put("msg","操作成功");
		} catch (Exception e) {
			map.put("status", "0");
			map.put("msg","操作失败");
		}
		return JSON.toJSONString(map);
	}
	
	/**
	 * 获取部门树节点信息
	 * 对接vue
	 */
	@RequestMapping("/getTreeInfo")
	@ResponseBody
	public String getTreeInfo() {
		List<Department> pList = null;
		Paramter par = new Paramter();
		Order order = new Order();
		order.setClumn("parentCode");
		par.addOrder(order);
		
		//查询条件
		String firstCode = request.getParameter("firstCode");
		if(firstCode!=null && firstCode!=""){
			if(firstCode.equals("10000001")){//根节点时只显示一级
				par.setAndSql(" and (code = '"+firstCode+"' or parentCode = '"+firstCode+"')");
			}else{
				String codes = getLowDeptIds(firstCode);
				par.setAndSql(" and code in "+codes+"");
			}
		}
		
		String returnValue = " var zNodes =[";
		pList = service.queryByCondition(par);
		for (int i = 0; i < pList.size(); i++) {
			returnValue += "{ id:'"+pList.get(i).getCode()+"', pId:'"+pList.get(i).getParentCode()+
				"', name:'"+pList.get(i).getName()+"', deptId:'"+pList.get(i).getId()+"'},";
		}
		returnValue = returnValue.substring(0, returnValue.length() - 1);
		returnValue += "]; ";
		return returnValue;
	}
	
	/**
	 *根据部门ID获取部门信息
	 * @author hzx
	 */
	@RequestMapping("/{deptId}/edit")
	@ResponseBody
	public String queryById(@PathVariable("deptId")String deptId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Department department=service.queryById(deptId);
			map = Common.getTipMsg("edit", 1);
			map.put("department", department);
		}catch (Exception e) {
			map = Common.getTipMsg("edit", 0);
			return JSON.toJSONString(map);
		}
		return JSON.toJSONString(map);
	}
	
	/*
	 * 部门及以下部门或者部门及以上
	 * */
	public List<Object> getdeptIds(String departmentId,String orders) {
		try {
			List<Object> deptIdString=service.mysqlgetdeptIds(departmentId,orders);
			return deptIdString;
		} catch (Exception e) {
			return null;
		}
		
	}
	/*
	 * 部门及以下部门
	 * */
	public String getLowDeptIds(String departmentId) {
		try {
			String minDepartmentIds="(";
			List<Object> minDepartmentId=getdeptIds(departmentId, "low");
			String deptidsString = (String) minDepartmentId.get(0);
			String[] arraydeptStrings=deptidsString.split(",");
			for(int d=1;d<arraydeptStrings.length;d++){
				minDepartmentIds+="'"+arraydeptStrings[d]+"',";
			}
			minDepartmentIds =  minDepartmentIds.substring(0, minDepartmentIds.lastIndexOf(","));
			minDepartmentIds+=")";
			return minDepartmentIds;
		} catch (Exception e) {
			return null;
		}
		
	}
	
	
	//根据当前登录人的部门，去过滤本所的所有部门
	public String getOwnDeptIds(String departmentId){
		List<Object> maxDepartmentId=getdeptIds(departmentId, "up");
		String maxDepartmentIds=(String) (maxDepartmentId.get(maxDepartmentId.size()-1));
		List<Object> minDepartmentId=getdeptIds(maxDepartmentIds, "low");
		String minDepartmentIds="(";
		for(int i=0;i<minDepartmentId.size();i++){
			minDepartmentIds+="'"+minDepartmentId.get(i)+"',";
		}
		minDepartmentIds =  minDepartmentIds.substring(0, minDepartmentIds.lastIndexOf(","));
		minDepartmentIds+=")";
		return minDepartmentIds;
	}
}
