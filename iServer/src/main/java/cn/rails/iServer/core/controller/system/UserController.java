package cn.rails.iServer.core.controller.system;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
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

import cn.rails.iServer.core.entity.Department;
import cn.rails.iServer.core.entity.PositionDepartment;
import cn.rails.iServer.core.entity.User;
import cn.rails.iServer.core.entity.UserPosition;
import cn.rails.iServer.core.service.system.DepartmentService;
import cn.rails.iServer.core.service.system.PositionDepartmentService;
import cn.rails.iServer.core.service.system.RoleService;
import cn.rails.iServer.core.service.system.UserPositionService;
import cn.rails.iServer.core.service.system.UserRoleService;
import cn.rails.iServer.core.service.system.UserService;
import cn.rails.iServer.core.service.workflow.WorkFlowService;
import cn.rails.iServer.utils.Common;
import cn.rails.iServer.utils.MD5;
import cn.rails.iServer.utils.UUIDHexGenerator;
import cn.rails.iServer.utils.Constant.Message;
import cn.rails.iServer.utils.Constant.StatusCode;
import cn.rails.iServer.utils.page.Order;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;
import cn.rails.iServer.utils.workflow.WorkFlowException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
/**
 * @author       : wangqi
 * @date         ：2017-03-22
 * @description  ：用户列表
 */
@RequestMapping(value ="/user",produces="text/html;charset=UTF-8")
@Controller
public class UserController{
	
	//绑定时间类型特殊处理
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
	}
	
	@Autowired
	private UserService service;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private UserPositionService userPositionService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpSession httpSession;
	@Autowired
	private DepartmentController departmentController;
	@Autowired
	private WorkFlowService workFlowService;
	@Autowired
	private PositionDepartmentService positionDepartmentService;
	
	
	/**
	 * 获取所有的人员信息（不加权限）
	 * @param 
	 * @author wangqi
	 */
	@RequestMapping(value="/listByPage")
	@ResponseBody
	public String listByPage(String conditionJson) throws UnsupportedEncodingException {
		//获取分页及排序相关信息
		int page = Integer.parseInt(request.getParameter("page"));// 当前页
		int rows = Integer.parseInt(request.getParameter("rows"));// 行数
		String orderString = request.getParameter("sidx");// 排序字段
		String orderBy = request.getParameter("sord");// 行数
		String name = request.getParameter("name");
		String code = request.getParameter("code");
		String params = request.getParameter("params");//参数
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
		String likeSql = "";
		if(name!=null&&!name.equals("null")&&name!=""){
			name= URLDecoder.decode(name,"UTF-8");
			likeSql+=" and name like '%"+name+"%' ";
		}
		if(code!=null&&!code.equals("null")&&code!=""){
			code= URLDecoder.decode(code,"UTF-8");
			likeSql+=" and code like '%"+code+"%' ";
		}
		if(params!=null && !params.equals("()") && !params.equals("null") &&  !params.equals("")){
			likeSql+=" and id  not in  "+params;
		}
		par.setAndSql(likeSql);
		par.setMap((Map<String, Object>) JSON.parseObject(conditionJson));
		PageTion data = service.listByPage(page, rows, par);				
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
	 * 根据人员ID获取所有人员相关信息（部门信息、角色信息、权限信息）
	 * @param userId 人员信息ID
	 */
	@RequestMapping("/queryUserInfoByUserId")
	@ResponseBody
	public String queryUserInfoByUserId(String userId) {
		String json = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			User u = service.queryById(userId);
			if (u != null) {
				map.put("status_code", StatusCode.CORRECT.getStatusCode());
				map.put("msg", Message.SUCCESS.getMsg());
				map.put("user", u);
				json = JSON.toJSONString(map);
			} else {
				map.put("status_code", StatusCode.ERROR.getStatusCode());
				map.put("msg", Message.FAIL.getMsg());
				json = JSON.toJSONString(map);
			}
			return json;
		} catch (Exception e) {
			return json;
		}
		
	}
	
	/**
	 * 用户信息保存
	 * @param users 人员信息
	 */
	@RequestMapping("/save")
	@ResponseBody
	public String save(User user) {
		Map<String, Object> map = new HashMap<String, Object>();
		try { 
			if(user.getPwd().length()!=32){
				user.setPwd(MD5.byteArrayToHexString(MD5.md5(user.getPwd().getBytes())));
			}
			if(user.getId().length()<32){
				user.setId(UUIDHexGenerator.getUUID());
				service.save(user);
				map = Common.getTipMsg("add", 1);
				
				//同步到BPM
/*				user.setSynFlag("2");
				String result = workFlowService.AddUser(user.getCode(), user.getName(), "");
				if(JSON.parseObject(result).getString("IsSuccess").equals("true")){
					user.setSynFlag("1");//同步成功
				}
				service.update(user);*/
			}else {
				service.update(user);
				map = Common.getTipMsg("edit", 1);
				
				//同步到BPM
/*				if(user.getSynFlag() == null|| user.getSynFlag() == ""|| user.getSynFlag() == "2"){//未同步、新增失败
					String result = workFlowService.AddUser(user.getCode(), user.getName(), "");
					if(JSON.parseObject(result).getString("IsSuccess").equals("true")){
						user.setSynFlag("1");//同步成功
					}
				}else{
					user.setSynFlag("3");
					String result = workFlowService.UpdateUser(user.getCode(), user.getName(), "");
					if(JSON.parseObject(result).getString("IsSuccess").equals("true")){
						user.setSynFlag("1");//同步成功
					}
				}
				service.update(user);*/
			}
		} catch (Exception e) {
			return JSON.toJSONString(Common.getTipMsg("ex", 2));
		}
		return JSON.toJSONString(map);
	}
	
	/**
	 * erp账户信息待存
	 * @param users 人员信息
	 */
	@RequestMapping("/saveErpUser")
	@ResponseBody
	public String saveErpUser(User user,String operateType) {
		Map<String, Object> map = new HashMap<String, Object>();
		String code = user.getCode();
		if(code.length()<32&&!"".equals(user.getCode())){
			String u = service.queryIdByCode(user.getCode());
			if(u!=null){
				user.setId(u);
			}
		}
		
		
		try { 
			if(user.getId().equals("")||user.getId()==null){
				String id = UUIDHexGenerator.getUUID();
				user.setId(id);
				if(user.getCode().length()<32&&!"".equals(user.getCode())){
					user.setCode(code);
				}else{
					user.setCode(id);
				}
				service.save(user);
				map.put("userId", id);
				map.put("msg", Message.ADDSUCCESS.getMsg());
				map.put("status_code", StatusCode.CORRECT.getStatusCode());
			}else {
				if(user.getCode().length()<32){
					//只更新手机号
					if("3".equals(operateType)||"1".equals(operateType)){
						service.updateUser(user.getId(),user.getMobile());
					}
				}else{
					service.update(user);
				}
				map.put("msg", Message.MODIFYSUCCESS.getMsg());
				map.put("status_code", StatusCode.CORRECT.getStatusCode());
				map.put("userId", user.getId());
			}
		} catch (Exception e) {
			map.put("msg", Message.ADDFAIL.getMsg());
			map.put("status_code", StatusCode.ERROR.getStatusCode());
		}
		return JSON.toJSONString(map);
		
	}
	
	
	/**
	 * 用户信息删除
	 * @param users 人员信息
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String deleteby(User user) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//删除人员表中相关信息
			service.delete(user);
			userPositionService.deleteUserPositionByUserId(user.getId());
			userRoleService.deleteByUserId(user.getId());
			map = Common.getTipMsg("delete", 1);
			
			//同步到BPM
			/*String result = workFlowService.DeleteUser(user.getCode());*/

		} catch (Exception e) {
			map = Common.getTipMsg("delete", 0);
		}
		return JSON.toJSONString(map);
	}
	
	/**
	 * 用户信息删除
	 * @param users 人员信息
	 */
	@RequestMapping("/deleteByCode")
	@ResponseBody
	public String deleteByCode(String userCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//删除人员表中相关信息
			service.deleteByCode(userCode);
			map = Common.getTipMsg("delete", 1);
		} catch (Exception e) {
			map = Common.getTipMsg("delete", 0);
		}
		return JSON.toJSONString(map);
	}
	
	/**
	 * 获取下拉框数据
	 * @param users 人员信息
	 */
	@RequestMapping("/dateForSelect")
	@ResponseBody
	public String dateForSelect() {
		String userCode = request.getParameter("userCode");
		try {
			List<User> pList=null;
			String returnValue="<option value=''>&nbsp;</option>";
			User user = service.queryUserByCode(userCode);
			UserPosition up = userPositionService.getMainPositon(user.getId());
			
			PositionDepartment pd =  positionDepartmentService.getDepartmentIdByPosition(up.getPositionId());
			Department depart = departmentService.queryById(pd.getDepartmentId());
			Department departMent = departmentService.queryByCode(depart.getCode());
			Department dep = departmentService.queryById(departMent.getId());
			String codes = departmentController.getLowDeptIds(dep.getCode());
			
			//部门CODE条件
			//String strCode = departmentService.getDepartCode(depart.getCode());
			
			pList = service.queryByMagnitude(codes);
			for(int i=0;i<pList.size();i++){
				returnValue+="<option value='"+pList.get(i).getId()+"'>"+pList.get(i).getName()+"</option> ";
			}
			return returnValue;
		} catch (Exception e) {
			e.printStackTrace();
			return "<option value=''>&nbsp;</option>";
		}
	}
	
}
