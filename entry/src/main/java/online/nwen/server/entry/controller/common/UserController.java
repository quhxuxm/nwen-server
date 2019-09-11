package online.nwen.server.entry.controller.common;

import online.nwen.server.bo.UserSummaryBo;
import online.nwen.server.entry.controller.CommonApiController;
import online.nwen.server.service.api.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CommonApiController
public class UserController {
    private IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/user/summary/{userId}")
    UserSummaryBo getUserSummary(@PathVariable("userId") Long userId) {
        return this.userService.getUserSummary(userId);
    }
}
