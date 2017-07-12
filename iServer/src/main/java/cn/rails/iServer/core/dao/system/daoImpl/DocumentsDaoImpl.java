package cn.rails.iServer.core.dao.system.daoImpl;

import org.springframework.stereotype.Repository;

import cn.rails.iServer.base.HibernateBaseDao;
import cn.rails.iServer.core.dao.system.DocumentsDao;
import cn.rails.iServer.core.entity.Documents;

/**
 * @author wl
 * @date 2017年5月5日
 * @description 上传文件
 */
@Repository
public class DocumentsDaoImpl extends HibernateBaseDao<Documents>implements DocumentsDao{

	@Override
	public Class<Documents> getEntityClass() {
		// TODO Auto-generated method stub
		return Documents.class;
	}

}
