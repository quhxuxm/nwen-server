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

@Service
class ArticleCommentDaoImpl implements IArticleCommentDao {
    private IArticleCommentRepository articleCommentRepository;

    ArticleCommentDaoImpl(IArticleCommentRepository articleCommentRepository) {
        this.articleCommentRepository = articleCommentRepository;
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "article-comment-by-id", key = "#p0.id", condition = "#p0 != null")
    })
    @Override
    public ArticleComment save(ArticleComment articleComment) {
        return this.articleCommentRepository.save(articleComment);
    }

    @Cacheable(cacheNames = "article-comment-by-id", key = "#p0", unless = "#result == null", condition = "#p0 != null")
    @Override
    public ArticleComment getById(Long id) {
        return this.articleCommentRepository.findById(id).orElse(null);
    }

    @Override
    public Page<ArticleComment> getByReplyTo(ArticleComment replyTo, Pageable pageable) {
        return this.articleCommentRepository.findByReplyTo(replyTo, pageable);
    }

    @Override
    public Page<ArticleComment> getByArticle(Article article, Pageable pageable) {
        return this.articleCommentRepository.findByArticleAndReplyToIsNull(article, pageable);
    }

    @Override
    public Page<Long> getIdsByReplyTo(ArticleComment replyTo, Pageable pageable) {
        return this.articleCommentRepository.findIdsByReplyTo(replyTo, pageable);
    }

    @Override
    public Page<Long> getIdsByArticle(Article article, Pageable pageable) {
        return this.articleCommentRepository.findIdsByArticleAndReplyToIsNull(article, pageable);
    }
}
