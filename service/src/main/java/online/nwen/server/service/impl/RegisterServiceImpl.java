package online.nwen.server.service.impl;

import online.nwen.server.bo.RegisterRequestBo;
import online.nwen.server.bo.RegisterResponseBo;
import online.nwen.server.bo.ResponseCode;
import online.nwen.server.common.ServerConfiguration;
import online.nwen.server.dao.api.IUserDao;
import online.nwen.server.domain.Label;
import online.nwen.server.domain.User;
import online.nwen.server.service.api.ILabelService;
import online.nwen.server.service.api.IRegisterService;
import online.nwen.server.service.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
class RegisterServiceImpl implements IRegisterService {
    private ServerConfiguration serverConfiguration;
    private IUserDao userDao;
    private ILabelService labelService;
    private Pattern usernamePattern;
    private Pattern nicknamePattern;
    private Pattern passwordPattern;

    RegisterServiceImpl(ServerConfiguration serverConfiguration, IUserDao userDao, ILabelService labelService) {
        this.serverConfiguration = serverConfiguration;
        this.userDao = userDao;
        this.labelService = labelService;
        this.usernamePattern = Pattern.compile(this.serverConfiguration.getUsernameFormat());
        this.nicknamePattern = Pattern.compile(this.serverConfiguration.getNicknameFormat());
        this.passwordPattern = Pattern.compile(this.serverConfiguration.getPasswordFormat());
    }

    @Override
    public RegisterResponseBo register(RegisterRequestBo registerRequestBo) {
        if (StringUtils.isEmpty(registerRequestBo.getUsername())) {
            throw new ServiceException(ResponseCode.REGISTER_USERNAME_EMPTY);
        }
        Matcher usernamePatternMatcher = this.usernamePattern.matcher(registerRequestBo.getUsername());
        if (!usernamePatternMatcher.matches()) {
            throw new ServiceException(ResponseCode.REGISTER_USERNAME_FORMAT_ERROR);
        }
        if (StringUtils.isEmpty(registerRequestBo.getPassword())) {
            throw new ServiceException(ResponseCode.REGISTER_PASSWORD_EMPTY);
        }
        Matcher passwordPatternMatcher = this.passwordPattern.matcher(registerRequestBo.getPassword());
        if (!passwordPatternMatcher.matches()) {
            throw new ServiceException(ResponseCode.REGISTER_PASSWORD_FORMAT_ERROR);
        }
        if (StringUtils.isEmpty(registerRequestBo.getNickname())) {
            throw new ServiceException(ResponseCode.REGISTER_NICKNAME_EMPTY);
        }
        Matcher nicknamePatternMatcher = this.nicknamePattern.matcher(registerRequestBo.getNickname());
        if (!nicknamePatternMatcher.matches()) {
            throw new ServiceException(ResponseCode.REGISTER_NICKNAME_FORMAT_ERROR);
        }
        if (this.userDao.getByUsername(registerRequestBo.getUsername()) != null) {
            throw new ServiceException(ResponseCode.REGISTER_USERNAME_EXIST);
        }
        if (this.userDao.getByNickname(registerRequestBo.getNickname()) != null) {
            throw new ServiceException(ResponseCode.REGISTER_NICKNAME_EXIST);
        }
        final User user = new User();
        user.setUsername(registerRequestBo.getUsername());
        user.setPassword(registerRequestBo.getPassword());
        user.setNickname(registerRequestBo.getNickname());
        user.setRegisteredTime(new Date());
        registerRequestBo.getLabels().forEach(text -> {
            Label label = this.labelService.getAndCreateIfAbsent(text);
            if (label != null) {
                user.getLabels().add(label);
            }
        });
        this.userDao.save(user);
        RegisterResponseBo result = new RegisterResponseBo();
        result.setId(user.getId());
        return result;
    }
}
