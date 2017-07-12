package cn.rails.iServer.core.dao.system.daoImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import cn.rails.iServer.base.HibernateBaseDao;
import cn.rails.iServer.core.dao.system.UserDao;
import cn.rails.iServer.core.entity.Department;
import cn.rails.iServer.core.entity.User;
import cn.rails.iServer.utils.page.Order;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;
/**
 * @author       : wangqi
 * @date         ：2017-02-16
 */
@Repository
public class UserDaoImpl extends HibernateBaseDao<User> implements UserDao {
	@Override
	public Class<User> getEntityClass() {
		return User.class;
	}

	public int checkLogin(String name, String pwd) {
		String hql = "select count(*) from User where name=? and password=?";
		Long x = (Long) getSession().createQuery(hql).setParameter(0,
				name).setParameter(1, pwd).uniqueResult();
		return (int)(long)x;
	}

	public User queryUser(String name, String pwd) {
		String hql ="from User where code =? and pwd =?";
		 User user = (User) getSession().createQuery(hql).setParameter(0,
					name).setParameter(1, pwd).uniqueResult();
		return user;
	}
	
	public User queryUserByCode(String code) {
		String hql ="from User where code =?";
		 User user = (User) getSession().createQuery(hql).setParameter(0,
					code).uniqueResult();
		return user;
	}
	

	@Override
	public void deleteByCondition(String key, Object value) { 
		String hql = "delete from "+getEntityClass().getSimpleName()+" where 1=1 and "+key+"=?";
		getSession().createQuery(hql).setParameter(0, value).executeUpdate();
	}

	public void updateUser(User t) {
		Session session = getSession();
		Query q = session.createQuery("update Users u set u.name = ? ,u.age = ? where u.id = ? ");
 
		q.executeUpdate();
	}
	//下拉框
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getSelectData(String key, String value){
		// 获取session
		String hql = "select new Users(a.gid,nvl(a.userName,' ')) "
				+ "from Users a ";
		List<User> pro =  (List<User>) getSession().createQuery(hql).list();
		return pro;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> listByDeptId(Paramter par) {
		String sql = " select A.GID,A.DEPARTMENT_ID,A.ROLE_ID,A.USERNAME,A.PASSWORD,A.PYM,A.FOUNDER,A.SORT,A.CREATE_DATE,A.LOGINNAME,A.TELEPHONE from Users a left join USER_DEPARTMENT b   ON a.gid = b.user_Id WHERE 1=1 ";
		//插入参数集合
				Map<String,Object> map = new HashMap<String, Object>();
				if(par!=null){
					 map = par.getMap();
					 //设置参数
					 for(Map.Entry<String, Object> en:map.entrySet()){
						 sql+=" and "+en.getKey()+"=:"+en.getKey();
					 }
					 //是否判断日期
					 if(par.getBtweenAnd()!=null){
						 sql+=par.getBtweenAnd();
					 }
					//and sql 语句
					 if(par.getAndSql()!=null){
						 sql+=par.getAndSql();
					 }
					 //是否排序
					 List<Order> orders = par.getOrders();
					 if(orders.size()>0){
						 sql += " order by ";
						 for(Order order :orders){
							 if(order.getFalg()== 1){
								 sql+=" a."+order.getClumn()+" desc,";
							 }else{
								 sql+=" a."+order.getClumn()+",";
							 }
						 }
						 sql =  sql.substring(0, sql.lastIndexOf(","));
					 }
				}
			Query query = getSession().createSQLQuery(sql);
			List<Object []> list = query.list();
			List<User> returnlist = new ArrayList<User>();
			for (int i = 0; i < list.size(); i++) {
				Object [] obj = list.get(i);
				User user = new User();
				String gid = (String)obj[0]==null?"":(String)obj[0];
				String DEPARTMENT_ID = (String)obj[1]==null?"":(String)obj[1];
				String USERNAME = (String)obj[3]==null?"":(String)obj[3];
				String PASSWORD = (String)obj[4]==null?"":(String)obj[4];
				String PYM = (String)obj[5]==null?"":(String)obj[5];
				String FOUNDER = (String)obj[6]==null?"":(String)obj[6];
				String LOGINNAME = (String)obj[9]==null?"":(String)obj[9];
				returnlist.add(user);
			}
		return returnlist;
	}

	@Override
	public PageTion listByPage(int pageNo,int pageSize ,Paramter par){
		//获取session
		Session session = getSession();
		//得到表名称
		String hql =" from User a where 1=1";
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
			 if(orders.size()>0){
				 hql += " order by ";
				 for(Order order :orders){
					 if(order.getFalg()== 1){
						 hql+=" a."+order.getClumn()+" desc,";
					 }else{
						 hql+=" a."+order.getClumn()+",";
					 }
				 }
				 hql =  hql.substring(0, hql.lastIndexOf(","));
				 hql+=" ,a.sortNumber ";
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
		@SuppressWarnings("unchecked")
		List<User> list = query.list();
		return new PageTion(pageNo, pageSize, list, total);
	}

	@Override
	public User queryByCode(String code) {
		String hql="from User where code =?";
		User user = (User) getSession().createQuery(hql).setParameter(0,
				code).uniqueResult();
		return user;
	}
	
	public String queryIdByCode(String code) {
		String hql="select a.id from s_user a where code =?";
		String user =  (String) getSession().createSQLQuery(hql).setParameter(0,
				code).uniqueResult();
		return user;
	}

	@Override
	public User queryByName(String name) {
		String hql="from User where name =?";
		User user = (User) getSession().createQuery(hql).setParameter(0,
				name).uniqueResult();
		return user;
	}
	
	
	
	@Override
	public List<User> queryByMagnitude(String codes) {
		// 获取session
		String sql = "select a.id,a.code,a.name,a.alias_name,a.pwd,a.mobile,a.phone,a.email,a.card_number,a.available_date,a.expire_date,a.create_user_id,a.sort_number,a.memo,a.sex,a.syn_flag,a.erp_code"
				+ " from s_user a left join s_user_position b on a.id=b.user_id left join s_position c on b.position_id=c.id"
				+ " left join s_position_department d on c.id=d.position_id left join s_department e on d.department_id=e.id"
				+ " where e.code in "+codes+"";
		List<User> pro =  (List<User>) getSession().createSQLQuery(sql).addEntity(User.class).list();
		return pro;
	}

	@Override
	public void updateUserById(String id, String mobile) {
		// TODO Auto-generated method stub
		String sql = "update s_user u set u.mobile = '"+mobile+"' where u.id='"+id+"'";
		getSession().createSQLQuery(sql).executeUpdate();
	}

	@Override
	public void deleteByCode(String userCode) {
		String sql = "delete from s_user where code='"+userCode+"'";
		getSession().createSQLQuery(sql).executeUpdate();
	}
	
} 