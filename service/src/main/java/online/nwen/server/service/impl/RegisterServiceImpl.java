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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
class RegisterServiceImpl implements IRegisterService {
    private static final Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);
    private ServerConfiguration serverConfiguration;
    private IUserDao userDao;
    private ILabelService labelService;
    private List<Pattern> usernamePatterns;
    private List<Pattern> nicknamePatterns;
    private List<Pattern> passwordPatterns;
    private Pattern nicknamePattern;
    private Pattern passwordPattern;

    RegisterServiceImpl(ServerConfiguration serverConfiguration, IUserDao userDao, ILabelService labelService) {
        this.serverConfiguration = serverConfiguration;
        this.userDao = userDao;
        this.labelService = labelService;
        this.usernamePatterns = new ArrayList<>();
        this.nicknamePatterns = new ArrayList<>();
        this.passwordPatterns = new ArrayList<>();
        this.initializePatterns(this.serverConfiguration.getUsernamePatterns(), this.usernamePatterns);
        this.initializePatterns(this.serverConfiguration.getNicknamePatterns(), this.nicknamePatterns);
        this.initializePatterns(this.serverConfiguration.getPasswordPatterns(), this.passwordPatterns);
    }

    private void initializePatterns(Resource patternsResource, List<Pattern> patterns) {
        this.usernamePatterns = new ArrayList<>();
        BufferedReader patternsReader = null;
        try {
            patternsReader = new BufferedReader(new InputStreamReader(patternsResource.getInputStream()));
            patterns.addAll(patternsReader.lines().map(Pattern::compile).collect(Collectors.toList()));
        } catch (IOException e) {
            logger.error("Fail to read username patterns resource because of exception.", e);
        } finally {
            if (patternsReader != null) {
                try {
                    patternsReader.close();
                } catch (IOException e) {
                    logger.error("Fail to close username patterns resource because of exception.", e);
                }
            }
        }
    }

    @Override
    public RegisterResponseBo register(RegisterRequestBo registerRequestBo) {
        if (StringUtils.isEmpty(registerRequestBo.getUsername())) {
            throw new ServiceException(ResponseCode.REGISTER_USERNAME_EMPTY);
        }
        this.usernamePatterns.forEach(pattern -> {
          Matcher currentMatcher=  pattern.matcher(registerRequestBo.getUsername());
          if(currentMatcher.matches()){
              return;
          }
        });
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
