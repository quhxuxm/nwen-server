package online.nwen.server.service.impl;

import online.nwen.server.bo.*;
import online.nwen.server.dao.api.IAnthologyBookmarkDao;
import online.nwen.server.dao.api.IAnthologyDao;
import online.nwen.server.dao.api.IArticleDao;
import online.nwen.server.dao.api.IUserDao;
import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.AnthologyBookmark;
import online.nwen.server.domain.Article;
import online.nwen.server.domain.User;
import online.nwen.server.service.api.*;
import online.nwen.server.service.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
class AnthologyBookmarkServiceImpl implements IAnthologyBookmarkService {
    private IAnthologyBookmarkDao anthologyBookmarkDao;
    private ISecurityService securityService;
    private IAnthologyDao anthologyDao;
    private IArticleDao articleDao;
    private IUserDao userDao;
    private IAnthologyService anthologyService;
    private IUserService userService;
    private ILabelService labelService;

    AnthologyBookmarkServiceImpl(IAnthologyBookmarkDao anthologyBookmarkDao, ISecurityService securityService,
                                 IAnthologyDao anthologyDao, IArticleDao articleDao, IUserDao userDao, IAnthologyService anthologyService,
                                 IUserService userService, ILabelService labelService) {
        this.anthologyBookmarkDao = anthologyBookmarkDao;
        this.securityService = securityService;
        this.anthologyDao = anthologyDao;
        this.articleDao = articleDao;
        this.userDao = userDao;
        this.anthologyService = anthologyService;
        this.userService = userService;
        this.labelService = labelService;
    }

    @Override
    public CreateOrUpdateAnthologyBookmarkResponseBo bookmarkAnthology(Long anthologyId, Long lastReadArticleId) {
        SecurityContextBo securityContextBo = this.securityService.checkAndGetSecurityContextFromCurrentThread();
        User currentUser = this.userDao.getByUsername(securityContextBo.getUsername());
        if (currentUser == null) {
            throw new ServiceException(ResponseCode.USER_NOT_EXIST);
        }
        Anthology anthology = this.anthologyDao.getById(anthologyId);
        if (anthology == null) {
            throw new ServiceException(ResponseCode.ANTHOLOGY_NOT_EXIST);
        }
        AnthologyBookmark existingBookmark = this.anthologyBookmarkDao.getByUserAndAnthology(currentUser, anthology);
        if (existingBookmark == null) {
            existingBookmark = new AnthologyBookmark();
            existingBookmark.setCreateTime(new Date());
            existingBookmark.setAnthology(anthology);
            existingBookmark.setUser(currentUser);
        } else {
            existingBookmark.setUpdateTime(new Date());
        }
        if (lastReadArticleId != null) {
            Article article = this.articleDao.getById(lastReadArticleId);
            if (article != null) {
                if (article.getAnthology().getId().compareTo(anthology.getId()) != 0) {
                    throw new ServiceException(ResponseCode.ARTICLE_NOT_BELONG_TO_ANTHOLOGY);
                }
                existingBookmark.setLastReadArticle(article);
            }
        }
        this.anthologyBookmarkDao.save(existingBookmark);
        CreateOrUpdateAnthologyBookmarkResponseBo result = new CreateOrUpdateAnthologyBookmarkResponseBo();
        result.setAnthologyBookmarkId(existingBookmark.getId());
        result.setCreateTime(existingBookmark.getCreateTime());
        result.setUpdateTime(existingBookmark.getUpdateTime());
        return result;
    }

    @Override
    public Page<AnthologyBookmarkByUserBo> getAnthologyBookmarksOfUser(Long userId, Pageable pageable) {
        User targetUser = null;
        if (userId == null) {
            SecurityContextBo securityContextBo = this.securityService.checkAndGetSecurityContextFromCurrentThread();
            targetUser = this.userDao.getByUsername(securityContextBo.getUsername());
        } else {
            targetUser = this.userDao.getById(userId);
        }
        if (targetUser == null) {
            throw new ServiceException(ResponseCode.USER_NOT_EXIST);
        }
        Page<AnthologyBookmark> anthologyBookmarks = this.anthologyBookmarkDao.getByUser(targetUser, pageable);
        return anthologyBookmarks.map(this::convertAnthologyBookmarkByUser);
    }

    @Override
    public Page<AnthologyBookmarkByAnthologyBo> getAnthologyBookmarksOfAnthology(Long anthologyId, Pageable pageable) {
        Anthology anthology = this.anthologyDao.getById(anthologyId);
        if (anthology == null) {
            throw new ServiceException(ResponseCode.ANTHOLOGY_NOT_EXIST);
        }
        Page<AnthologyBookmark> anthologyBookmarks = this.anthologyBookmarkDao.getByAnthology(anthology, pageable);
        return anthologyBookmarks.map(this::convertAnthologyBookmarkByAnthology);
    }

    private AnthologyBookmarkByUserBo convertAnthologyBookmarkByUser(AnthologyBookmark anthologyBookmark) {
        AnthologyBookmarkByUserBo result = new AnthologyBookmarkByUserBo();
        result.setAnthologyBookmarkId(anthologyBookmark.getId());
        Anthology anthology = this.anthologyDao.getById(anthologyBookmark.getAnthology().getId());
        result.setAnthologySummary(this.anthologyService.convertToSummary(anthology));
        result.setCreateTime(anthologyBookmark.getCreateTime());
        Article lastReadArticle = null;
        if (anthologyBookmark.getLastReadArticle() != null) {
            lastReadArticle = this.articleDao.getById(anthologyBookmark.getLastReadArticle().getId());
            if (lastReadArticle != null) {
                LastReadArticleInBookmarkBo lastReadArticleInBookmarkBo = new LastReadArticleInBookmarkBo();
                lastReadArticleInBookmarkBo.setArticleId(lastReadArticle.getId());
                lastReadArticleInBookmarkBo.setArticleDescription(lastReadArticle.getDescription());
                lastReadArticleInBookmarkBo.setArticleTitle(lastReadArticle.getTitle());
                lastReadArticle.getLabels().forEach(label -> {
                    lastReadArticleInBookmarkBo.getLabels().add(this.labelService.convert(label));
                });
                result.setLastReadArticle(lastReadArticleInBookmarkBo);
            }
        }
        return result;
    }

    private AnthologyBookmarkByAnthologyBo convertAnthologyBookmarkByAnthology(AnthologyBookmark anthologyBookmark) {
        AnthologyBookmarkByAnthologyBo result = new AnthologyBookmarkByAnthologyBo();
        result.setAnthologyBookmarkId(anthologyBookmark.getId());
        User author = this.userDao.getById(anthologyBookmark.getUser().getId());
        result.setAuthorSummary(this.userService.convertToSummary(author));
        result.setCreateTime(anthologyBookmark.getCreateTime());
        Article lastReadArticle = null;
        if (anthologyBookmark.getLastReadArticle() != null) {
            lastReadArticle = this.articleDao.getById(anthologyBookmark.getLastReadArticle().getId());
            if (lastReadArticle != null) {
                LastReadArticleInBookmarkBo lastReadArticleInBookmarkBo = new LastReadArticleInBookmarkBo();
                lastReadArticleInBookmarkBo.setArticleId(lastReadArticle.getId());
                lastReadArticleInBookmarkBo.setArticleDescription(lastReadArticle.getDescription());
                lastReadArticleInBookmarkBo.setArticleTitle(lastReadArticle.getTitle());
                lastReadArticle.getLabels().forEach(label -> {
                    lastReadArticleInBookmarkBo.getLabels().add(this.labelService.convert(label));
                });
                result.setLastReadArticle(lastReadArticleInBookmarkBo);
            }
        }
        return result;
    }
}
