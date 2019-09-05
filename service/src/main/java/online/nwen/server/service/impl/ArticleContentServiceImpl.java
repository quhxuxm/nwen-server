package online.nwen.server.service.impl;

import online.nwen.server.bo.ArticleContentBo;
import online.nwen.server.bo.ResponseCode;
import online.nwen.server.bo.SecurityContextBo;
import online.nwen.server.common.ServerConfiguration;
import online.nwen.server.dao.api.IAnthologyDao;
import online.nwen.server.dao.api.IArticleContentDao;
import online.nwen.server.dao.api.IArticleDao;
import online.nwen.server.dao.api.IUserDao;
import online.nwen.server.domain.*;
import online.nwen.server.service.api.IArticleContentService;
import online.nwen.server.service.api.IContentAnalyzeService;
import online.nwen.server.service.api.ISecurityService;
import online.nwen.server.service.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
class ArticleContentServiceImpl implements IArticleContentService {
    private IArticleContentDao articleContentDao;
    private ServerConfiguration serverConfiguration;
    private IArticleDao articleDao;
    private IAnthologyDao anthologyDao;
    private IUserDao userDao;
    private ISecurityService securityService;
    private IContentAnalyzeService contentAnalyzeService;

    ArticleContentServiceImpl(IArticleContentDao articleContentDao, ServerConfiguration serverConfiguration, IArticleDao articleDao,
                              IAnthologyDao anthologyDao, IUserDao userDao, ISecurityService securityService,
                              IContentAnalyzeService contentAnalyzeService) {
        this.articleContentDao = articleContentDao;
        this.serverConfiguration = serverConfiguration;
        this.articleDao = articleDao;
        this.anthologyDao = anthologyDao;
        this.userDao = userDao;
        this.securityService = securityService;
        this.contentAnalyzeService = contentAnalyzeService;
    }

    @Override
    public ArticleContent save(Article article, Long version, String content) {
        ArticleContent articleContent = new ArticleContent();
        ArticleContentId articleContentId = new ArticleContentId();
        articleContentId.setArticle(article);
        if (version == null) {
            version = this.generateContentVersion();
        }
        articleContentId.setVersion(version);
        articleContent.setId(articleContentId);
        String analyzedContent = this.contentAnalyzeService.analyze(content);
        articleContent.setContent(analyzedContent);
        articleContent.setVersionTime(new Date());
        return this.articleContentDao.save(articleContent);
    }

    @Override
    public ArticleContentBo getLastVersion(Article article) {
        ArticleContentId articleContentId = new ArticleContentId();
        articleContentId.setArticle(article);
        Long articleLastVersion = this.articleContentDao.getArticleContentLastVersion(article);
        articleContentId.setVersion(articleLastVersion);
        ArticleContent articleContent = this.articleContentDao.getById(articleContentId);
        if (articleContent == null) {
            return null;
        }
        ArticleContentBo articleContentBo = new ArticleContentBo();
        articleContentBo.setContent(articleContent.getContent());
        articleContentBo.setContentVersion(articleContent.getId().getVersion());
        articleContentBo.setVersionTime(articleContent.getVersionTime());
        return articleContentBo;
    }

    @Override
    public Page<ArticleContentBo> getArticleContentHistories(Long articleId, Pageable pageable) {
        SecurityContextBo securityContextBo = this.securityService.checkAndGetSecurityContextFromCurrentThread();
        User author = this.userDao.getByUsername(securityContextBo.getUsername());
        if (author == null) {
            throw new ServiceException(ResponseCode.USER_NOT_EXIST);
        }
        Article article = this.articleDao.getById(articleId);
        if (article == null) {
            throw new ServiceException(ResponseCode.ARTICLE_NOT_EXIST);
        }
        Anthology anthology = this.anthologyDao.getById(article.getAnthology().getId());
        if (anthology == null) {
            throw new ServiceException(ResponseCode.ANTHOLOGY_NOT_EXIST);
        }
        if (!anthology.getAuthor().getId().equals(author.getId())) {
            throw new ServiceException(ResponseCode.ANTHOLOGY_NOT_BELONG_TO_AUTHOR);
        }
        Page<ArticleContent> histories = this.articleContentDao.getByArticle(article, pageable);
        return histories.map(articleContent -> {
            ArticleContentBo result = new ArticleContentBo();
            result.setVersionTime(articleContent.getVersionTime());
            result.setContentVersion(articleContent.getId().getVersion());
            result.setContent(articleContent.getContent());
            return result;
        });
    }

    private long generateContentVersion() {
        return System.currentTimeMillis() / this.serverConfiguration.getArticleContentSaveInterval();
    }
}
