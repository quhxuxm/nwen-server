package online.nwen.server.service.impl;

import online.nwen.server.bo.*;
import online.nwen.server.common.ServerConfiguration;
import online.nwen.server.dao.api.IAnthologyDao;
import online.nwen.server.dao.api.IArticleDao;
import online.nwen.server.dao.api.IUserDao;
import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Label;
import online.nwen.server.domain.User;
import online.nwen.server.service.api.IAnthologyService;
import online.nwen.server.service.api.ILabelService;
import online.nwen.server.service.api.ISecurityService;
import online.nwen.server.service.api.IUserService;
import online.nwen.server.service.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
class AnthologyServiceImpl implements IAnthologyService {
    private IAnthologyDao anthologyDao;
    private IArticleDao articleDao;
    private IUserDao userDao;
    private ServerConfiguration serverConfiguration;
    private IUserService userService;
    private ISecurityService securityService;
    private ILabelService labelService;

    AnthologyServiceImpl(IAnthologyDao anthologyDao, IArticleDao articleDao, IUserDao userDao, ServerConfiguration serverConfiguration, IUserService userService,
                         ISecurityService securityService, ILabelService labelService) {
        this.anthologyDao = anthologyDao;
        this.articleDao = articleDao;
        this.userDao = userDao;
        this.serverConfiguration = serverConfiguration;
        this.userService = userService;
        this.securityService = securityService;
        this.labelService = labelService;
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
        if (createAnthologyRequestBo.getDescription().length() > this.serverConfiguration.getAnthologyDescriptionMaxLength()) {
            throw new ServiceException(ResponseCode.ANTHOLOGY_SUMMARY_IS_TOO_LONG);
        }
        final Anthology anthology = new Anthology();
        anthology.setCreateTime(new Date());
        anthology.setDescription(createAnthologyRequestBo.getDescription());
        anthology.setTitle(createAnthologyRequestBo.getTitle());
        String authorUsername = securityContextBo.getUsername();
        User author = this.userDao.getByUsername(authorUsername);
        if (author == null) {
            throw new ServiceException(ResponseCode.USER_NOT_EXIST);
        }
        anthology.setAuthor(author);
        createAnthologyRequestBo.getLabels().forEach(text -> {
            Label label = this.labelService.getAndCreateIfAbsent(text);
            if (label != null) {
                anthology.getLabels().add(label);
            }
        });
        this.anthologyDao.save(anthology);
        if (createAnthologyRequestBo.isAsDefault()) {
            author.setDefaultAnthology(anthology);
            this.userDao.save(author);
        }
        CreateAnthologyResponseBo result = new CreateAnthologyResponseBo();
        result.setAnthologyId(anthology.getId());
        return result;
    }

    @Override
    public UpdateAnthologyResponseBo update(UpdateAnthologyRequestBo updateAnthologyRequestBo) {
        SecurityContextBo securityContextBo = this.securityService.checkAndGetSecurityContextFromCurrentThread();
        if (updateAnthologyRequestBo.getAnthologyId() == null) {
            throw new ServiceException(ResponseCode.ANTHOLOGY_ID_IS_EMPTY);
        }
        if (StringUtils.isEmpty(updateAnthologyRequestBo.getTitle())) {
            throw new ServiceException(ResponseCode.ANTHOLOGY_TITLE_IS_EMPTY);
        }
        if (updateAnthologyRequestBo.getTitle().length() > this.serverConfiguration.getAnthologyTitleMaxLength()) {
            throw new ServiceException(ResponseCode.ANTHOLOGY_TITLE_IS_TOO_LONG);
        }
        if (updateAnthologyRequestBo.getDescription().length() > this.serverConfiguration.getAnthologyDescriptionMaxLength()) {
            throw new ServiceException(ResponseCode.ANTHOLOGY_SUMMARY_IS_TOO_LONG);
        }
        User currentUser = this.userDao.getByUsername(securityContextBo.getUsername());
        if (currentUser == null) {
            throw new ServiceException(ResponseCode.USER_NOT_EXIST);
        }
        final Anthology anthology = this.anthologyDao.getById(updateAnthologyRequestBo.getAnthologyId());
        if (!anthology.getAuthor().getId().equals(currentUser.getId())) {
            throw new ServiceException(ResponseCode.ANTHOLOGY_NOT_BELONG_TO_AUTHOR);
        }
        anthology.setUpdateTime(new Date());
        anthology.setDescription(updateAnthologyRequestBo.getDescription());
        anthology.setTitle(updateAnthologyRequestBo.getTitle());
        if (updateAnthologyRequestBo.getLabels() != null) {
            Set<Label> updateLabels = new HashSet<>();
            updateAnthologyRequestBo.getLabels().forEach(text -> {
                Label label = this.labelService.getAndCreateIfAbsent(text);
                if (label != null) {
                    updateLabels.add(label);
                }
            });
            anthology.setLabels(updateLabels);
        }
        this.anthologyDao.save(anthology);
        if (updateAnthologyRequestBo.isAsDefault()) {
            currentUser.setDefaultAnthology(anthology);
            this.userDao.save(currentUser);
        }
        UpdateAnthologyResponseBo result = new UpdateAnthologyResponseBo();
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
        anthologySummaryBo.setDescription(anthology.getDescription());
        anthologySummaryBo.setTitle(anthology.getTitle());
        User author = this.userDao.getById(anthology.getAuthor().getId());
        anthologySummaryBo.setAuthor(this.userService.convertToSummary(author));
        anthology.getLabels().forEach(label -> {
            anthologySummaryBo.getLabels().add(this.labelService.convert(label));
        });
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
