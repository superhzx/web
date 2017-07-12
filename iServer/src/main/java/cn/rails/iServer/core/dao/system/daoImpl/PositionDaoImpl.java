/**
 * 
 */
package cn.rails.iServer.core.dao.system.daoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import cn.rails.iServer.base.HibernateBaseDao;
import cn.rails.iServer.core.dao.system.PositionDao;
import cn.rails.iServer.core.entity.Position;
import cn.rails.iServer.core.entity.PositionDepartment;
import cn.rails.iServer.utils.page.Order;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

/**
 * <p>Title      : 中国铁道科学研究院[]</p>
 * <p>Description: [部门管理]</p>
 * <p>Copyright  : Copyright (c) 2017</p>
 * <p>Company    : 铁科院电子所</p>
 * <p>Department : 信息中心</p>
 * @author       : sunyh
 * @version      : 1.0
 * @date         ：2017-02-16
 */
@Repository
public class PositionDaoImpl extends HibernateBaseDao<Position>implements PositionDao{

	@Override
	public Class<Position> getEntityClass() {
		return Position.class;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getdeptIds(String departmentId, String orders) {
		//获取session
		Session session = getSession();
		String sqlString="";
		if(departmentId.equals("")||departmentId==""||departmentId==null){
			sqlString=" select d.id from Position d ";
			if(orders.equals("low")){
				//sqlString+=" d.gid=d.parent_id";
			}else{
				sqlString+=" where d.parent_id is null ";
			}
		}else{
			sqlString=" select d.id from Position d where d.parent_id is not null start with d.id='"+departmentId
					+ "' connect by prior ";
			if(orders.equals("low")){
				sqlString+=" d.id=d.parent_id";
			}else{
				sqlString+=" d.parent_id=d.id";
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
			sqlString=" select d.id from Position d ";
			if(orders.equals("low")){
				
			}else{
				sqlString+=" where d.parent_id is null ";
			}
		}else{
			sqlString="select getChildGids('"+departmentId+"');";
		}
		List<Object> query= session.createSQLQuery(sqlString).list();
		return query;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Position> queryByCondition(Paramter par) {
		//获取session
		Session session = getSession();
		String hql = "from Position where 1=1 ";
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

}
