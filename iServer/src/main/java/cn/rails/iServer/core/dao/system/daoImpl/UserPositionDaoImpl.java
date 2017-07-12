package cn.rails.iServer.core.dao.system.daoImpl;

import org.springframework.stereotype.Repository;

import cn.rails.iServer.base.HibernateBaseDao;
import cn.rails.iServer.core.dao.system.UserPositionDao;
import cn.rails.iServer.core.entity.UserPosition;
/**
 * @author       : wangqi
 * @date         ：2017-02-16
 */
@Repository
public class UserPositionDaoImpl extends HibernateBaseDao<UserPosition> implements UserPositionDao {
	@Override
	public Class<UserPosition> getEntityClass() {
		return UserPosition.class;
	}

	public UserPosition queryUserPosition(String userId, String positionId) {
		String hql ="from UserPosition where userId =? and positionId =?";
		UserPosition userPosition = (UserPosition) getSession().createQuery(hql).setParameter(0,
				userId).setParameter(1, positionId).uniqueResult();
		return userPosition;
	}
	
	public void deleteUserPositionByUserId(String userId){
		String hql ="delete from UserPosition where userId =?";
		getSession().createQuery(hql).setParameter(0,userId).executeUpdate();
	}
	
	public UserPosition getMainPositon(String userId){
		String hql="from UserPosition where userId =? and isMain ='1'";
		UserPosition userPosition = (UserPosition) getSession().createQuery(hql).setParameter(0,
				userId).uniqueResult();
		return userPosition;
	}
} 