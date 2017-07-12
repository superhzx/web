/**
 * 
 */
package cn.rails.iServer.core.controller.dictionary;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.rails.iServer.core.entity.FeeClass;
import cn.rails.iServer.core.service.system.FeeClassService;
import cn.rails.iServer.utils.UUIDHexGenerator;
import cn.rails.iServer.utils.Constant.Message;
import cn.rails.iServer.utils.Constant.StatusCode;
import cn.rails.iServer.utils.page.Order;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

/**
 * @author dengjm
 * @date 2017年6月5日
 * @description 
 */
@RequestMapping(value="/feeclass" ,produces="text/html;charset=UTF-8")
@Controller
public class FeeClassController {
	//绑定时间类型特殊处理
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
	}
	
	@Autowired
	private FeeClassService feeClassService;
	@Autowired
	private HttpServletRequest request;
	
	@RequestMapping(value="/listByPage")
	@ResponseBody
	public String listByPage(String conditionJson) throws UnsupportedEncodingException{
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
			likeSql+=" and name like '%"+name+"%' ";
		}
		if(params!=null && !params.equals("()") && !params.equals("null") &&  !params.equals("")){
			likeSql+=" and id  not in  "+params;
		}
		param.setAndSql(likeSql);
		
		// 查询数据
		PageTion data = feeClassService.listByPage(page, rows, param);
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
	 *获取菜单树
	 * @author dengjm
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
		List<FeeClass> pList = (List<FeeClass>) feeClassService.listByPage(1, 1000, par).getList();
		for (int i = 0; i < pList.size(); i++) {
			returnValue += "{ id:'"+pList.get(i).getId()+"', pId:'"+pList.get(i).getParentId()+
				"', name:'"+pList.get(i).getName()+"'},";
		}
		returnValue = returnValue.substring(0, returnValue.length() - 1);
		returnValue += "]; ";
		return returnValue;
	}
	
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
			PageTion data = feeClassService.listByPage(page, rows, param);
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
	
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(FeeClass t) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (t.getId().isEmpty()) {
				try {
					t.setId(UUIDHexGenerator.getUUID());
					feeClassService.save(t);
					map.put("msg", Message.ADDSUCCESS.getMsg());
					map.put("status_code", StatusCode.CORRECT.getStatusCode());
				} catch (Exception e) {
					map.put("msg", Message.ADDFAIL.getMsg());
					map.put("status_code", StatusCode.ERROR.getStatusCode());
				}
			} else {
				try {
					feeClassService.update(t);
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
	
	@RequestMapping(value = "queryPermissionInfoById/{id}")
	@ResponseBody
	public String queryPermissionInfoById(@PathVariable("id")String id) {
		String json = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			FeeClass p = feeClassService.queryById(id);
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
	
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(FeeClass t) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			feeClassService.delete(t);
			//根据父节点，删除下级费用
			feeClassService.deleteByCondition("parentId",t.getId());
			map.put("msg", Message.DELETESUCCESS.getMsg());
			map.put("status_code", StatusCode.CORRECT.getStatusCode());
		} catch (Exception e) {
			map.put("msg", Message.DELETEFAIL.getMsg());
			map.put("status_code", StatusCode.ERROR.getStatusCode());
		}
		return JSON.toJSONString(map);
	}
}
