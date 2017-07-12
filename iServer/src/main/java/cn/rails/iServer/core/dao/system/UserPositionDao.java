package cn.rails.iServer.core.dao.system;

import cn.rails.iServer.base.IBaseDao;
import cn.rails.iServer.core.entity.UserPosition;
/**
 * 
 * @author wangqi
 * @date 2017年3月23日
 * @description
 */
public interface UserPositionDao extends IBaseDao<UserPosition>{
	public UserPosition queryUserPosition(String userId,String positionId);
	
	public void deleteUserPositionByUserId(String userId);
	
	public UserPosition getMainPositon(String userId);
}
