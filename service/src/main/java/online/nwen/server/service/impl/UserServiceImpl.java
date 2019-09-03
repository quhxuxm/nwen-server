package online.nwen.server.service.impl;

import online.nwen.server.bo.UserSummaryBo;
import online.nwen.server.domain.User;
import online.nwen.server.service.api.IUserService;
import org.springframework.stereotype.Service;

@Service
class UserServiceImpl implements IUserService {
    @Override
    public UserSummaryBo convertToSummary(User user) {
        UserSummaryBo userSummaryBo = new UserSummaryBo();
        userSummaryBo.setAuthorId(user.getId());
        userSummaryBo.setNickname(user.getNickname());
        return userSummaryBo;
    }
}
