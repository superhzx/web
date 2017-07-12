package cn.rails.iServer.core.controller.system;

import java.net.URLDecoder;
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

import cn.rails.iServer.core.entity.Permission;
import cn.rails.iServer.core.entity.User;
import cn.rails.iServer.core.service.system.PermissionService;
import cn.rails.iServer.core.service.system.RolePermissionService;
import cn.rails.iServer.core.service.system.UserRoleService;
import cn.rails.iServer.core.service.system.UserService;
import cn.rails.iServer.utils.Constant;
import cn.rails.iServer.utils.UUIDHexGenerator;
import cn.rails.iServer.utils.Constant.Message;
import cn.rails.iServer.utils.Constant.StatusCode;
import cn.rails.iServer.utils.page.Order;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

import com.alibaba.fastjson.JSON;

/**
 * @author wl
 * @date 2017年3月22日
 * @description 权限
 */
@Controller
@RequestMapping(value = "/permission", produces = "text/html;charset=UTF-8")
public class PermissionController {
	@Autowired
	private PermissionService service;
	@Autowired
	private RolePermissionService rolePermissionService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpSession httpSession;
	@Autowired
	private UserService userService;
	
	/**
	 * 登陆后根据登录人获取该人员菜单
	 * @param loginname 用户登录名
	 * @param pwd 用户编码
	 * @author sunyh
	 */
	@RequestMapping("/queryMenu")
	@ResponseBody
	public String queryMenu() {
		try {
			//根据当前登陆人获取角色信息
			String token = request.getHeader("token");
			String userCode = Constant.getTokenValue(token);
			User user= userService.queryUserByCode(userCode);
			String userId=user.getId();
			//返回字符串
			String returnValue = "[";
			String parentPageName, childPageName;
			//根据人员ID获取第一层菜单列表
			List<Permission> parentMenulist= service.getMenuListByUserId(userId,"0");
			for( int i=0;i<parentMenulist.size();i++){
				parentPageName = parentMenulist.get(i).getPageUrl()==null?"":parentMenulist.get(i).getPageUrl();
				returnValue += "{ id:'" + parentMenulist.get(i).getId() + "', "
							+ "title:'"+ parentMenulist.get(i).getName() + "' , "
							+ "icon:'"+ parentMenulist.get(i).getMenuIcon() + "' , "
							+ "url:'" + parentPageName + "' ";
				//根据第一层菜单加载下边的第二层菜单
				List<Permission> childMenulist= service.getMenuListByUserId(userId,parentMenulist.get(i).getId());
				if (childMenulist.size() > 0) {
					returnValue += ", children :[";
					for (int j = 0; j < childMenulist.size(); j++) {
						childPageName = childMenulist.get(j).getPageUrl()==null?"":childMenulist.get(j).getPageUrl();
						returnValue += "{ id:'" + childMenulist.get(j).getId()+"',"
								+ "title:'" + childMenulist.get(j).getName() + "',"
								+ "icon:'"+ childMenulist.get(j).getMenuIcon() + "',"
								+ "url:'" + childPageName + "'},";
					}
					returnValue = returnValue.substring(0, returnValue.length() - 1);
					returnValue += "]";
				}
				returnValue += "},";
			}
			returnValue = returnValue.substring(0, returnValue.length() - 1);
			returnValue += "]";
			return returnValue;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("获取菜单失败");
			return "";
		}
	}
	
	/**
	 * 删除权限和角色菜单表中数据
	 * @author sunyh
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(Permission t) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			service.delete(t);
			rolePermissionService.deleteByCondition("permissionId", t.getId());
			map.put("msg", Message.DELETESUCCESS.getMsg());
			map.put("status_code", StatusCode.CORRECT.getStatusCode());
		} catch (Exception e) {
			map.put("msg", Message.DELETEFAIL.getMsg());
			map.put("status_code", StatusCode.ERROR.getStatusCode());
		}
		return JSON.toJSONString(map);
	}
	
	/**
	 *菜单相关数据加载
	 * @author sunyh
	 */
	@RequestMapping("/list")
	@ResponseBody
	public String list(String conditionJson) {
		String json = "";
		try {
			// 获取分页及排序相关信息
			int page = Integer.parseInt(request.getParameter("page"));// 当前页
			int rows = Integer.parseInt(request.getParameter("rows"));// 行数
			String orderString = request.getParameter("sidx");// 排序字段
			String orderBy = request.getParameter("sord");// 行数
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
			String parentId = request.getParameter("pid");
			if(parentId!=null){
				parentId= URLDecoder.decode(parentId,"UTF-8");
				param.addCondition("parentId", parentId);
			}
			param.setAndSql(likeSql);
			// 查询数据
			PageTion data = service.listByPage(page, rows, param);
			// 设置页面展示相关信息
			map.put("total", data.getTotal());// 总页数
			map.put("rows", data.getList());// 总行数
			map.put("page", data.getPageNo());// 第几页
			map.put("rowNum", data.getPageSize());// 第几页
			map.put("records", data.getNum());//总条数
			json = JSON.toJSONString(map);
			return json;
		} catch (Exception e) {
			return json;
		}
	}
	
	/**
	 *菜单相关数据保存
	 * @author sunyh
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(Permission t) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (t.getId().isEmpty()) {
				try {
					t.setId(UUIDHexGenerator.getUUID());
					service.save(t);
					map.put("msg", Message.ADDSUCCESS.getMsg());
					map.put("status_code", StatusCode.CORRECT.getStatusCode());
				} catch (Exception e) {
					map.put("msg", Message.ADDFAIL.getMsg());
					map.put("status_code", StatusCode.ERROR.getStatusCode());
				}
			} else {
				try {
					service.update(t);
					map.put("msg", Message.MODIFYSUCCESS.getMsg());
					map.put("status_code", StatusCode.CORRECT.getStatusCode());
				} catch (Exception e) {
					map.put("msg", Message.MODIFYFAIL.getMsg());
					map.put("status_code", StatusCode.ERROR.getStatusCode());
				}
			}
		} catch (Exception e) {
			map.put("status_code", StatusCode.ERROR.getStatusCode());
			map.put("msg", Message.FAIL.getMsg());
			return JSON.toJSONString(map);
		}
		return JSON.toJSONString(map);
	}
	
	/**
	 *获取菜单树
	 * @author sunyh
	 */
	@RequestMapping("/getTreeData")
	@ResponseBody
	public String getTreeData(String tableName, String tiaojian) {
		String returnValue = " var zNodes =[";
		Paramter par = new Paramter();
		Order order = new Order();
		order.setClumn("parentId");
		par.addOrder(order);
		order = new Order();
		order.setClumn("sortNumber");
		par.addOrder(order);
		@SuppressWarnings("unchecked")
		List<Permission> pList = (List<Permission>) service.listByPage(1, 1000, par).getList();
		for (int i = 0; i < pList.size(); i++) {
			returnValue += "{ id:'"+pList.get(i).getId()+"', pId:'"+pList.get(i).getParentId()+
				"', name:'"+pList.get(i).getName()+"'},";
			}
		returnValue = returnValue.substring(0, returnValue.length() - 1);
		returnValue += "]; ";
		return returnValue;
	}
	/**
	 *获取下拉框数据
	 * @author sunyh
	 */
	@RequestMapping("/getSelectData")
	@ResponseBody
	public String getSelectData() {
		try {
			List<Permission> pList = null;
			String returnValue = "<option value=''>&nbsp;</option>";
			pList = service.queryByCondition("", "");
			for (int i = 0; i < pList.size(); i++) {
				returnValue += "<option value='" + pList.get(i).getId() + "'>"
						+ pList.get(i).getName() + "</option> ";
			}
			return returnValue;
		} catch (Exception e) {
			return "<option value=''>&nbsp;</option>";
		}
	}
	/**
	 * 根据权限ID获取所有权限相关信息
	 * @param roleId 角色信息ID
	 */
	@RequestMapping(value = "queryPermissionInfoById/{id}")
	@ResponseBody
	public String queryPermissionInfoById(@PathVariable("id")String id) {
		String json = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Permission p = service.queryById(id);
			if (p != null) {
				map.put("status_code", StatusCode.CORRECT.getStatusCode());
				map.put("msg", Message.SUCCESS.getMsg());
				map.put("privilege", p);
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
