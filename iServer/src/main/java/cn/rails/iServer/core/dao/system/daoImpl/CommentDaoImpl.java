package cn.rails.iServer.core.dao.system.daoImpl;

import org.springframework.stereotype.Repository;

import cn.rails.iServer.base.HibernateBaseDao;
import cn.rails.iServer.core.dao.system.CommentDao;
import cn.rails.iServer.core.entity.Comment;
/**
 * @author       : hzx
 * @date         ：2017-04-07
 */
@Repository
public class CommentDaoImpl extends HibernateBaseDao<Comment> implements CommentDao {
	@Override
	public Class<Comment> getEntityClass() {
		return Comment.class;
	}

	
} 