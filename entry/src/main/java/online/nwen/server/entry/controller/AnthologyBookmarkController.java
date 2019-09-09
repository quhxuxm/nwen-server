package online.nwen.server.entry.controller;

import online.nwen.server.bo.AnthologyBookmarkByAnthologyBo;
import online.nwen.server.bo.AnthologyBookmarkByUserBo;
import online.nwen.server.bo.CreateOrUpdateAnthologyBookmarkResponseBo;
import online.nwen.server.service.api.IAnthologyBookmarkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
class AnthologyBookmarkController {
    private IAnthologyBookmarkService anthologyBookmarkService;

    AnthologyBookmarkController(IAnthologyBookmarkService anthologyBookmarkService) {
        this.anthologyBookmarkService = anthologyBookmarkService;
    }

    @PatchMapping("/security/bookmark/{anthologyId}")
    CreateOrUpdateAnthologyBookmarkResponseBo bookmark(@PathVariable("anthologyId") Long anthologyId, @RequestParam(value = "articleId", required = false) Long articleId) {
        return this.anthologyBookmarkService.bookmarkAnthology(anthologyId, articleId);
    }

    @GetMapping("/bookmark/user/{userId}")
    Page<AnthologyBookmarkByUserBo> getAnthologyBookmarksOfUser(@PathVariable(value = "userId") Long userId, Pageable pageable) {
        return this.anthologyBookmarkService.getAnthologyBookmarksOfUser(userId, pageable);
    }

    @GetMapping("/bookmark/anthology/{anthologyId}")
    Page<AnthologyBookmarkByAnthologyBo> getAnthologyBookmarksOfAnthology(@PathVariable(value = "anthologyId") Long anthologyId, Pageable pageable) {
        return this.anthologyBookmarkService.getAnthologyBookmarksOfAnthology(anthologyId, pageable);
    }
}
