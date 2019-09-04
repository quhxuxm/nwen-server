package online.nwen.server.entry.controller;

import online.nwen.server.bo.*;
import online.nwen.server.service.api.IAnthologyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
}
