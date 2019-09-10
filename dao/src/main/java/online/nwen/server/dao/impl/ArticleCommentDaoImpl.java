package online.nwen.server.dao.impl;

import online.nwen.server.dao.api.IArticleCommentDao;
import online.nwen.server.domain.Article;
import online.nwen.server.domain.ArticleComment;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class ArticleCommentDaoImpl implements IArticleCommentDao {
    private IArticleCommentRepository articleCommentRepository;

    ArticleCommentDaoImpl(IArticleCommentRepository articleCommentRepository) {
        this.articleCommentRepository = articleCommentRepository;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "article-comment-by-id", key = "#p0.id", condition = "#p0 != null")
    })
    @Override
    public ArticleComment save(ArticleComment articleComment) {
        return this.articleCommentRepository.save(articleComment);
    }

    @Transactional
    @Cacheable(cacheNames = "article-comment-by-id", key = "#p0", unless = "#result == null", condition = "#p0 != null")
    @Override
    public ArticleComment getById(Long id) {
        return this.articleCommentRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Page<ArticleComment> getByReplyTo(ArticleComment replyTo, Pageable pageable) {
        return this.articleCommentRepository.findByReplyToOrderByCreateTime(replyTo, pageable);
    }

    @Transactional
    @Override
    public Page<ArticleComment> getByArticle(Article article, Pageable pageable) {
        return this.articleCommentRepository.findByArticleAndReplyToIsNullOrderByCreateTime(article, pageable);
    }

    @Override
    public Page<Long> getIdsByReplyTo(ArticleComment replyTo, Pageable pageable) {
        return this.articleCommentRepository.findIdsByReplyToOrderByCreateTime(replyTo, pageable);
    }

    @Override
    public Page<Long> getIdsByArticle(Article article, Pageable pageable) {
        return this.articleCommentRepository.findIdsByArticleAndReplyToIsNullOrderByCreateTime(article, pageable);
    }

    @Override
    public Integer countByReplyTo(ArticleComment replyTo) {
        return this.articleCommentRepository.countByReplyTo(replyTo);
    }
}
