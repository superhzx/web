package cn.rails.iServer.core.service.system;

import java.util.List;

import cn.rails.iServer.base.IBaseService;
import cn.rails.iServer.core.entity.User;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

/**
 * @author       : wangqi
 * @date         ：2017-03-22
 */
public interface UserService extends IBaseService<User>{
	public void update(User t) ;

	public void updateUser(User t) ;
	
	public void deleteByCondition(String key,Object value);

	public List<User> queryByCondition(String key, String value);
	
	public User queryUser(String name,String pwd);
	
	public User queryUserByCode(String name);
	
	/**
	 * APP获取下拉列表数据
	 * 
	 * @param par
	 * @return
	 */
	public List<User> getSelectData(String key, String value);
	
	//根据部门ID过滤
	public List<User> listByDeptId(Paramter par);
	
	public PageTion listByPage(int pageNo, int pageSize, Paramter par);

	public User queryByName(String name);

	public List<User> queryByMagnitude(String codes);
	
	String queryIdByCode(String code);

	public void updateUser(String id, String mobile);

	public void deleteByCode(String userCode);
}
