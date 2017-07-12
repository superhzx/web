package cn.rails.iServer.core.controller.workflow;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rails.iServer.core.entity.BusinessOrgRole;
import cn.rails.iServer.core.entity.BusinessOrgRoleUser;
import cn.rails.iServer.core.entity.BusinessRoleOrgRole;
import cn.rails.iServer.core.entity.Department;
import cn.rails.iServer.core.entity.Role;
import cn.rails.iServer.core.service.system.BusinessRoleService;
import cn.rails.iServer.core.service.system.OrgRoleService;
import cn.rails.iServer.core.service.system.OrgRoleUserService;
import cn.rails.iServer.core.service.system.RoleOrgRoleService;
import cn.rails.iServer.core.service.workflow.WorkFlowService;
import cn.rails.iServer.utils.Common;
import cn.rails.iServer.utils.UUIDHexGenerator;
import cn.rails.iServer.utils.Constant.Message;
import cn.rails.iServer.utils.Constant.StatusCode;
import cn.rails.iServer.utils.page.Order;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author hzx
 * @date 2017年4月10日
 * @description 审核信息控制层
 */
@RequestMapping(value ="/orgRole",produces="text/html;charset=UTF-8")
@Controller
public class OrgRoleController{
	
	//绑定时间类型特殊处理
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
	}
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private OrgRoleService orgRoleService;
	@Autowired
	private RoleOrgRoleService roleOrgRoleService;
	@Autowired
	private OrgRoleUserService orgRoleUserService;
	@Autowired
	private WorkFlowService workFlowService;
	@Autowired
	private BusinessRoleService businessRoleService;
	
	@RequestMapping("/list")
	@ResponseBody
	
	public String list(String conditionJson) throws UnsupportedEncodingException {
		// 获取分页及排序相关信息
		int page = Integer.parseInt(request.getParameter("page"));// 当前页
		int rows = Integer.parseInt(request.getParameter("rows"));// 行数
		String orderString = request.getParameter("sidx");// 排序字段
		String orderBy = request.getParameter("sord");// 行数
		String params = request.getParameter("params");
		String json = "";
		Map<String, Object> map = new HashMap<String, Object>();
		Paramter param = new Paramter();
		// 设置排序
		Order order = new Order();
		order.setClumn(orderString);
		if (orderBy.equals("desc")) {
			order.setFalg(1);
		} else {
			order.setFalg(2);
		}
		param.addOrder(order);
		
		//查询条件
		String likeSql=" ";
		String name= request.getParameter("name");
		if(name!=null){
			name= URLDecoder.decode(name,"UTF-8");
			likeSql+=" and orgRoleName like '%"+name+"%' ";
		}
		if(params!=null && !params.equals("()") && !params.equals("null") &&  !params.equals("")){
			likeSql+=" and id  not in  "+params;
		}
		param.setAndSql(likeSql);
		
		// 查询数据
		PageTion data = orgRoleService.listByPage(page, rows, param);
		// 设置页面展示相关信息
		map.put("total", data.getTotal());// 总页数
		map.put("rows", data.getList());// 总行数
		map.put("page", data.getPageNo());// 第几页
		map.put("rowNum", data.getPageSize());// 第几页
		map.put("records", data.getNum());//总条数
		json = JSON.toJSONString(map);
		return json;
	}
	
	
	/**
	 * 删除组织角色
	 * @param role 角色信息
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(BusinessOrgRole t) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			orgRoleService.delete(t);
			orgRoleUserService.deleteByOrgRoleId(t.getId());
			map.put("msg", Message.DELETESUCCESS.getMsg());
			map.put("status_code", StatusCode.CORRECT.getStatusCode());
			
			//同步到BPM
			/*String result = workFlowService.DeletePost(t.getCode());*/
		} catch (Exception e) {
			map.put("msg", Message.DELETEFAIL.getMsg());
			map.put("status_code", StatusCode.ERROR.getStatusCode());
		}
		return JSON.toJSONString(map);
	}
	
	/**
	 * 保存组织角色信息
	 * @param t
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/save")
	@ResponseBody
	public String save(BusinessOrgRole t,String businessRole) throws ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String token=request.getHeader("token");
			t.setBusinessRoleCode(businessRoleService.queryById(t.getBusinessRoleId()).getCode());
			if(t.getId().length()!=32){
				t.setId(UUIDHexGenerator.getUUID());
				t.setCreateDate(new Date());//添加时间  20170503 by djm
				orgRoleService.save(t);
				map.put("status_code", StatusCode.CORRECT.getStatusCode());
				map.put("msg", Message.SUCCESS.getMsg());
				map.put("projectInfo", t);
				
				//同步到BPM
			/*	t.setSynFlag("2");//新增失败
				String result = workFlowService.AddPost(t.getCode(), t.getOrgRoleName(), t.getBusinessRoleCode(), t.getDepartmentCode());
				if(JSON.parseObject(result).getString("IsSuccess").equals("true")){
					t.setSynFlag("1");//同步成功
				}
				orgRoleService.update(t);*/
			}else {
				orgRoleService.update(t);
				map.put("status_code", StatusCode.CORRECT.getStatusCode());
				map.put("msg", Message.SUCCESS.getMsg());
				map.put("projectInfo", t);
				
				//同步到BPM
				/*if(t.getSynFlag() == null|| t.getSynFlag() == ""|| t.getSynFlag() == "2"){//未同步、新增失败
					String result = workFlowService.AddPost(t.getCode(), t.getOrgRoleName(), t.getBusinessRoleCode(), t.getDepartmentCode());
					if(JSON.parseObject(result).getString("IsSuccess").equals("true")){
						t.setSynFlag("1");//同步成功
					}
				}else{
					t.setSynFlag("3");//更新失败
					String result = workFlowService.Updatepost(t.getCode(), t.getOrgRoleName(), t.getBusinessRoleCode(), t.getDepartmentCode());
					if(JSON.parseObject(result).getString("IsSuccess").equals("true")){
						t.setSynFlag("1");//同步成功
					}
				}
				orgRoleService.update(t);*/
			}
		} catch (Exception e) {
			return JSON.toJSONString(Common.getTipMsg("ex", 2));
		}
		return JSON.toJSONString(map);
	}
	
	/**
	 * 根据角色ID获取所有角色相关信息
	 * @param roleId 角色信息ID
	 */
	@RequestMapping(value = "queryOrgRoleById")
	@ResponseBody
	public String queryRoleInfoByRoleId() {
		String json = "";
		Map<String, Object> map = new HashMap<String, Object>();
		String id = request.getParameter("id");
		try {
			BusinessOrgRole r = orgRoleService.queryById(id);
			if (r != null) {
				map.put("status_code", StatusCode.CORRECT.getStatusCode());
				map.put("msg", Message.SUCCESS.getMsg());
				map.put("businessOrgRole", r);
				json = JSON.toJSONString(map);
			} else {
				map.put("status_code", StatusCode.ERROR.getStatusCode());
				map.put("msg", Message.FAIL.getMsg());
				json = JSON.toJSONString(map);
			}
			return json;
		} catch (Exception e) {
			// TODO: handle exception
			return json;
		}
	}
	
}