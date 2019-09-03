package online.nwen.server.service.impl;

import online.nwen.server.bo.CreateAnthologyRequestBo;
import online.nwen.server.bo.CreateAnthologyResponseBo;
import online.nwen.server.bo.ResponseCode;
import online.nwen.server.bo.SecurityContextBo;
import online.nwen.server.common.ServerConfiguration;
import online.nwen.server.dao.api.IAnthologyDao;
import online.nwen.server.dao.api.IUserDao;
import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.User;
import online.nwen.server.service.api.IAnthologyService;
import online.nwen.server.service.api.ISecurityService;
import online.nwen.server.service.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
class AnthologyServiceImpl implements IAnthologyService {
    private IAnthologyDao anthologyDao;
    private IUserDao userDao;
    private ISecurityService securityService;
    private ServerConfiguration serverConfiguration;

    AnthologyServiceImpl(IAnthologyDao anthologyDao, IUserDao userDao, ISecurityService securityService, ServerConfiguration serverConfiguration) {
        this.anthologyDao = anthologyDao;
        this.userDao = userDao;
        this.securityService = securityService;
        this.serverConfiguration = serverConfiguration;
    }

    @Override
    public CreateAnthologyResponseBo create(String secureToken, CreateAnthologyRequestBo createAnthologyRequestBo) {
        if (StringUtils.isEmpty(secureToken)) {
            throw new ServiceException(ResponseCode.SECURITY_TOKEN_IS_EMPTY);
        }
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
        SecurityContextBo securityContextBo = this.securityService.parseJwtToken(secureToken);
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
}
