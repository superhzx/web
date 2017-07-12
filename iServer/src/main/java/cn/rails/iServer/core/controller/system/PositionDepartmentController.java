package cn.rails.iServer.core.controller.system;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rails.iServer.core.entity.Department;
import cn.rails.iServer.core.entity.PositionDepartment;
import cn.rails.iServer.core.entity.Role;
import cn.rails.iServer.core.service.system.PositionDepartmentService;
import cn.rails.iServer.core.service.system.PositionService;
import cn.rails.iServer.core.service.system.UserService;
import cn.rails.iServer.utils.Common;
import cn.rails.iServer.utils.Constant;
import cn.rails.iServer.utils.UUIDHexGenerator;
import cn.rails.iServer.utils.Constant.Message;
import cn.rails.iServer.utils.Constant.StatusCode;
import cn.rails.iServer.utils.page.Order;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 
 * @author hzx
 * @date 2017年3月23日
 * @description 岗位部门控制层
 */
@RequestMapping(value ="/positiondepartment",produces="text/html;charset=UTF-8")
@Controller
public class PositionDepartmentController{
	
	//绑定时间类型特殊处理
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
	}
	@Autowired
	private UserService userService;
	@Autowired
	private PositionService positionService;
	@Autowired
	private PositionDepartmentService positionDepartmentService;
	@Autowired
	private DepartmentController departmentController;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpSession httpSession;
	/**
	 * 获取所有的人员部门信息（不加权限）
	 * @param departmentId 部门ID
	 * @author sunyh
	 */
	@RequestMapping(value="/listByPage")
	@ResponseBody
	public String listByPage(String conditionJson) throws UnsupportedEncodingException {
		//获取分页及排序相关信息
		int page = Integer.parseInt(request.getParameter("page"));// 当前页
		int rows = Integer.parseInt(request.getParameter("rows"));// 行数
		String orderString = request.getParameter("sidx");// 排序字段
		String orderBy = request.getParameter("sord");// 行数
		String departmentId = request.getParameter("pid");//部门Id
		String deptId = request.getParameter("deptId");
		Paramter par = new Paramter();
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
		//本部门及以下所有部门ID
		//String departmentIds=departmentController.getLowDeptIds(departmentId, "low");
		if(departmentId!=null && departmentId!="" && !departmentId.equals("null")){
			likeSql+=" and a.departmentId  = '"+deptId+"' ";
		}else{
			likeSql+=" and a.departmentId  !='"+Constant.rootDepart+"' ";
		}
		par.setAndSql(likeSql);
		par.setMap((Map<String, Object>) JSON.parseObject(conditionJson));
		PageTion data = positionDepartmentService.listByPage(page, rows, par);				
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置页面展示相关信息
		map.put("total", data.getTotal());// 总页数
		map.put("rows", data.getList());// 总行数
		map.put("page", data.getPageNo());// 第几页
		map.put("rowNum", data.getPageSize());// 第几行
		map.put("records", data.getNum());//总条数
		return JSON.toJSONString(map,SerializerFeature.DisableCircularReferenceDetect);
	}
	
	/**
	 * 删除部门信息
	 * @author hzx
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String id,String pid) {
		Map<String, Object> map = new HashMap<String, Object>();
		PositionDepartment t = new PositionDepartment();
		try {
			t.setId(id);
			positionDepartmentService.delete(t);
			positionService.deleteById(pid);
			map.put("msg", Message.DELETESUCCESS.getMsg());
			map.put("status_code", StatusCode.CORRECT.getStatusCode());
		} catch (Exception e) {
			map.put("msg", Message.DELETEFAIL.getMsg());
			map.put("status_code", StatusCode.ERROR.getStatusCode());
		}
		return JSON.toJSONString(map);
	}

	public void save(String id, String departmentId) {
		// TODO Auto-generated method stub
		PositionDepartment t = new PositionDepartment();
		String gid = UUIDHexGenerator.getUUID();
		t.setId(gid);
		t.setPositionId(id);
		t.setDepartmentId(departmentId);
		positionDepartmentService.save(t);
	}

	@RequestMapping(value = "/getPositionDepartmentById")
	@ResponseBody
	public String getPositionDepartmentById(String positionId) {
		String json = "";
		Map<String, Object> map = new HashMap<String, Object>();
		PositionDepartment t = positionDepartmentService.getDepartmentIdByPosition(positionId);
		if (t != null) {
			map.put("status_code", StatusCode.CORRECT.getStatusCode());
			map.put("msg", Message.SUCCESS.getMsg());
			map.put("projectInfo", t);
			json = JSON.toJSONString(map);
		} else {
			map.put("status_code", StatusCode.ERROR.getStatusCode());
			map.put("msg", Message.FAIL.getMsg());
			json = JSON.toJSONString(map);
		}
		return json;
	}
	
	
}
