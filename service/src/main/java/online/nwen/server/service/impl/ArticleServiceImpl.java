package online.nwen.server.service.impl;

import online.nwen.server.bo.*;
import online.nwen.server.common.ServerConfiguration;
import online.nwen.server.common.constant.IConstant;
import online.nwen.server.dao.api.IAnthologyDao;
import online.nwen.server.dao.api.IArticleDao;
import online.nwen.server.dao.api.IUserDao;
import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Article;
import online.nwen.server.domain.User;
import online.nwen.server.service.api.IAnthologyService;
import online.nwen.server.service.api.IArticleService;
import online.nwen.server.service.api.ILocaleService;
import online.nwen.server.service.exception.ServiceException;
import org.springframework.context.MessageSource;
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

    ArticleServiceImpl(IArticleDao articleDao, IAnthologyDao anthologyDao, IUserDao userDao,
                       ServerConfiguration serverConfiguration, IAnthologyService anthologyService, MessageSource messageSource,
                       ILocaleService localeService) {
        this.articleDao = articleDao;
        this.anthologyDao = anthologyDao;
        this.userDao = userDao;
        this.serverConfiguration = serverConfiguration;
        this.anthologyService = anthologyService;
        this.messageSource = messageSource;
        this.localeService = localeService;
    }

    @Override
    public CreateArticleResponseBo create(SecurityContextBo securityContextBo, CreateArticleRequestBo createArticleRequestBo) {
        if (StringUtils.isEmpty(createArticleRequestBo.getTitle())) {
            throw new ServiceException(ResponseCode.ARTICLE_TITLE_IS_EMPTY);
        }
        if (createArticleRequestBo.getTitle().length() > this.serverConfiguration.getArticleTitleMaxLength()) {
            throw new ServiceException(ResponseCode.ARTICLE_TITLE_IS_TOO_LONG);
        }
        if (createArticleRequestBo.getSummary().length() > this.serverConfiguration.getArticleSummaryMaxLength()) {
            throw new ServiceException(ResponseCode.ARTICLE_SUMMARY_IS_TOO_LONG);
        }
        User author = this.userDao.getByUsername(securityContextBo.getUsername());
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
                createAnthologyRequestBo.setSummary(
                        this.messageSource.getMessage(IConstant.MessageKey.ANTHOLOGY_DEFAULT_SUMMARY_MESSAGE_KEY, null, this.localeService.getLocaleFromCurrentThread()));
                CreateAnthologyResponseBo createAnthologyResponseBo = this.anthologyService.create(securityContextBo, createAnthologyRequestBo);
                anthology = this.anthologyDao.getById(createAnthologyResponseBo.getAnthologyId());
            }
        }
        Article article = new Article();
        article.setTitle(createArticleRequestBo.getTitle());
        article.setContent(createArticleRequestBo.getContent());
        article.setSummary(createArticleRequestBo.getSummary());
        article.setCreateTime(new Date());
        article.setAnthology(anthology);
        int articleIndex = this.articleDao.countArticleNumberInAnthology(anthology);
        article.setIndexInAnthology(articleIndex);
        article = this.articleDao.save(article);
        CreateArticleResponseBo result = new CreateArticleResponseBo();
        result.setArticleId(article.getId());
        return result;
    }
}
