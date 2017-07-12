package cn.rails.iServer.core.dao.system.daoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import cn.rails.iServer.base.HibernateBaseDao;
import cn.rails.iServer.core.dao.system.PositionDepartmentDao;
import cn.rails.iServer.core.entity.PositionDepartment;
import cn.rails.iServer.utils.page.Order;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;
/**
 * <p>Title      : 中国铁道科学研究院[]</p>
 * <p>Description: [人员部门管理]</p>
 * <p>Copyright  : Copyright (c) 2017</p>
 * <p>Company    : 铁科院电子所</p>
 * <p>Department : 信息中心</p>
 * @author       : sunyh
 * @version      : 1.0
 * @date         ：2017-02-16
 */
@Repository
public class PositionDepartmentDaoImpl extends HibernateBaseDao<PositionDepartment> implements PositionDepartmentDao {
	@Override
	public Class<PositionDepartment> getEntityClass() {
		return PositionDepartment.class;
	}
	
	@Override
	public PageTion listByPage(int pageNo,int pageSize ,Paramter par){
		//获取session
		Session session = getSession();
		//得到表名称
		String hql ="from PositionDepartment a  where 1=1 ";
		//插入参数集合
		Map<String,Object> map = new HashMap<String, Object>();
		if(par!=null){
			 map = par.getMap();
			 //设置参数
			 for(Map.Entry<String, Object> en:map.entrySet()){
				 hql+=" and "+en.getKey()+"=:"+en.getKey();
			 }
			 //是否判断日期
			 if(par.getBtweenAnd()!=null){
				 hql+=par.getBtweenAnd();
			 }
			//and sql 语句
			 if(par.getAndSql()!=null){
				 hql+=par.getAndSql();
			 }
			 //是否排序
			 List<Order> orders = par.getOrders();
			 /*if(orders.size()>0){
				 hql += " order by ";
				 for(Order order :orders){
					 if(order.getFalg()== 1){
						 hql+=" a."+order.getClumn()+",";
					 }else{
						 hql+=" a."+order.getClumn()+",";
					 }
				 }
				 hql =  hql.substring(0, hql.lastIndexOf(","));
				 hql+=" a.positions.sortNumber ";
			 }*/
			
		}
		//创建查询
		String count = "select count(*) "+hql;
		hql = "select new PositionDepartment (a.id,a.positionId,a.departmentId,a.positions,a.departments) " + hql;
		Query query = session.createQuery(hql);
		Query countQuery = session.createQuery(count);
		//插叙结果
		for(Map.Entry<String, Object> en:map.entrySet()){
			query.setParameter(en.getKey(), en.getValue());
			countQuery.setParameter(en.getKey(), en.getValue());
		}
		
		//分页
		query.setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize);
		//结果总数
		long lon = 	(Long) countQuery.uniqueResult();
		int total =(int) lon;
		@SuppressWarnings("unchecked")
		List<PositionDepartment> list = query.list();
		return new PageTion(pageNo, pageSize, list, total);
	}

	
	//更新人员部门表中数据
	@Override
	public void updateByUserId(String userId, String departmentId) {
		String sql="UPDATE s_position_department SET DEPARTMENT_ID='"+departmentId+"' where position_id='"+userId+"'";
		getSession().createSQLQuery(sql).executeUpdate();
	}
	//删除人员部门表中的数据
	@Override
	public void deleteByUserId(String userId) {
		String sql="DELETE FROM s_position_department WHERE USER_ID='"+userId+"'";
		getSession().createSQLQuery(sql).executeUpdate();
	}
	//根据人员ID获取人员部门表的数据
	@SuppressWarnings("unchecked")
	@Override
	public PositionDepartment getDepartmentIdByPosition(String positionId) {
		String hql = " from PositionDepartment a where a.positionId=?";
		List<PositionDepartment> list  = getSession().createQuery(hql).setParameter(0, positionId).list();
		PositionDepartment ud = new PositionDepartment();
		if(list.size()>0){
			ud = list.get(0);
		}
		return ud;
	}

	@Override
	public void updatepositiondepartment(String pdid, String departmentId) {
		// TODO Auto-generated method stub
		String sql="UPDATE s_position_department SET DEPARTMENT_ID='"+departmentId+"' where id='"+pdid+"'";
		getSession().createSQLQuery(sql).executeUpdate();
	}
}
