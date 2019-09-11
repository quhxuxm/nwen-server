package online.nwen.server.entry.controller.security;

import online.nwen.server.bo.CreateOrUpdateAnthologyBookmarkResponseBo;
import online.nwen.server.entry.controller.SecurityApiController;
import online.nwen.server.service.api.IAnthologyBookmarkService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SecurityApiController
class SecurityAnthologyBookmarkController {
    private IAnthologyBookmarkService anthologyBookmarkService;

    SecurityAnthologyBookmarkController(IAnthologyBookmarkService anthologyBookmarkService) {
        this.anthologyBookmarkService = anthologyBookmarkService;
    }

    @PostMapping(path = "/bookmark/{anthologyId}")
    CreateOrUpdateAnthologyBookmarkResponseBo bookmark(@PathVariable("anthologyId") Long anthologyId, @RequestParam(value = "articleId", required = false) Long articleId) {
        return this.anthologyBookmarkService.bookmarkAnthology(anthologyId, articleId);
    }
}
