package cn.rails.iServer.core.service.system.serviceImpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rails.iServer.core.dao.system.DocumentsDao;
import cn.rails.iServer.core.entity.Documents;
import cn.rails.iServer.core.service.system.DocumentsService;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

/**
 * @author wl
 * @date 2017年5月5日
 * @description 上传文件
 */
@Service
@Transactional
public class DocumentsServiceImpl implements DocumentsService{

	@Autowired
	private DocumentsDao dao;
	
	@Override
	public PageTion listByPage(int pageNo, int pageSize, Paramter par) {
		return dao.listByPage(pageNo, pageSize, par);
	}

	@Override
	public List<Documents> list() {
		return dao.list();
	}

	@Override
	public void save(Documents t) {
		dao.save(t);;
		
	}

	@Override
	public void update(Documents t) {
		dao.update(t);
		
	}

	@Override
	public void delete(Documents t) {
		dao.delete(t);
		
	}

	@Override
	public Documents queryById(Serializable id) {
		
		return dao.queryById(id);
	}

	@Override
	public List<Documents> queryByCondition(Documents t) {
		
		return dao.queryByCondition(t);
	}

}
