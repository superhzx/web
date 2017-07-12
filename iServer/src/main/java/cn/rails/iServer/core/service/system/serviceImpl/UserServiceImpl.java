package cn.rails.iServer.core.service.system.serviceImpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rails.iServer.core.dao.system.DepartmentDao;
import cn.rails.iServer.core.dao.system.PositionDao;
import cn.rails.iServer.core.dao.system.PositionDepartmentDao;
import cn.rails.iServer.core.dao.system.UserDao;
import cn.rails.iServer.core.dao.system.UserPositionDao;
import cn.rails.iServer.core.entity.Department;
import cn.rails.iServer.core.entity.Position;
import cn.rails.iServer.core.entity.PositionDepartment;
import cn.rails.iServer.core.entity.Role;
import cn.rails.iServer.core.entity.User;
import cn.rails.iServer.core.entity.UserPosition;
import cn.rails.iServer.core.entity.UserRole;
import cn.rails.iServer.core.service.system.DepartmentService;
import cn.rails.iServer.core.service.system.RoleService;
import cn.rails.iServer.core.service.system.UserRoleService;
import cn.rails.iServer.core.service.system.UserService;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

/**
 * @author       : wangqi
 * @date         ：2017-03-22
 */
@Service
@Transactional
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao dao ;
	@Autowired
	private UserPositionDao userPositionDao ;
	@Autowired
	private PositionDao positionDao ;
	@Autowired
	private RoleService roleService;
	@Autowired 
	private UserRoleService userRoleService;
	@Autowired
	private PositionDepartmentDao positionDepartmentDao;
	@Autowired
	private DepartmentService departmentService;
	
	public void delete(User t) {
		dao.delete(t);
	}

	public List<User> list() {
		return dao.list();
	}

	public PageTion listByPage(int pageNo, int pageSize, Paramter par) {
		PageTion pageTion = dao.listByPage(pageNo, pageSize, par);
		for(User user:(List<User>)pageTion.getList()){
			List<UserPosition> userPositions = userPositionDao.queryByCondition("userId", user.getId());
			if(userPositions != null && userPositions.size()>0){
				String positionIds = "";
				String positionNames = "";
				String isMains = "";
				for(UserPosition userPosition:userPositions){
					String positionId = userPosition.getPositionId();
					List<Position> positions = positionDao.queryByCondition("id", positionId);
					if(positions != null && positions.size()>0){
						positionIds += positionId +",";
						positionNames += positions.get(0).getName() + ",";
						isMains +=userPosition.getIsMain()+ ",";
					}
					
				}
				if(!positionNames.equals("")){
					positionNames = positionNames.substring(0,positionNames.length()-1);
					user.setPositionId(positionIds);
					user.setPositionName(positionNames);
					user.setIsMain(isMains);
				}
			}
			List<UserRole> userRoles = userRoleService.queryByCondition("userId", user.getId());
			if(userRoles != null && userRoles.size()>0){
				String roleIds = "";
				String roleNames = "";
				for(UserRole userRole:userRoles){
					String roleId = userRole.getRoleId();
					List<Role> roles = roleService.queryByCondition("id", roleId);
					if(roles != null & roles.size()>0){
						roleIds += roleId +",";
						roleNames += roles.get(0).getName()+",";
					}
				}
				if(!roleNames.equals("")){
					roleNames = roleNames.substring(0, roleNames.length()-1);
					user.setRoleId(roleIds);
					user.setRoleName(roleNames);
				}
			}
		}
		return pageTion;
	}

	public User queryById(Serializable id) {
		User user = dao.queryById(id);
		UserPosition up = userPositionDao.getMainPositon(user.getId());
		Department d = new Department();
		if(up!=null){
			//岗位部门信息
			PositionDepartment pd =  positionDepartmentDao.getDepartmentIdByPosition(up.getPositionId());
			if(pd!=null){
				//部门信息
				d = departmentService.queryById(pd.getDepartmentId());
				user.setDepartmentCode(d.getCode());
				user.setDepartmentName(d.getName());
				
				Department depart = departmentService.queryByCode(d.getCode());
				if(depart != null){
					user.setMagnitudeCode(depart.getCode());
					user.setMagnitudeName(depart.getName());
				}
			}
		}
		
		
		
		return user;
	}

	public void save(User t) {
		dao.save(t);
	}

	public void deleteByCondition(String key, Object value) {
		dao.deleteByCondition(key, value);
	}

	public void update(User t) {
		dao.update(t);
	}

	public void updateUser(User t) {
		dao.updateUser(t);
	}

	
	public User queryUser(String name,String pwd){
		return dao.queryUser(name, pwd);
	}
	
	public User queryUserByCode(String code){
		return dao.queryUserByCode(code);
	}
	
	//获取下拉数据
	public List<User> getSelectData(String key, String value){
		return dao.getSelectData(key, value);
	}

	@Override
	public List<User> listByDeptId(Paramter par) {
		// TODO Auto-generated method stub
		return dao.listByDeptId(par);
	}

	@Override
	public List<User> queryByCondition(User t) {
		// TODO Auto-generated method stub
		return dao.queryByCondition(t);
	}

	@Override
	public List<User> queryByCondition(String key, String value) {
		// TODO Auto-generated method stub
		return dao.queryByCondition(key, value);
	}

	@Override
	public User queryByName(String name) {
		// TODO Auto-generated method stub
		return dao.queryByName(name);
	}

	@Override
	public List<User> queryByMagnitude(String codes) {
		// TODO Auto-generated method stub
		return dao.queryByMagnitude(codes);
	}

	@Override
	public String queryIdByCode(String code) {
		// TODO Auto-generated method stub
		return dao.queryIdByCode(code);
	}

	@Override
	public void updateUser(String id, String mobile) {
		// TODO Auto-generated method stub
		dao.updateUserById(id, mobile);
	}

	@Override
	public void deleteByCode(String userCode) {
		// TODO Auto-generated method stub
		dao.deleteByCode(userCode);
	}

}
