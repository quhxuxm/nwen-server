package online.nwen.server.dao.api;

import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.AnthologyComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAnthologyCommentDao {
    AnthologyComment save(AnthologyComment articleComment);

    AnthologyComment getById(Long id);

    Page<AnthologyComment> getByReplyTo(AnthologyComment replyTo, Pageable pageable);

    Page<AnthologyComment> getByArticle(Anthology anthology, Pageable pageable);

    Page<Long> getIdsByReplyTo(AnthologyComment replyTo, Pageable pageable);

    Page<Long> getIdsByArticle(Anthology anthology, Pageable pageable);
}
