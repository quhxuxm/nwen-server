package online.nwen.server.entry.controller.common;

import online.nwen.server.bo.AnthologyBookmarkByAnthologyBo;
import online.nwen.server.bo.AnthologyBookmarkByUserBo;
import online.nwen.server.entry.controller.CommonApiController;
import online.nwen.server.service.api.IAnthologyBookmarkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CommonApiController
class AnthologyBookmarkController {
    private IAnthologyBookmarkService anthologyBookmarkService;

    AnthologyBookmarkController(IAnthologyBookmarkService anthologyBookmarkService) {
        this.anthologyBookmarkService = anthologyBookmarkService;
    }

    @GetMapping(path = "/bookmark/user/{userId}")
    Page<AnthologyBookmarkByUserBo> getAnthologyBookmarksOfUser(@PathVariable(value = "userId") Long userId, Pageable pageable) {
        return this.anthologyBookmarkService.getAnthologyBookmarksOfUser(userId, pageable);
    }

    @GetMapping(path = "/bookmark/anthology/{anthologyId}")
    Page<AnthologyBookmarkByAnthologyBo> getAnthologyBookmarksOfAnthology(@PathVariable(value = "anthologyId") Long anthologyId, Pageable pageable) {
        return this.anthologyBookmarkService.getAnthologyBookmarksOfAnthology(anthologyId, pageable);
    }
}
