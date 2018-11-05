package online.nwen.server.repository;

import online.nwen.server.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentRepository extends MongoRepository<Comment, String> {
    Page<Comment> findAllByTypeAndRefDocumentId(Comment.Type type, String refDocumentId, Pageable pageable);
}
