package cn.rails.iServer.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rails.iServer.utils.page.Order;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

public abstract class HibernateBaseDao<T> implements IBaseDao<T> {
	@Autowired
	private SessionFactory sessionFactory;
	
	public abstract Class<T> getEntityClass ();
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getSession (){
		return this.sessionFactory.getCurrentSession();
	}

	public Session getNewSession (){
		return this.sessionFactory.openSession();
	}
	/**
	 * 查询分页
	 * @param pageNo
	 * @param pageSize
	 * @param par
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageTion listByPage(int pageNo,int pageSize ,Paramter par){
		//获取session
		Session session = getSession();
		//得到表名称
		Class<? extends Object> class1 = getEntityClass();
		String simpleName = class1.getSimpleName();
		String hql = "from "+simpleName+" where 1=1 ";
		//String hql = "from "+simpleName+" where 1=1 and isDelete = 0 ";
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
		//创建查询
		String count = "select count(*) "+hql;
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
		List<T> list = query.list();
		return new PageTion(pageNo, pageSize, list, total);
	}
	/**
	 * 模糊查询分页
	 * @param pageNo
	 * @param pageSize
	 * @param par
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageTion listByPageByLike(int pageNo,int pageSize ,Paramter par){
		//获取session
		Session session = getSession();
		//得到表名称
		Class<? extends Object> class1 = getEntityClass();
		String simpleName = class1.getSimpleName();
		String hql = "from "+simpleName+" where 1=1 ";
		//插入参数集合
		Map<String,Object> map = new HashMap<String, Object>();
		if(par!=null){
			 map = par.getMap();
			 //设置参数
			 for(Map.Entry<String, Object> en:map.entrySet()){
				 hql+=" and "+en.getKey()+" like:"+en.getKey();
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
		//创建查询
		String count = "select count(*) "+hql;
		Query query = session.createQuery(hql);
		Query countQuery = session.createQuery(count);
		//插叙结果
		for(Map.Entry<String, Object> en:map.entrySet()){
			query.setParameter(en.getKey(), "%"+en.getValue()+"%");
			countQuery.setParameter(en.getKey(), en.getValue());
		}
		
		//分页
		query.setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize);
		//结果总数
		long lon = 	(Long) countQuery.uniqueResult();
		int total =(int) lon;
		List<T> list = query.list();
		return new PageTion(pageNo, pageSize, list, total);
	}

	public void delete(T t) {
		getSession().delete(t);
	}

	@SuppressWarnings("unchecked")
	public List<T> list() {
		Class<? extends Object> class1 = getEntityClass();
		String simpleName = class1.getSimpleName();
		String hql = "from "+simpleName ;
		List<T> list = getSession().createQuery(hql).list();
		return list;
	}

	public List<T> queryByCondition(T t) {
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> queryByCondition(String key ,Object obj) {
		Criteria cri = getSession().createCriteria(getEntityClass());
		if(key.equals("")){
			
		}else{
			cri.add(Restrictions.eq(key, obj));
		}
		return cri.list();
	}

	@SuppressWarnings("unchecked")
	public T queryById(Serializable id) {
		Class<? extends Object> class1 = getEntityClass();
		T t= (T) getSession().get(class1, id);
		return t;
	}

	public void save(T t) {
		try {
			getSession().save(t);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("=================>");
		}
		
	}

	public void update(T t) {
		getSession().update(t);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryBySql(String sql){
		 List<Map<String,Object>> list = getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		 return list;
	}
	@SuppressWarnings("unchecked")
	public T queryOneByCondition(String key, Object value) {
		T retVal = (T) getSession().createCriteria(getEntityClass()).
		add(Restrictions.eq(key, value)).setMaxResults(1).uniqueResult();
		return retVal;
	}
	public void saveOrUpdate(T t) {
		Session session = getSession();
		session.saveOrUpdate(t);
	};
	/**
	 * 按条件删除
	 */
	public void deleteByCondition(String key,Object value){
		String hql = "delete from "+getEntityClass().getSimpleName()+" where 1=1 and "+key+"=?";
		getSession().createQuery(hql).setParameter(0, value).executeUpdate();
	}
	/**
	 * 安多条件删除
	 */
	public void deleteByConditions(Map<String,Object> map){
		
	}
}
