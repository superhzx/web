package cn.rails.iServer.core.controller.system;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rails.iServer.core.entity.Position;
import cn.rails.iServer.core.service.system.PositionDepartmentService;
import cn.rails.iServer.core.service.system.PositionService;
import cn.rails.iServer.core.service.system.UserService;
import cn.rails.iServer.utils.UUIDHexGenerator;
import cn.rails.iServer.utils.Constant.Message;
import cn.rails.iServer.utils.Constant.StatusCode;
import cn.rails.iServer.utils.page.Order;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

import com.alibaba.fastjson.JSON;

/**
 * @author hzx
 * @date 2017年3月20日
 * @description 岗位控制层
 */
@RequestMapping(value ="/position",produces="text/html;charset=UTF-8")
@Controller
public class PositionController {
	@Autowired
	private PositionService service;
	@Autowired
	private UserService userService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpSession httpSession;
	@Autowired
	private PositionDepartmentController positionDepartmentController;
	@Autowired
	private PositionDepartmentService positionDepartmentService;
	

	/**
	 * 获取岗位列表信息
	 * @author hzx
	 */
	@RequestMapping(value="/listByPage")
	@ResponseBody
	public String listByPage() throws UnsupportedEncodingException {
		//获取分页及排序相关信息
		int page = Integer.parseInt(request.getParameter("page"));// 当前页
		int rows = Integer.parseInt(request.getParameter("rows"));// 行数
		String orderString = request.getParameter("sidx");// 排序字段
		String orderBy = request.getParameter("sord");// 行数
		String deptId = request.getParameter("deptId");// 部门ID
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
		//查询条件
		String likeSql=" ";
		
		//String parentId=request.getParameter("pid");// 父节点
		if(deptId!=null&&!deptId.equals("null")){
			deptId= URLDecoder.decode(deptId,"UTF-8");
			//par.addCondition("deptId", deptId);
			likeSql+=(" and id = '"+deptId+"'");
		}
		
		String name= request.getParameter("name");
		if(name!=null&&!name.equals("null")&&name!=""){
			name= URLDecoder.decode(name,"UTF-8");
			likeSql+=" and name like '%"+name+"%' ";
		}
		
		if(params!=null && !params.equals("()") && !params.equals("null") &&  !params.equals("")){
			likeSql+=" and id  not in  "+params;
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
	 * @author hzx
	 * @throws ParseException 
	 */
	@RequestMapping("/save/{departmentId}")
	@ResponseBody
	public String save(@PathVariable("departmentId") String departmentId,Position t) throws ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(t.getId().length()!=32){
				try {
					String id = UUIDHexGenerator.getUUID();
					t.setId(id);
					service.save(t);
					positionDepartmentController.save(id,departmentId);
					map.put("msg", Message.ADDSUCCESS.getMsg());
					map.put("status_code", StatusCode.CORRECT.getStatusCode());
				} catch (Exception e) {
					map.put("msg", Message.ADDFAIL.getMsg());
					map.put("status_code", StatusCode.ERROR.getStatusCode());
				}
			}else {
				try {
					System.out.println(t.getName());
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
	 * 删除部门信息
	 * @author sunyh
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(Position t) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			service.delete(t);
			map.put("msg", Message.DELETESUCCESS.getMsg());
			map.put("status_code", StatusCode.CORRECT.getStatusCode());
		} catch (Exception e) {
			map.put("msg", Message.DELETEFAIL.getMsg());
			map.put("status_code", StatusCode.ERROR.getStatusCode());
		}
		return JSON.toJSONString(map);
	}
	
	/**
	 * 获取部门树节点信息
	 * @author sunyh
	 */
	@RequestMapping("/getTreeData")
	@ResponseBody
	public String getTreeData(String tableName, String tiaojian) {
		List<Position> pList = null;
		Paramter par = new Paramter();
		String returnValue = " var zNodes =[";
		pList = service.queryByCondition(par);
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
	 * @author hzx
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getSelectData.do")
	@ResponseBody
	public String getSelectData() {
		try {
			List<Position> pList=null;
			String returnValue="<option value=''>&nbsp;</option>";
			pList=service.list();
			for(int i=0;i<pList.size();i++){
				returnValue+="<option value='"+pList.get(i).getId()+"'>"+pList.get(i).getName()+"</option> ";
			}
			return returnValue;
		} catch (Exception e) {
			return "<option value=''>&nbsp;</option>";
		}
	}
	
	/**
	 *根据部门ID获取部门信息
	 * @author sunyh
	 */
	@RequestMapping("/{deptId}/edit")
	@ResponseBody
	public String queryById(@PathVariable("deptId") String deptId) {
		Position position=service.queryById(deptId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status_code", StatusCode.CORRECT.getStatusCode());
		map.put("msg", Message.SUCCESS.getMsg());
		map.put("department", position);
		return JSON.toJSONString(map);
	}
	
	/**
	 * 修改部门信息
	 * @author hzx
	 * @throws ParseException 
	 */
	@RequestMapping("/update/{departmentId}/{pdid}")
	@ResponseBody
	public String update(@PathVariable("departmentId") String departmentId,@PathVariable("pdid") String pdid,Position t) throws ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String token=request.getHeader("token");
				try {
					service.update(t);
					try {
						positionDepartmentService.updatepositiondepartment(pdid,departmentId);
						map.put("msg", Message.SUCCESS.getMsg());
						map.put("status_code", StatusCode.CORRECT.getStatusCode());
					} catch (Exception e) {
						map.put("msg", Message.SUCCESS.getMsg());
						map.put("status_code", StatusCode.ERROR.getStatusCode());
					}
					map.put("msg", Message.SUCCESS.getMsg());
					map.put("status_code", StatusCode.CORRECT.getStatusCode());
				} catch (Exception e) {
					map.put("msg", Message.SUCCESS.getMsg());
					map.put("status_code", StatusCode.ERROR.getStatusCode());
				}
		} catch (Exception e) {
			map.put("status_code", StatusCode.ERROR.getStatusCode());
			map.put("msg", Message.FAIL.getMsg());
			return JSON.toJSONString(map);
		}
		return JSON.toJSONString(map);
	}
	
	
	
}
