package cn.rails.iServer.core.dao.system.daoImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import cn.rails.iServer.base.HibernateBaseDao;
import cn.rails.iServer.core.dao.system.PermissionDao;
import cn.rails.iServer.core.entity.Permission;

/**
 * @author wl
 * @date 2017年3月22日
 * @description 权限
 */
@Repository
public class PermissionDaoImpl extends HibernateBaseDao<Permission> implements PermissionDao {
	@Override
	public Class<Permission> getEntityClass() {
		return Permission.class;
	}
	/**
	 * 根据人员ID获取菜单列表
	 * @author sunyh
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Permission> getMenuListByUserId(String userId,String parentId) {
		try {
			//根据人员获取角色菜单
			String menuListSqlString="SELECT * FROM s_Permission P WHERE P.ID IN ("
					+ " SELECT RP.permission_id FROM s_role_permission RP WHERE RP.ROLE_ID IN ("
					+ " SELECT UR.ROLE_ID FROM S_USER_ROLE UR WHERE UR.USER_ID = '"+userId+"')) "
					+ " AND P.PARENT_ID = '"+parentId+"' ORDER BY P.SORT_Number ";
			SQLQuery sqlQuery = getSession().createSQLQuery(menuListSqlString);
			List<Permission> rp=  new ArrayList<Permission>();
			sqlQuery.addEntity(Permission.class);
			rp = sqlQuery.list();
			/*for(int i=0;i<list.size();i++){
				Permission permission=new Permission();
				Object [] obj = (Object[]) list.get(i);
				permission.setId(obj[0]==null?"":(String)obj[0]);
				permission.setParentId(obj[1]==null?"":(String)obj[1]);
				permission.setName(obj[2]==null?"":(String)obj[2]);
				permission.setPageUrl(obj[4]==null?"":(String)obj[3]);
				permission.setSortNumber((obj[5]==null?0:((BigDecimal)obj[4])).intValue());
				permission.setMemo(obj[6]==null?"":(String)obj[5]);
				permission.setMenuIcon(obj[7]==null?"":(String)obj[6]);
				rp.add(permission);
			}*/
			return rp;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}
}
