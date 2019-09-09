package online.nwen.server.service.api;

import online.nwen.server.bo.AnthologyBookmarkByAnthologyBo;
import online.nwen.server.bo.AnthologyBookmarkByUserBo;
import online.nwen.server.bo.CreateOrUpdateAnthologyBookmarkResponseBo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAnthologyBookmarkService {
    CreateOrUpdateAnthologyBookmarkResponseBo bookmarkAnthology(Long anthologyId, Long lastReadArticleId);

    Page<AnthologyBookmarkByUserBo> getAnthologyBookmarksOfUser(Long userId, Pageable pageable);

    Page<AnthologyBookmarkByAnthologyBo> getAnthologyBookmarksOfAnthology(Long anthologyId, Pageable pageable);
}
