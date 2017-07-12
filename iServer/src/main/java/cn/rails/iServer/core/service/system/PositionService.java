package cn.rails.iServer.core.service.system;
import java.util.List;

import cn.rails.iServer.base.IBaseService;
import cn.rails.iServer.core.entity.Position;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

/**
 * <p>Title      : 中国铁道科学研究院[]</p>
 * <p>Description: [部门管理]</p>
 * <p>Copyright  : Copyright (c) 2017</p>
 * <p>Company    : 铁科院电子所</p>
 * <p>Departsment : 信息中心</p>
 * @author       : sunyh
 * @version      : 1.0
 * @date         ：2017-02-16
 */
public interface PositionService extends IBaseService<Position>{
	public List<Position> queryByCondition(String key,Object value);
	public List<Object> getdeptIds(String DepartsmentId,String orders);
	
	//根据paramer查询数据
	public List<Position> queryByCondition(Paramter par);
	public List<Object> mysqlgetdeptIds(String DepartmentId, String orders);
	public void deleteById(String pid);

}
