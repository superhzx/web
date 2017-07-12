package cn.rails.iServer.core.service.system;

import cn.rails.iServer.base.IBaseService;
import cn.rails.iServer.core.entity.Comment;

/**
 * 
 * @author hzx
 * @date 2017年4月10日
 * @description 审核信息
 */
public interface CommentService extends IBaseService<Comment>{
	public void update(Comment t) ;
}
