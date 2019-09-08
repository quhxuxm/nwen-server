package online.nwen.server.entry.controller;

import online.nwen.server.bo.*;
import online.nwen.server.service.api.IAnthologyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api")
class AnthologyController {
    private IAnthologyService anthologyService;

    AnthologyController(IAnthologyService anthologyService) {
        this.anthologyService = anthologyService;
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
    AnthologyBookmarkBo bookmark(@PathVariable("anthologyId") Long anthologyId, @RequestParam(value = "articleId", required = false) Long articleId) {
        return this.anthologyService.bookmarkAnthology(anthologyId, articleId);
    }

    @GetMapping("/anthology/bookmark/{anthologyId}")
    Page<AnthologyBookmarkBo> getAnthologyBookmarksOfAuthor(@RequestParam(value = "userId", required = false) Long userId, Pageable pageable) {
        return this.anthologyService.getAnthologyBookmarksOfAuthor(userId, pageable);
    }
}
