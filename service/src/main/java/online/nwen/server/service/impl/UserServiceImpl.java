package online.nwen.server.service.impl;

import online.nwen.server.bo.ResponseCode;
import online.nwen.server.bo.UserSummaryBo;
import online.nwen.server.dao.api.IUserDao;
import online.nwen.server.domain.User;
import online.nwen.server.service.api.ILabelService;
import online.nwen.server.service.api.IUserService;
import online.nwen.server.service.exception.ServiceException;
import org.springframework.stereotype.Service;

@Service
class UserServiceImpl implements IUserService {
    private IUserDao userDao;
    private ILabelService labelService;

    UserServiceImpl(IUserDao userDao, ILabelService labelService) {
        this.userDao = userDao;
        this.labelService = labelService;
    }

    @Override
    public UserSummaryBo convertToSummary(User user) {
        UserSummaryBo userSummaryBo = new UserSummaryBo();
        userSummaryBo.setAuthorId(user.getId());
        userSummaryBo.setNickname(user.getNickname());
        user.getLabels().forEach(label -> {
            userSummaryBo.getLabels().add(this.labelService.convert(label));
        });
        return userSummaryBo;
    }

    @Override
    public UserSummaryBo getUserSummary(Long userId) {
        User user = this.userDao.getById(userId);
        if (user == null) {
            throw new ServiceException(ResponseCode.USER_NOT_EXIST);
        }
        return this.convertToSummary(user);
    }
}
