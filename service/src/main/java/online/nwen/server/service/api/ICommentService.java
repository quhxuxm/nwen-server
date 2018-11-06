package online.nwen.server.service.api;

import online.nwen.server.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICommentService {
    Comment findById(String id);

    Page<Comment> findAllByTypeAndRefDocumentId(Comment.Type type, String refDocumentId, Pageable pageable);

    Comment save(Comment comment);
}
