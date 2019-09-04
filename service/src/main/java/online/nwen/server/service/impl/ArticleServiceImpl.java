package online.nwen.server.service.impl;

import online.nwen.server.bo.*;
import online.nwen.server.common.ServerConfiguration;
import online.nwen.server.common.constant.IConstant;
import online.nwen.server.dao.api.IAnthologyDao;
import online.nwen.server.dao.api.IArticleContentDao;
import online.nwen.server.dao.api.IArticleDao;
import online.nwen.server.dao.api.IUserDao;
import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Article;
import online.nwen.server.domain.ArticleContent;
import online.nwen.server.domain.User;
import online.nwen.server.service.api.IAnthologyService;
import online.nwen.server.service.api.IArticleService;
import online.nwen.server.service.api.ILocaleService;
import online.nwen.server.service.api.ISecurityService;
import online.nwen.server.service.exception.ServiceException;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
class ArticleServiceImpl implements IArticleService {
    private IArticleDao articleDao;
    private IAnthologyDao anthologyDao;
    private IUserDao userDao;
    private ServerConfiguration serverConfiguration;
    private IAnthologyService anthologyService;
    private MessageSource messageSource;
    private ILocaleService localeService;
    private ISecurityService securityService;
    private IArticleContentDao articleContentDao;

    ArticleServiceImpl(IArticleDao articleDao, IAnthologyDao anthologyDao, IUserDao userDao,
                       ServerConfiguration serverConfiguration, IAnthologyService anthologyService, MessageSource messageSource,
                       ILocaleService localeService, ISecurityService securityService, IArticleContentDao articleContentDao) {
        this.articleDao = articleDao;
        this.anthologyDao = anthologyDao;
        this.userDao = userDao;
        this.serverConfiguration = serverConfiguration;
        this.anthologyService = anthologyService;
        this.messageSource = messageSource;
        this.localeService = localeService;
        this.securityService = securityService;
        this.articleContentDao = articleContentDao;
    }

    @Override
    public CreateArticleResponseBo create(CreateArticleRequestBo createArticleRequestBo) {
        SecurityContextBo securityContextBo = this.securityService.checkAndGetSecurityContextFromCurrentThread();
        if (StringUtils.isEmpty(createArticleRequestBo.getTitle())) {
            throw new ServiceException(ResponseCode.ARTICLE_TITLE_IS_EMPTY);
        }
        if (createArticleRequestBo.getTitle().length() > this.serverConfiguration.getArticleTitleMaxLength()) {
            throw new ServiceException(ResponseCode.ARTICLE_TITLE_IS_TOO_LONG);
        }
        if (createArticleRequestBo.getDescription().length() > this.serverConfiguration.getArticleDescriptionMaxLength()) {
            throw new ServiceException(ResponseCode.ARTICLE_SUMMARY_IS_TOO_LONG);
        }
        User author = this.userDao.getByUsername(securityContextBo.getUsername());
        if (author == null) {
            throw new ServiceException(ResponseCode.USER_NOT_EXIST);
        }
        Anthology anthology = null;
        if (createArticleRequestBo.getAnthologyId() != null) {
            anthology = this.anthologyDao.getById(createArticleRequestBo.getAnthologyId());
            if (anthology == null) {
                throw new ServiceException(ResponseCode.ANTHOLOGY_NOT_EXIST);
            }
            if (anthology.getAuthor().getId().compareTo(author.getId()) != 0) {
                throw new ServiceException(ResponseCode.ANTHOLOGY_NOT_BELONG_TO_AUTHOR);
            }
        } else {
            anthology = author.getDefaultAnthology();
            if (anthology == null) {
                CreateAnthologyRequestBo createAnthologyRequestBo = new CreateAnthologyRequestBo();
                createAnthologyRequestBo.setAsDefault(true);
                createAnthologyRequestBo
                        .setTitle(this.messageSource.getMessage(IConstant.MessageKey.ANTHOLOGY_DEFAULT_TITLE_MESSAGE_KEY, null, this.localeService.getLocaleFromCurrentThread()));
                createAnthologyRequestBo.setDescription(
                        this.messageSource.getMessage(IConstant.MessageKey.ANTHOLOGY_DEFAULT_SUMMARY_MESSAGE_KEY, null, this.localeService.getLocaleFromCurrentThread()));
                CreateAnthologyResponseBo createAnthologyResponseBo = this.anthologyService.create(createAnthologyRequestBo);
                anthology = this.anthologyDao.getById(createAnthologyResponseBo.getAnthologyId());
            }
        }
        Article article = new Article();
        article.setTitle(createArticleRequestBo.getTitle());
        ArticleContent articleContent = new ArticleContent();
        articleContent.setContent(createArticleRequestBo.getContent());
        articleContent = this.articleContentDao.save(articleContent);
        article.setContent(articleContent);
        article.setDescription(createArticleRequestBo.getDescription());
        article.setCreateTime(new Date());
        article.setAnthology(anthology);
        article = this.articleDao.save(article);
        CreateArticleResponseBo result = new CreateArticleResponseBo();
        result.setArticleId(article.getId());
        return result;
    }

    @Override
    public DeleteArticlesResponseBo deleteAll(DeleteArticlesRequestBo deleteArticlesRequestBo) {
        SecurityContextBo securityContextBo = this.securityService.checkAndGetSecurityContextFromCurrentThread();
        User currentUser = this.userDao.getByUsername(securityContextBo.getUsername());
        if (currentUser == null) {
            throw new ServiceException(ResponseCode.USER_NOT_EXIST);
        }
        DeleteArticlesResponseBo result = new DeleteArticlesResponseBo();
        deleteArticlesRequestBo.getArticleIds().forEach(id -> {
            Article article = this.articleDao.getById(id);
            Anthology anthology = this.anthologyDao.getById(article.getAnthology().getId());
            if (anthology.getAuthor().getId().compareTo(currentUser.getId()) != 0) {
                return;
            }
            this.articleDao.delete(article);
            result.getArticleIds().add(article.getId());
        });
        return result;
    }

    @Override
    public Page<ArticleSummaryBo> getArticleSummariesOfAnthology(Long anthologyId, Pageable pageable) {
        Anthology anthology = this.anthologyDao.getById(anthologyId);
        if (anthology == null) {
            throw new ServiceException(ResponseCode.ANTHOLOGY_NOT_EXIST);
        }
        User author = this.userDao.getById(anthology.getAuthor().getId());
        if (author == null) {
            throw new ServiceException(ResponseCode.USER_NOT_EXIST);
        }
        Page<Article> articles = this.articleDao.getByAnthology(anthology, pageable);
        return articles.map(this::convertToSummary);
    }

    @Override
    public ArticleSummaryBo convertToSummary(Article article) {
        Anthology anthology = this.anthologyDao.getById(article.getAnthology().getId());
        if (anthology == null) {
            throw new ServiceException(ResponseCode.ANTHOLOGY_NOT_EXIST);
        }
        AnthologySummaryBo anthologySummaryBo = this.anthologyService.convertToSummary(anthology);
        ArticleSummaryBo articleSummaryBo = new ArticleSummaryBo();
        articleSummaryBo.setArticleId(article.getId());
        articleSummaryBo.setDescription(article.getDescription());
        articleSummaryBo.setCreateTime(article.getCreateTime());
        articleSummaryBo.setUpdateTime(article.getUpdateTime());
        articleSummaryBo.setTitle(article.getTitle());
        articleSummaryBo.setAnthologySummary(anthologySummaryBo);
        return articleSummaryBo;
    }

    @Override
    public ArticleDetailBo getArticleDetail(Long articleId) {
        Article article = this.articleDao.getById(articleId);
        if (article == null) {
            throw new ServiceException(ResponseCode.ARTICLE_NOT_EXIST);
        }
        ArticleSummaryBo articleSummaryBo = this.convertToSummary(article);
        ArticleDetailBo articleDetailBo = new ArticleDetailBo();
        articleDetailBo.setSummary(articleSummaryBo);
        ArticleContent articleContent = this.articleContentDao.getById(article.getContent().getId());
        if (articleContent == null) {
            return articleDetailBo;
        }
        articleDetailBo.setContent(articleContent.getContent());
        articleDetailBo.setContentId(articleContent.getId());
        return articleDetailBo;
    }
}
