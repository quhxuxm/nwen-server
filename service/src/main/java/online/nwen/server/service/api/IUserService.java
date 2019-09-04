package online.nwen.server.service.api;

import online.nwen.server.bo.UserSummaryBo;
import online.nwen.server.domain.User;

public interface IUserService {
    UserSummaryBo convertToSummary(User user);

    UserSummaryBo getUserSummary(Long userId);
}
