package cn.rails.iServer.core.dao.system;

import java.util.List;

import cn.rails.iServer.base.IBaseDao;
import cn.rails.iServer.core.entity.User;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;
/**
 * @author       : wangqi
 * @date         ：2017-03-22
 */
public interface UserDao extends IBaseDao<User>{
	public int checkLogin(String name,String pwd);
	
	public User queryUser(String name,String pwd);
	
	public User queryUserByCode(String code);
	
	public void updateUser(User t) ;
	/**
	 * 保存或更新
	 */
	public void saveOrUpdate(User t);
	/**
	 * 按条件删除
	 */
	public void deleteByCondition(String key,Object value);
	
	/**
	 * 获取下拉列表
	 * @param gid
	 * @return
	 */
	public List<User> getSelectData(String key, String value);
	//根据部门ID过滤
	public List<User> listByDeptId(Paramter par);
	
	public PageTion listByPage(int pageNo, int pageSize, Paramter par);

	public User queryByCode(String userCode);

	public User queryByName(String name);

	List<User> queryByMagnitude(String codes);
	
	String queryIdByCode(String code);

	public void updateUserById(String id, String mobile);

	public void deleteByCode(String userCode);
}
