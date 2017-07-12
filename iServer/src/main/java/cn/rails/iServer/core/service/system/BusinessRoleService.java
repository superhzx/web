package cn.rails.iServer.core.service.system;

import java.util.List;

import cn.rails.iServer.base.IBaseService;
import cn.rails.iServer.core.entity.BusinessOrgRole;
import cn.rails.iServer.core.entity.BusinessOrgRoleUser;
import cn.rails.iServer.core.entity.BusinessRole;
import cn.rails.iServer.core.entity.BusinessSystem;
import cn.rails.iServer.core.entity.Department;
import cn.rails.iServer.core.entity.User;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

/**
 * 
 * @author hzx
 * @date 2017年4月10日
 * @description 审核信息
 */
public interface BusinessRoleService extends IBaseService<BusinessRole>{
	public void update(BusinessRole t) ;
	

	public List<BusinessRole> listBySysId(String type);
	
	public List<BusinessRole> getSelectData(String key, String value);
	
	public void deleteBySystemId(String systemId);


	public List<BusinessRole> queryByCondition(String key,
			String value);

}
