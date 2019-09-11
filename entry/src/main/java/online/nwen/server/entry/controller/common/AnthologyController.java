package online.nwen.server.entry.controller.common;

import online.nwen.server.bo.AnthologySummaryBo;
import online.nwen.server.entry.controller.CommonApi;
import online.nwen.server.service.api.IAnthologyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@CommonApi
class AnthologyController {
    private IAnthologyService anthologyService;

    AnthologyController(IAnthologyService anthologyService) {
        this.anthologyService = anthologyService;
    }

    @GetMapping(path = "/anthology/summaries/author/{authorId}")
    Page<AnthologySummaryBo> getAnthologySummariesOfAuthor(@PathVariable("authorId") Long authorId, Pageable pageable) {
        return this.anthologyService.getAnthologySummariesOfAuthor(authorId, pageable);
    }

    @GetMapping(path = "/anthology/summaries/labels/{labels}")
    Page<AnthologySummaryBo> getAnthologySummariesWithLabels(@PathVariable("labels") Set<String> labels, Pageable pageable) {
        return this.anthologyService.getAnthologySummariesWithLabels(labels, pageable);
    }

    @GetMapping(path = "/anthology/summaries/category/{categoryId}")
    Page<AnthologySummaryBo> getAnthologySummariesWithCategory(@PathVariable("categoryId") Long categoryId, Pageable pageable) {
        return this.anthologyService.getAnthologySummariesWithCategory(categoryId, pageable);
    }
}
