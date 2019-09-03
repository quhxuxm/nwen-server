package online.nwen.server.service.impl;

import online.nwen.server.bo.*;
import online.nwen.server.common.ServerConfiguration;
import online.nwen.server.dao.api.IAnthologyDao;
import online.nwen.server.dao.api.IArticleDao;
import online.nwen.server.dao.api.IUserDao;
import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.User;
import online.nwen.server.service.api.IAnthologyService;
import online.nwen.server.service.api.ISecurityService;
import online.nwen.server.service.api.IUserService;
import online.nwen.server.service.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
class AnthologyServiceImpl implements IAnthologyService {
    private IAnthologyDao anthologyDao;
    private IArticleDao articleDao;
    private IUserDao userDao;
    private ServerConfiguration serverConfiguration;
    private IUserService userService;
    private ISecurityService securityService;

    AnthologyServiceImpl(IAnthologyDao anthologyDao, IArticleDao articleDao, IUserDao userDao, ServerConfiguration serverConfiguration, IUserService userService, ISecurityService securityService) {
        this.anthologyDao = anthologyDao;
        this.articleDao = articleDao;
        this.userDao = userDao;
        this.serverConfiguration = serverConfiguration;
        this.userService = userService;
        this.securityService = securityService;
    }

    @Override
    public CreateAnthologyResponseBo create(CreateAnthologyRequestBo createAnthologyRequestBo) {
        SecurityContextBo securityContextBo = this.securityService.checkAndGetSecurityContextFromCurrentThread();
        if (StringUtils.isEmpty(createAnthologyRequestBo.getTitle())) {
            throw new ServiceException(ResponseCode.ANTHOLOGY_TITLE_IS_EMPTY);
        }
        if (createAnthologyRequestBo.getTitle().length() > this.serverConfiguration.getAnthologyTitleMaxLength()) {
            throw new ServiceException(ResponseCode.ANTHOLOGY_TITLE_IS_TOO_LONG);
        }
        if (createAnthologyRequestBo.getSummary().length() > this.serverConfiguration.getAnthologySummaryMaxLength()) {
            throw new ServiceException(ResponseCode.ANTHOLOGY_SUMMARY_IS_TOO_LONG);
        }
        Anthology anthology = new Anthology();
        anthology.setCreateTime(new Date());
        anthology.setSummary(createAnthologyRequestBo.getSummary());
        anthology.setTitle(createAnthologyRequestBo.getTitle());
        String authorUsername = securityContextBo.getUsername();
        User author = this.userDao.getByUsername(authorUsername);
        if (author == null) {
            throw new ServiceException(ResponseCode.USER_NOT_EXIST);
        }
        anthology.setAuthor(author);
        anthology = this.anthologyDao.save(anthology);
        if (createAnthologyRequestBo.isAsDefault()) {
            author.setDefaultAnthology(anthology);
            this.userDao.save(author);
        }
        CreateAnthologyResponseBo result = new CreateAnthologyResponseBo();
        result.setAnthologyId(anthology.getId());
        return result;
    }

    @Override
    public DeleteAnthologiesResponseBo deleteAll(DeleteAnthologiesRequestBo deleteAnthologiesRequestBo) {
        SecurityContextBo securityContextBo = this.securityService.checkAndGetSecurityContextFromCurrentThread();
        User currentUser = this.userDao.getByUsername(securityContextBo.getUsername());
        if (currentUser == null) {
            throw new ServiceException(ResponseCode.USER_NOT_EXIST);
        }
        DeleteAnthologiesResponseBo result = new DeleteAnthologiesResponseBo();
        deleteAnthologiesRequestBo.getAnthologyIds().forEach(id -> {
            Anthology anthology = this.anthologyDao.getById(id);
            if (anthology.getAuthor().getId().compareTo(currentUser.getId()) != 0) {
                return;
            }
            this.articleDao.getByAnthology(anthology, Pageable.unpaged()).forEach(article -> {
                this.articleDao.delete(article);
            });
            this.anthologyDao.delete(anthology);
            result.getAnthologyIds().add(anthology.getId());
        });
        return result;
    }

    @Override
    public AnthologySummaryBo convertToSummary(Anthology anthology) {
        AnthologySummaryBo anthologySummaryBo = new AnthologySummaryBo();
        anthologySummaryBo.setAnthologyId(anthology.getId());
        anthologySummaryBo.setCreateTime(anthology.getCreateTime());
        anthologySummaryBo.setUpdateTime(anthology.getUpdateTime());
        anthologySummaryBo.setSummary(anthology.getSummary());
        anthologySummaryBo.setTitle(anthology.getTitle());
        User author = this.userDao.getById(anthology.getAuthor().getId());
        anthologySummaryBo.setAuthor(this.userService.convertToSummary(author));
        return anthologySummaryBo;
    }

    @Override
    public Page<AnthologySummaryBo> getAnthologySummariesOfAuthor(Long authorId, Pageable pageable) {
        User author = this.userDao.getById(authorId);
        if (author == null) {
            throw new ServiceException(ResponseCode.USER_NOT_EXIST);
        }
        Page<Anthology> anthologies = this.anthologyDao.getByAuthor(author, pageable);
        return anthologies.map(this::convertToSummary);
    }
}
