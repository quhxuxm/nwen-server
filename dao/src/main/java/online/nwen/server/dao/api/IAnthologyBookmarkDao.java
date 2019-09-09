package online.nwen.server.dao.api;

import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.AnthologyBookmark;
import online.nwen.server.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAnthologyBookmarkDao {
    AnthologyBookmark save(AnthologyBookmark anthologyBookmark);

    Page<AnthologyBookmark> getByUser(User user, Pageable pageable);

    AnthologyBookmark getByUserAndAnthology(User user, Anthology anthology);

    Page<AnthologyBookmark> getByAnthology(Anthology anthology, Pageable pageable);
}
