package com.bsdl.service;

import com.bsdl.dao.CommentRepository;
import com.bsdl.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: blog
 * @create: 2020-07-26 18:15
 **/

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    private List<Comment> tmpRepliesComments;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createTime");
        // 获取所有的根评论，判断条件是它们都没有父评论
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        // 复制新的一组评论
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment: comments) {
            Comment tmp = new Comment();
            BeanUtils.copyProperties(comment, tmp);
            commentsView.add(tmp);
        }
        // 初始化容器，临时存放所有的回复
        this.tmpRepliesComments = new ArrayList<>();
        // 递归得到所有根评论下所有的回复，set到根回复列表里
        for (Comment comment: commentsView) {
            List<Comment> replies = comment.getReplyComments();
            for (Comment reply: replies) {
                recursive(reply);
            }
            comment.setReplyComments(tmpRepliesComments);
            // 清空临时容器
            tmpRepliesComments = new ArrayList<>();
        }
        return commentsView;
    }

    /**
     * 递归每个顶级的评论节点，添加到临时存放的评论容器
     * @param reply Comment 回复
     * @return void
     */
    private void recursive(Comment reply) {
        tmpRepliesComments.add(reply);
        List<Comment> newReplies = reply.getReplyComments();
        if (newReplies != null && newReplies.size() > 0) {
            for (Comment newReply: newReplies) {
                recursive(newReply);
            }
        }
    }


    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            comment.setParentComment(commentRepository.findById(parentCommentId).orElse(null));
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }
}
