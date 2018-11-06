package online.nwen.server.service.impl;

import online.nwen.server.domain.Comment;
import online.nwen.server.repository.ICommentRepository;
import online.nwen.server.service.api.ICommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class CommentService implements ICommentService {
    private ICommentRepository commentRepository;

    CommentService(ICommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment findById(String id) {
        Optional<Comment> resultOptional = this.commentRepository.findById(id);
        return resultOptional.orElse(null);
    }

    @Override
    public Page<Comment> findAllByTypeAndRefDocumentId(Comment.Type type, String refDocumentId, Pageable pageable) {
        return this.commentRepository.findAllByTypeAndRefDocumentId(type, refDocumentId, pageable);
    }

    @Override
    public Comment save(Comment comment) {
        return this.commentRepository.save(comment);
    }
}
