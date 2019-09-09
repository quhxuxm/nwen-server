package online.nwen.server.service.api;

import online.nwen.server.bo.AnthologyBookmarkBo;
import online.nwen.server.bo.CreateOrUpdateAnthologyBookmarkResponseBo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAnthologyBookmarkService {
    CreateOrUpdateAnthologyBookmarkResponseBo bookmarkAnthology(Long anthologyId, Long lastReadArticleId);

    Page<AnthologyBookmarkBo> getAnthologyBookmarksOfUser(Long userId, Pageable pageable);

    Page<AnthologyBookmarkBo> getAnthologyBookmarksOfAnthology(Long anthologyId, Pageable pageable);
}
