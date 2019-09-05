package online.nwen.server.service.api;

import online.nwen.server.bo.*;
import online.nwen.server.domain.Anthology;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface IAnthologyService {
    CreateAnthologyResponseBo create(CreateAnthologyRequestBo createAnthologyRequestBo);

    UpdateAnthologyResponseBo update(UpdateAnthologyRequestBo updateAnthologyRequestBo);

    DeleteAnthologiesResponseBo deleteAll(DeleteAnthologiesRequestBo deleteAnthologiesRequestBo);

    Page<AnthologySummaryBo> getAnthologySummariesOfAuthor(Long authorId, Pageable pageable);

    AnthologySummaryBo convertToSummary(Anthology anthology);

    Page<AnthologySummaryBo> getAnthologySummariesWithLabels(Set<String> labels, Pageable pageable);

}
