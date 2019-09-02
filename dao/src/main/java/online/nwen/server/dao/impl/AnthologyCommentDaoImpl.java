package online.nwen.server.dao.impl;

import online.nwen.server.dao.api.IAnthologyCommentDao;
import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.AnthologyComment;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class AnthologyCommentDaoImpl implements IAnthologyCommentDao {
    private IAnthologyCommentRepository anthologyCommentRepository;

    AnthologyCommentDaoImpl(IAnthologyCommentRepository anthologyCommentRepository) {
        this.anthologyCommentRepository = anthologyCommentRepository;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "anthology-comment-by-id", key = "#p0.id", condition = "#p0 != null")
    })
    @Override
    public AnthologyComment save(AnthologyComment anthologyComment) {
        return this.anthologyCommentRepository.save(anthologyComment);
    }

    @Cacheable(cacheNames = "anthology-comment-by-id", key = "#p0", unless = "#result == null", condition = "#p0 != null")
    @Override
    public AnthologyComment getById(Long id) {
        return this.anthologyCommentRepository.findById(id).orElse(null);
    }

    @Override
    public Page<AnthologyComment> getByReplyTo(AnthologyComment replyTo, Pageable pageable) {
        return this.anthologyCommentRepository.findByReplyTo(replyTo, pageable);
    }

    @Override
    public Page<AnthologyComment> getByArticle(Anthology anthology, Pageable pageable) {
        return this.anthologyCommentRepository.findByAnthologyAndReplyToIsNull(anthology, pageable);
    }

    @Override
    public Page<Long> getIdsByReplyTo(AnthologyComment replyTo, Pageable pageable) {
        return this.anthologyCommentRepository.findIdsByReplyTo(replyTo, pageable);
    }

    @Override
    public Page<Long> getIdsByArticle(Anthology anthology, Pageable pageable) {
        return this.anthologyCommentRepository.findIdsByArticleAndReplyToIsNull(anthology, pageable);
    }
}
