package online.nwen.server.service.impl;

import online.nwen.server.bo.*;
import online.nwen.server.dao.api.IArticleCommentDao;
import online.nwen.server.dao.api.IArticleDao;
import online.nwen.server.dao.api.IUserDao;
import online.nwen.server.domain.Article;
import online.nwen.server.domain.ArticleComment;
import online.nwen.server.domain.User;
import online.nwen.server.service.api.IArticleCommentService;
import online.nwen.server.service.api.ISecurityService;
import online.nwen.server.service.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
class ArticleCommentServiceImpl implements IArticleCommentService {
    private IArticleCommentDao articleCommentDao;
    private IArticleDao articleDao;
    private ISecurityService securityService;
    private IUserDao userDao;

    ArticleCommentServiceImpl(IArticleCommentDao articleCommentDao, IArticleDao articleDao, ISecurityService securityService, IUserDao userDao) {
        this.articleCommentDao = articleCommentDao;
        this.articleDao = articleDao;
        this.securityService = securityService;
        this.userDao = userDao;
    }

    @Override
    public CreateArticleCommentResponseBo create(Long articleId, CreateArticleCommentRequestBo createArticleCommentRequestBo) {
        SecurityContextBo securityContextBo = this.securityService.checkAndGetSecurityContextFromCurrentThread();
        User currentUser = this.userDao.getByUsername(securityContextBo.getUsername());
        if (currentUser == null) {
            throw new ServiceException(ResponseCode.USER_NOT_EXIST);
        }
        if (articleId == null) {
            throw new ServiceException(ResponseCode.INPUT_ERROR);
        }
        if (createArticleCommentRequestBo.getContent() == null) {
            throw new ServiceException(ResponseCode.INPUT_ERROR);
        }
        Article article = this.articleDao.getById(articleId);
        if (article == null) {
            throw new ServiceException(ResponseCode.ARTICLE_NOT_EXIST);
        }
        ArticleComment articleComment = new ArticleComment();
        articleComment.setArticle(article);
        articleComment.setCommenter(currentUser);
        articleComment.setCreateTime(new Date());
        articleComment.setContent(createArticleCommentRequestBo.getContent());
        if (createArticleCommentRequestBo.getReplyToCommentId() != null) {
            ArticleComment replyToComment = this.articleCommentDao.getById(createArticleCommentRequestBo.getReplyToCommentId());
            if (replyToComment.getArticle().getId().compareTo(article.getId()) != 0) {
                throw new ServiceException(ResponseCode.COMMENT_NOT_BELONG_TO_ARTICLE);
            }
            articleComment.setReplyTo(replyToComment);
        }
        this.articleCommentDao.save(articleComment);
        CreateArticleCommentResponseBo result = new CreateArticleCommentResponseBo();
        result.setCommentId(articleComment.getId());
        return result;
    }

    @Override
    public Page<ArticleCommentBo> getComments(Long articleId, Pageable pageable) {
        Article article = this.articleDao.getById(articleId);
        if (article == null) {
            throw new ServiceException(ResponseCode.ARTICLE_NOT_EXIST);
        }
        Page<ArticleComment> articleComments = this.articleCommentDao.getByArticle(article, pageable);
        return articleComments.map(this::convert);
    }

    @Override
    public Page<ArticleCommentBo> getReplyToComments(Long commentId, Pageable pageable) {
        ArticleComment replyToArticleComment = this.articleCommentDao.getById(commentId);
        if (replyToArticleComment == null) {
            throw new ServiceException(ResponseCode.COMMENT_NOT_EXIST);
        }
        Page<ArticleComment> articleComments = this.articleCommentDao.getByReplyTo(replyToArticleComment, pageable);
        return articleComments.map(this::convert);
    }

    private ArticleCommentBo convert(ArticleComment articleComment) {
        ArticleCommentBo result = new ArticleCommentBo();
        result.setArticleId(articleComment.getArticle().getId());
        result.setCommentId(articleComment.getId());
        result.setContent(articleComment.getContent());
        result.setCreateTime(articleComment.getCreateTime());
        result.setReplyToCommentId(articleComment.getReplyTo().getId());
        Page<ArticleComment> replyComments = this.articleCommentDao.getByReplyTo(articleComment, Pageable.unpaged());
        result.setReplyComments(replyComments.map(this::convert).getContent());
        return result;
    }
}
