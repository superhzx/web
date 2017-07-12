package cn.rails.iServer.core.service.system.serviceImpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rails.iServer.core.dao.system.BusinessRoleDao;
import cn.rails.iServer.core.dao.system.BusinessSystemDao;
import cn.rails.iServer.core.dao.system.OrgRoleDao;
import cn.rails.iServer.core.dao.system.RoleOrgRoleDao;
import cn.rails.iServer.core.entity.BusinessOrgRole;
import cn.rails.iServer.core.entity.BusinessRole;
import cn.rails.iServer.core.entity.BusinessRoleOrgRole;
import cn.rails.iServer.core.entity.BusinessSystem;
import cn.rails.iServer.core.service.system.BusinessRoleService;
import cn.rails.iServer.core.service.system.OrgRoleService;
import cn.rails.iServer.core.service.system.RoleOrgRoleService;
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
public class BusinessRoleServiceImpl implements BusinessRoleService{

	@Autowired
	private BusinessRoleDao businessRoleDao;
	@Autowired
	private OrgRoleDao orgRoleDao;
	@Autowired
	private OrgRoleService orgRoleService;
	@Autowired
	private RoleOrgRoleService rorService;
	@Autowired
	private RoleOrgRoleDao roleOrgRoleDao;
	@Autowired
	private BusinessSystemDao businessSystemDao;

	@Override
	public PageTion listByPage(int pageNo, int pageSize, Paramter par) {
		// TODO Auto-generated method stub
		/*return businessRoleDao.listByPage(pageNo, pageSize, par);*/
		
		PageTion pageTion = businessRoleDao.listByPage(pageNo, pageSize, par);
		for(BusinessRole busRole:(List<BusinessRole>)pageTion.getList())
		{
			//把业务系统名称赋值给虚拟字段
			List<BusinessSystem> businessSystem=businessSystemDao.queryByCondition("id",busRole.getSystemId());
			if(businessSystem!=null&&businessSystem.size()>0)
			{
				busRole.setSystemName(businessSystem.get(0).getBusinessSystemName());
			}
			List<BusinessRoleOrgRole> busOrgRoles=roleOrgRoleDao.queryByCondition("businessRoleId",busRole.getId());
			if(busOrgRoles!=null&&busOrgRoles.size()>0)
			{
				String orgRoleIds="";
				String orgRoleNames="";
				for(BusinessRoleOrgRole busOrgRole:busOrgRoles)
				{
					String orgRoleId=busOrgRole.getBusinessOrgRoleId();
					List<BusinessOrgRole> org = orgRoleService.queryByCondition("id", orgRoleId);
					if(org!=null&&org.size()>0)
					{
						orgRoleIds += orgRoleId+",";
						orgRoleNames += org.get(0).getOrgRoleName()+",";
					}
				}
				if(!orgRoleNames.equals("")){
					orgRoleNames = orgRoleNames.substring(0, orgRoleNames.length()-1);
					busRole.setOrgRoleId(orgRoleIds);
					busRole.setOrgRoleName(orgRoleNames);
				}
			}
			
		}
			
		return pageTion;
	}

	@Override
	public List<BusinessRole> list() {
		// TODO Auto-generated method stub
		return businessRoleDao.list();
	}

	@Override
	public void save(BusinessRole t) {
		// TODO Auto-generated method stub
		businessRoleDao.save(t);
	}

	@Override
	public void delete(BusinessRole t) {
		// TODO Auto-generated method stub
		businessRoleDao.delete(t);
	}

	@Override
	public BusinessRole queryById(Serializable id) {
		// TODO Auto-generated method stub
		return businessRoleDao.queryById(id);
	}

	@Override
	public List<BusinessRole> queryByCondition(BusinessRole t) {
		// TODO Auto-generated method stub
		return businessRoleDao.queryByCondition(t);
	}

	@Override
	public void update(BusinessRole t) {
		// TODO Auto-generated method stub
		businessRoleDao.update(t);
	}


	@Override
	public List<BusinessRole> listBySysId(String aa) {
		
		return businessRoleDao.getSelectData(aa);
	}
	
	@Override
	public List<BusinessRole> getSelectData(String key, String value) {
		// TODO Auto-generated method stub
		return businessRoleDao.getSelectData(key, value);
	}
	
	@Override
	public void deleteBySystemId(String systemId) {
		businessRoleDao.deleteBySystemId(systemId);
	}

	@Override
	public List<BusinessRole> queryByCondition(String key, String value) {
		// TODO Auto-generated method stub
		return businessRoleDao.queryByCondition(key, value);
	}

}
