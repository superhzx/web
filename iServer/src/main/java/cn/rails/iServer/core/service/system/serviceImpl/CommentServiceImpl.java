package cn.rails.iServer.core.service.system.serviceImpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rails.iServer.core.dao.system.CommentDao;
import cn.rails.iServer.core.dao.system.UserDao;
import cn.rails.iServer.core.entity.Comment;
import cn.rails.iServer.core.entity.User;
import cn.rails.iServer.core.service.system.CommentService;
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
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentDao commentDao;
	@Autowired
	private UserDao userDao;
	
	@Override
	public PageTion listByPage(int pageNo, int pageSize, Paramter par) {
		// TODO Auto-generated method stub
		PageTion pageTion = commentDao.listByPage(pageNo, pageSize, par);
		for(Comment comment:(List<Comment>)pageTion.getList()){
			User user = userDao.queryByCode(comment.getApprovalUser());
			comment.setApprovalUserName(user.getName());
		}
		return pageTion;
	}

	@Override
	public List<Comment> list() {
		return commentDao.list();
	}

	@Override
	public void save(Comment t) {
		// TODO Auto-generated method stub
		commentDao.save(t);
	}

	@Override
	public void delete(Comment t) {
		// TODO Auto-generated method stub
		commentDao.delete(t);
	}

	@Override
	public Comment queryById(Serializable id) {
		// TODO Auto-generated method stub
		return commentDao.queryById(id);
	}

	@Override
	public List<Comment> queryByCondition(Comment t) {
		// TODO Auto-generated method stub
		return commentDao.queryByCondition(t);
	}

	@Override
	public void update(Comment t) {
		// TODO Auto-generated method stub
		commentDao.update(t);
	}
	
}
