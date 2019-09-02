package online.nwen.server.service.impl;

import online.nwen.server.bo.RegisterRequestBo;
import online.nwen.server.bo.RegisterResponseBo;
import online.nwen.server.bo.ResponseCode;
import online.nwen.server.common.ServerConfiguration;
import online.nwen.server.dao.api.IUserDao;
import online.nwen.server.domain.User;
import online.nwen.server.service.api.IRegisterService;
import online.nwen.server.service.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
class RegisterServiceImpl implements IRegisterService {
    private ServerConfiguration serverConfiguration;
    private IUserDao userDao;

    RegisterServiceImpl(ServerConfiguration serverConfiguration, IUserDao userDao) {
        this.serverConfiguration = serverConfiguration;
        this.userDao = userDao;
    }

    @Override
    public RegisterResponseBo register(RegisterRequestBo registerRequestBo) {
        if (StringUtils.isEmpty(registerRequestBo.getUsername())) {
            throw new ServiceException(ResponseCode.REGISTER_USERNAME_EMPTY);
        }
        if (!registerRequestBo.getUsername().matches(this.serverConfiguration.getUsernameFormat())) {
            throw new ServiceException(ResponseCode.REGISTER_USERNAME_FORMAT_ERROR);
        }
        if (StringUtils.isEmpty(registerRequestBo.getPassword())) {
            throw new ServiceException(ResponseCode.REGISTER_PASSWORD_EMPTY);
        }
        if (!registerRequestBo.getPassword().matches(this.serverConfiguration.getPasswordFormat())) {
            throw new ServiceException(ResponseCode.REGISTER_PASSWORD_FORMAT_ERROR);
        }
        if (StringUtils.isEmpty(registerRequestBo.getNickname())) {
            throw new ServiceException(ResponseCode.REGISTER_NICKNAME_EMPTY);
        }
        if (!registerRequestBo.getNickname().matches(this.serverConfiguration.getNicknameFormat())) {
            throw new ServiceException(ResponseCode.REGISTER_NICKNAME_FORMAT_ERROR);
        }
        if (this.userDao.getByUsername(registerRequestBo.getUsername()) != null) {
            throw new ServiceException(ResponseCode.REGISTER_USERNAME_EXIST);
        }
        if (this.userDao.getByNickname(registerRequestBo.getNickname()) != null) {
            throw new ServiceException(ResponseCode.REGISTER_NICKNAME_EXIST);
        }
        User user = new User();
        user.setUsername(registerRequestBo.getUsername());
        user.setPassword(registerRequestBo.getPassword());
        user.setNickname(registerRequestBo.getNickname());
        user.setRegisteredTime(new Date());
        user = this.userDao.save(user);
        RegisterResponseBo result = new RegisterResponseBo();
        result.setId(user.getId());
        return result;
    }
}
