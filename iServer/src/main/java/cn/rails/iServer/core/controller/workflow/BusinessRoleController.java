package cn.rails.iServer.core.controller.workflow;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rails.iServer.core.entity.BusinessRole;
import cn.rails.iServer.core.entity.BusinessRoleOrgRole;
import cn.rails.iServer.core.entity.Department;
import cn.rails.iServer.core.service.system.BusinessRoleService;
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
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author hzx
 * @date 2017年4月10日
 * @description 审核信息控制层
 */
@RequestMapping(value ="/businessRole",produces="text/html;charset=UTF-8")
@Controller
public class BusinessRoleController{
	
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
	private BusinessRoleService businessRoleService;
	@Autowired
	private RoleOrgRoleService roleOrgRoleService;
	@Autowired
	private WorkFlowService workFlowService;
	
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
			likeSql+=" and business_role_name like '%"+name+"%' ";
		}
		if(params!=null && !params.equals("()") && !params.equals("null") &&  !params.equals("")){
			likeSql+=" and id  not in  "+params;
		}
		param.setAndSql(likeSql);

		
		// 查询数据
		PageTion data = businessRoleService.listByPage(page, rows, param);
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
	 * 保存角色
	 * @param Role 角色信息
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(BusinessRole t){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(t.getId().isEmpty())
			{
				try {
					t.setId(UUIDHexGenerator.getUUID());
					t.setCreateDate(new Date());
					businessRoleService.save(t);
					map.put("msg", Message.ADDSUCCESS.getMsg());
					map.put("status_code", StatusCode.CORRECT.getStatusCode());
					
					//同步到BPM
					/*t.setSynFlag("2");//新增失败
					String result = workFlowService.AddJob(t.getCode(), t.getBusinessRoleName());
					if(JSON.parseObject(result).getString("IsSuccess").equals("true")){
						t.setSynFlag("1");//同步成功
					}
					businessRoleService.update(t);*/
				} catch (Exception e) {
					map.put("msg", Message.ADDFAIL.getMsg());
					map.put("status_code", StatusCode.ERROR.getStatusCode());
				}
			}
			else
			{
				try {
					businessRoleService.update(t);
					map.put("msg", Message.MODIFYSUCCESS.getMsg());
					map.put("status_code", StatusCode.CORRECT.getStatusCode());
					
					//同步到BPM
					/*if(t.getSynFlag() == null|| t.getSynFlag() == ""|| t.getSynFlag() == "2"){//未同步、新增失败
						String result = workFlowService.AddJob(t.getCode(), t.getBusinessRoleName());
						if(JSON.parseObject(result).getString("IsSuccess").equals("true")){
							t.setSynFlag("1");//同步成功
						}
					}else{
						t.setSynFlag("3");//更新失败
						String result = workFlowService.UpdateJob(t.getCode(), t.getBusinessRoleName());
						if(JSON.parseObject(result).getString("IsSuccess").equals("true")){
							t.setSynFlag("1");//同步成功
						}
					}
					businessRoleService.update(t);*/
		
				} catch (Exception e) {
					map.put("msg", Message.MODIFYFAIL.getMsg());
					map.put("status_code", StatusCode.ERROR.getStatusCode());
				}
			}
		}
		catch(Exception e){
			map.put("status_code", StatusCode.ERROR.getStatusCode());
			map.put("msg", Message.FAIL.getMsg());
			return JSON.toJSONString(map);
		}
		return JSON.toJSONString(map);
	}

	/**
	 * 删除角色
	 * @param businessRole 角色信息
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String deleteby(BusinessRole r)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		try{

			//同步到BPM
			/*String result = workFlowService.DeleteJob(r.getCode());*/
			
			businessRoleService.delete(r);
			map=Common.getTipMsg("delete", 0);
		}
		catch(Exception e){
			map=Common.getTipMsg("delete", 1);
		}
		return JSON.toJSONString(map);
	}

	@RequestMapping("/queryBusinessRoleById")
	@ResponseBody
	public String queryBusinessRoleById(String id){
		System.out.println("---------------------------");
		String json = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			BusinessRole bus= businessRoleService.queryById(id);
			if (bus != null) {
				map.put("status_code", StatusCode.CORRECT.getStatusCode());
				map.put("msg", Message.SUCCESS.getMsg());
				map.put("businessrole", bus);
				json = JSON.toJSONString(map);
			} else {
				map.put("status_code", StatusCode.ERROR.getStatusCode());
				map.put("msg", Message.FAIL.getMsg());
				json = JSON.toJSONString(map);
			}
			return json;
		}
		catch(Exception e){
			return json;
		}		
	}
	
	/**
	 * 获取下拉框数据
	 * @param users 人员信息
	 */
	@RequestMapping(value="/dateForSelect")
	@ResponseBody
	public String dateForSelect(HttpServletResponse response,String type) {
		try {
			List<BusinessRole> pList=null;
			String returnValue="<option value=''>&nbsp;</option>";
			pList = businessRoleService.listBySysId(type);
			for(int i=0;i<pList.size();i++){
				returnValue+="<option value='"+pList.get(i).getId()+"'>"+pList.get(i).getBusinessRoleName()+"</option> ";
			}
			return returnValue;
		} catch (Exception e) {
			e.printStackTrace();
			return "<option value=''>&nbsp;</option>";
		}
	}
	
	//保存该人员的角色信息
	@RequestMapping(value = "saveRoleOrgRole")
	@ResponseBody
	public String saveRoleOrgRole() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String roleId = request.getParameter("roleId");
			String ids = request.getParameter("codes");
			String[] id = ids.split(",");
			roleOrgRoleService.deleteByRoleId(roleId);
			
			for(String uid:id){
				String userid = uid;
				if(id==null||"".equals(userid)){
					map.put("msg", Message.DELETESUCCESS.getMsg());
					map.put("status_code", StatusCode.CORRECT.getStatusCode());
				}else{
				
					BusinessRoleOrgRole boru = new BusinessRoleOrgRole();
					boru.setId(UUIDHexGenerator.getUUID());
					boru.setBusinessOrgRoleId(userid);
					boru.setBusinessRoleId(roleId);
					roleOrgRoleService.save(boru);
				}
				map.put("msg", Message.DELETESUCCESS.getMsg());
				map.put("status_code", StatusCode.CORRECT.getStatusCode());
			}
			
		} catch (Exception e) {
			return JSON.toJSONString(Common.getTipMsg("ex", 2));
		}
		return JSON.toJSONString(map);
	}
	
	/**
	 * 获取下拉框数据
	 */
	@RequestMapping("/getSelectData1")
	@ResponseBody
	public String getSelectData1() {
		try {
			List<BusinessRole> pList=null;
			String returnValue="<option value=''>&nbsp;</option>";
			pList = businessRoleService.list();
			for(int i=0;i<pList.size();i++){
				returnValue+="<option value='"+pList.get(i).getId()+"'>"+pList.get(i).getBusinessRoleName()+"</option> ";
			}
			return returnValue;
		} catch (Exception e) {
			e.printStackTrace();
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
			List<BusinessRole> pList=null;
			pList = businessRoleService.list();
			map.put("plist", pList);
			map.put("status", "1");
			map.put("msg","操作成功");
		} catch (Exception e) {
			map.put("status", "0");
			map.put("msg","操作失败");
		}
		return JSON.toJSONString(map);
	}
	
}