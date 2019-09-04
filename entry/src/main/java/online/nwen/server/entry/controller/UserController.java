package online.nwen.server.entry.controller;

import online.nwen.server.bo.UserSummaryBo;
import online.nwen.server.service.api.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    private IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/summary/{userId}")
    UserSummaryBo getUserSummary(@PathVariable("userId") Long userId) {
        return this.userService.getUserSummary(userId);
    }
}
