package online.nwen.server.entry.controller;

import online.nwen.server.bo.*;
import online.nwen.server.service.api.IAnthologyBookmarkService;
import online.nwen.server.service.api.IAnthologyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api")
class AnthologyController {
    private IAnthologyService anthologyService;
    private IAnthologyBookmarkService anthologyBookmarkService;

    AnthologyController(IAnthologyService anthologyService, IAnthologyBookmarkService anthologyBookmarkService) {
        this.anthologyService = anthologyService;
        this.anthologyBookmarkService = anthologyBookmarkService;
    }

    @PostMapping("/security/anthology/create")
    CreateAnthologyResponseBo create(@RequestBody CreateAnthologyRequestBo createAnthologyRequestBo) {
        return this.anthologyService.create(createAnthologyRequestBo);
    }

    @PostMapping("/security/anthology/update/{anthologyId}")
    UpdateAnthologyResponseBo update(@PathVariable("anthologyId") Long anthologyId, @RequestBody UpdateAnthologyRequestBo updateAnthologyRequestBo) {
        updateAnthologyRequestBo.setAnthologyId(anthologyId);
        return this.anthologyService.update(updateAnthologyRequestBo);
    }

    @GetMapping("/anthology/summaries/author/{authorId}")
    Page<AnthologySummaryBo> getAnthologySummariesOfAuthor(@PathVariable("authorId") Long authorId, Pageable pageable) {
        return this.anthologyService.getAnthologySummariesOfAuthor(authorId, pageable);
    }

    @GetMapping("/anthology/summaries/labels/{labels}")
    Page<AnthologySummaryBo> getAnthologySummariesWithLabels(@PathVariable("labels") Set<String> labels, Pageable pageable) {
        return this.anthologyService.getAnthologySummariesWithLabels(labels, pageable);
    }

    @PatchMapping("/security/anthology/bookmark/{anthologyId}")
    CreateOrUpdateAnthologyBookmarkResponseBo bookmark(@PathVariable("anthologyId") Long anthologyId, @RequestParam(value = "articleId", required = false) Long articleId) {
        return this.anthologyBookmarkService.bookmarkAnthology(anthologyId, articleId);
    }

    @GetMapping("/anthology/bookmark/byUser/{userId}")
    Page<AnthologyBookmarkBo> getAnthologyBookmarksOfUser(@PathVariable(value = "userId") Long userId, Pageable pageable) {
        return this.anthologyBookmarkService.getAnthologyBookmarksOfUser(userId, pageable);
    }

    @GetMapping("/anthology/bookmark/byAnthology/{anthologyId}")
    Page<AnthologyBookmarkBo> getAnthologyBookmarksOfAnthology(@PathVariable(value = "anthologyId") Long anthologyId, Pageable pageable) {
        return this.anthologyBookmarkService.getAnthologyBookmarksOfAnthology(anthologyId, pageable);
    }
}
