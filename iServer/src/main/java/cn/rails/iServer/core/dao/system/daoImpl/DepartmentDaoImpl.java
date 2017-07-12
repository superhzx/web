package cn.rails.iServer.core.dao.system.daoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import cn.rails.iServer.base.HibernateBaseDao;
import cn.rails.iServer.core.dao.system.DepartmentDao;
import cn.rails.iServer.core.entity.Department;
import cn.rails.iServer.core.entity.UserPosition;
import cn.rails.iServer.utils.page.Order;
import cn.rails.iServer.utils.page.Paramter;

/**
 * 
 * @author hzx
 * @date 2017年3月22日
 * @description 部门的DAO层
 */
@Repository
public class DepartmentDaoImpl extends HibernateBaseDao<Department>implements DepartmentDao{

	@Override
	public Class<Department> getEntityClass() {
		return Department.class;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getdeptIds(String departmentId, String orders) {
		//获取session
		Session session = getSession();
		String sqlString="";
		if(departmentId.equals("")||departmentId==""||departmentId==null){
			sqlString=" select d.code from Department d ";
			if(orders.equals("low")){
				//sqlString+=" d.gid=d.parent_id";
			}else{
				sqlString+=" where d.parent_code is null ";
			}
		}else{
			sqlString=" select d.code from Department d where d.parent_code is not null start with d.code='"+departmentId
					+ "' connect by prior ";
			if(orders.equals("low")){
				sqlString+=" d.code=d.parent_code";
			}else{
				sqlString+=" d.parent_code=d.code";
			}
		}
		List<Object> query= session.createSQLQuery(sqlString).list();
		return query;
	}
	//mysql函数获取子部门
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> MySQLgetdeptIds(String departmentId, String orders) {
		//获取session
		Session session = getSession();
		String sqlString="";
		if(departmentId.equals("")||departmentId==""||departmentId==null){
			sqlString=" select d.code from s_Department d ";
			if(orders.equals("low")){
				
			}else{
				sqlString+=" where d.parent_code is null ";
			}
		}else{
			sqlString="select getChildGids('"+departmentId+"');";
		}
		List<Object> query= session.createSQLQuery(sqlString).list();
		return query;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Department> queryByCondition(Paramter par) {
		//获取session
		Session session = getSession();
		String hql = "from Department where 1=1 ";
		//插入参数集合
		Map<String,Object> map = new HashMap<String, Object>();
		if(par!=null){
			 map = par.getMap();
			 //设置参数
			 for(Map.Entry<String, Object> en:map.entrySet()){
				 hql+=" and "+en.getKey()+"=:"+en.getKey();
			 }
			 //and sql 语句
			 if(par.getAndSql()!=null){
				 hql+=par.getAndSql();
			 }
			 //是否判断日期
			 if(par.getBtweenAnd()!=null){
				 hql+=par.getBtweenAnd();
			 }
			 //是否排序
			 List<Order> orders = par.getOrders();
			 if(orders.size()>0){
				 hql += " order by ";
				 for(Order order :orders){
					 if(order.getFalg()== 2){
						 hql+=" "+order.getClumn()+" desc,";
					 }else{
						 hql+=" "+order.getClumn()+",";
					 }
				 }
				 hql =  hql.substring(0, hql.lastIndexOf(","));
			 }
		}
		Query query = session.createQuery(hql);
		
		return query.list();
	}
	@Override
	public Department queryByCode(String code) {
		String hql="from Department where code =?";
		Department department = (Department) getSession().createQuery(hql).setParameter(0,
				code).uniqueResult();
		return department;
	}

	@Override
	public List<Department> queryByParentCode(String code) {
		String hql="from Department where parentCode =?";
		@SuppressWarnings("unchecked")
		List<Department> department = (List<Department>) getSession().createQuery(hql).setParameter(0,
				code).list();
		return department;
	}
}
