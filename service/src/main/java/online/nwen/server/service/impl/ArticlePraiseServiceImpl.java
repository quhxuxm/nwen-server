package online.nwen.server.service.impl;

import online.nwen.server.bo.CreateArticlePraiseResponseBo;
import online.nwen.server.bo.ResponseCode;
import online.nwen.server.bo.SecurityContextBo;
import online.nwen.server.dao.api.IAnthologyDao;
import online.nwen.server.dao.api.IArticleDao;
import online.nwen.server.dao.api.IArticlePraiseDao;
import online.nwen.server.dao.api.IUserDao;
import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Article;
import online.nwen.server.domain.ArticlePraise;
import online.nwen.server.domain.User;
import online.nwen.server.service.api.IArticlePraiseService;
import online.nwen.server.service.api.ISecurityService;
import online.nwen.server.service.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
class ArticlePraiseServiceImpl implements IArticlePraiseService {
    private IArticlePraiseDao articlePraiseDao;
    private ISecurityService securityService;
    private IArticleDao articleDao;
    private IUserDao userDao;
    private IAnthologyDao anthologyDao;

    ArticlePraiseServiceImpl(IArticlePraiseDao articlePraiseDao, ISecurityService securityService, IArticleDao articleDao, IUserDao userDao,
                             IAnthologyDao anthologyDao) {
        this.articlePraiseDao = articlePraiseDao;
        this.securityService = securityService;
        this.articleDao = articleDao;
        this.userDao = userDao;
        this.anthologyDao = anthologyDao;
    }

    @Override
    public CreateArticlePraiseResponseBo praise(Long articleId) {
        SecurityContextBo securityContextBo = this.securityService.checkAndGetSecurityContextFromCurrentThread();
        User currentUser = this.userDao.getByUsername(securityContextBo.getUsername());
        if (currentUser == null) {
            throw new ServiceException(ResponseCode.USER_NOT_EXIST);
        }
        Article article = this.articleDao.getById(articleId);
        if (article == null) {
            throw new ServiceException(ResponseCode.ARTICLE_NOT_EXIST);
        }
        ArticlePraise existingArticlePraise = this.articlePraiseDao.getByArticleAndUser(article, currentUser);
        if (existingArticlePraise != null) {
            CreateArticlePraiseResponseBo result = new CreateArticlePraiseResponseBo();
            result.setArticleId(articleId);
            result.setArticlePraiseId(existingArticlePraise.getId());
            result.setCreateTime(existingArticlePraise.getCreateTime());
            return result;
        }
        ArticlePraise articlePraise = new ArticlePraise();
        articlePraise.setArticle(article);
        articlePraise.setCreateTime(new Date());
        articlePraise.setUser(currentUser);
        this.articlePraiseDao.save(articlePraise);
        CreateArticlePraiseResponseBo result = new CreateArticlePraiseResponseBo();
        result.setArticleId(articleId);
        result.setArticlePraiseId(articlePraise.getId());
        result.setCreateTime(articlePraise.getCreateTime());
        return result;
    }

    @Override
    public Long countTotalPraiseOfAnthology(Long anthologyId) {
        Anthology anthology = this.anthologyDao.getById(anthologyId);
        if (anthology == null) {
            throw new ServiceException(ResponseCode.ANTHOLOGY_NOT_EXIST);
        }
        return this.articlePraiseDao.countTotalPraiseOfAnthology(anthology);
    }

    @Override
    public Long countTotalPraiseOfArticle(Long articleId) {
        Article article = this.articleDao.getById(articleId);
        if (article == null) {
            throw new ServiceException(ResponseCode.ARTICLE_NOT_EXIST);
        }
        return this.articlePraiseDao.countTotalPraiseOfArticle(article);
    }
}
