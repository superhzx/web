package cn.rails.iServer.core.service.system.serviceImpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rails.iServer.core.dao.system.DepartmentDao;
import cn.rails.iServer.core.dao.system.OrgRoleDao;
import cn.rails.iServer.core.dao.system.OrgRoleUserDao;
import cn.rails.iServer.core.entity.BusinessOrgRole;
import cn.rails.iServer.core.entity.BusinessOrgRoleUser;
import cn.rails.iServer.core.entity.Department;
import cn.rails.iServer.core.entity.User;
import cn.rails.iServer.core.service.system.BusinessRoleService;
import cn.rails.iServer.core.service.system.OrgRoleService;
import cn.rails.iServer.core.service.system.RoleOrgRoleService;
import cn.rails.iServer.core.service.system.UserService;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

/**
 * 
 * @author hzx
 * @date 2017年4月10日
 * @description 审核信息业务层
 */
@Service
@Transactional
public class OrgRoleServiceImpl implements OrgRoleService{

	@Autowired
	private OrgRoleDao orgRoleDao;
	@Autowired
	private DepartmentDao dao;
	@Autowired
	private OrgRoleUserDao orgRoleUserDao;
	@Autowired
	private UserService service;
	@Autowired
	private RoleOrgRoleService roleOrgRoleService;
	@Autowired
	private BusinessRoleService businessRoleService;

	@Override
	public PageTion listByPage(int pageNo, int pageSize, Paramter par) {
		PageTion pageTion = orgRoleDao.listByPage(pageNo, pageSize, par);
		Department department = null;
		for(BusinessOrgRole businessOrgRole:(List<BusinessOrgRole>)pageTion.getList()){
			department = dao.queryByCode(businessOrgRole.getDepartmentCode());
			businessOrgRole.setDepartmentName(department.getName());
			
			
			
			List<BusinessOrgRoleUser> businessOrgRoleUser = orgRoleUserDao.queryByCondition("orgRoleId", businessOrgRole.getId());
			if(businessOrgRoleUser != null && businessOrgRoleUser.size()>0){
				String userids = "";
				String userNames = "";
				String userCodes = "";
				for(BusinessOrgRoleUser orgRoleUser:businessOrgRoleUser){
					String userId = orgRoleUser.getUserId();
					List<User> users = service.queryByCondition("id", userId);
					if(users != null & users.size()>0){
						userids += userId +",";
						userNames += users.get(0).getName()+",";
						userCodes +=users.get(0).getCode()+",";
					}
				}
				if(!userNames.equals("")){
					userNames = userNames.substring(0, userNames.length()-1);
					userCodes = userCodes.substring(0, userCodes.length()-1);
					businessOrgRole.setUserId(userids);
					businessOrgRole.setUserName(userNames);
					businessOrgRole.setUserCode(userCodes);
				}
			}
			
			
			//拼接业务角色用于前台展示
			/*List<BusinessRoleOrgRole> businessRoleOrgRoles = roleOrgRoleService.queryByCondition("business_org_role_id", businessOrgRole.getId());
			if(businessRoleOrgRoles != null && businessRoleOrgRoles.size()>0){
				String businessRoleNames = "";
				for(BusinessRoleOrgRole businessRoleOrgRole:businessRoleOrgRoles){
					String businessRoleId = businessRoleOrgRole.getBusinessRoleId();
					List<BusinessRole> businessRoles = businessRoleService.queryByCondition("id", businessRoleId);
					if(businessRoles != null && businessRoles.size()>0){
						businessRoleNames += businessRoles.get(0).getBusinessRoleName() + ",";
					}
					
				}
				if(!businessRoleNames.equals("")){
					businessRoleNames = businessRoleNames.substring(0,businessRoleNames.length()-1);
					businessOrgRole.setRolename(businessRoleNames);
				}
			}*/
		}
		return pageTion;
	}

	@Override
	public List<BusinessOrgRole> list() {
		// TODO Auto-generated method stub
		return orgRoleDao.list();
	}

	@Override
	public void save(BusinessOrgRole t) {
		// TODO Auto-generated method stub
		orgRoleDao.save(t);
	}

	@Override
	public void delete(BusinessOrgRole t) {
		// TODO Auto-generated method stub
		orgRoleDao.delete(t);
	}

	@Override
	public BusinessOrgRole queryById(Serializable id) {
		// TODO Auto-generated method stub
		return orgRoleDao.queryById(id);
	}

	@Override
	public List<BusinessOrgRole> queryByCondition(BusinessOrgRole t) {
		// TODO Auto-generated method stub
		return orgRoleDao.queryByCondition(t);
	}

	@Override
	public void update(BusinessOrgRole t) {
		// TODO Auto-generated method stub
		orgRoleDao.update(t);
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.core.service.system.OrgRoleService#queryByCondition(java.lang.String, java.lang.String)
	 */
	@Override
	public List<BusinessOrgRole> queryByCondition(String key,
			String value) {
		// TODO Auto-generated method stub
		return orgRoleDao.queryByCondition(key, value);
	}
	
}
