package online.nwen.server.service.impl;

import online.nwen.server.domain.Article;
import online.nwen.server.repository.IArticleRepository;
import online.nwen.server.service.api.IArticleService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
class ArticleService implements IArticleService {
    private IArticleRepository articleRepository;

    ArticleService(IArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Cacheable(value = "article", key = "#p0", unless = "#result == null")
    @Override
    public Article findById(String id) {
        Optional<Article> resultOptional = this.articleRepository.findById(id);
        return resultOptional.orElse(null);
    }

    @Override
    public boolean isOwner(String authorId, String articleId) {
        return this.articleRepository.existsByIdAndAuthorId(articleId, authorId);
    }

    @Cacheable(value = "article", key = "#p0", unless = "#result == null")
    @Override
    public Article findByIdAndSystemConfirmedPublish(String id, boolean systemConfirmedPublish) {
        return this.articleRepository.findByIdAndSystemConfirmedPublish(id, systemConfirmedPublish);
    }

    @Override
    public Page<Article> findAllByTagsContainingAndSystemConfirmedPublishOrderByUpdateDate(String[] tags,
                                                                                           boolean systemConfirmedPublish,
                                                                                           Pageable pageable) {
        return this.articleRepository
                .findAllByTagsContainingAndSystemConfirmedPublishOrderByUpdateDate(tags, systemConfirmedPublish,
                        pageable);
    }

    @Override
    public Page<Article> findAllByAnthologyIdOrderByCreateDate(String anthologyId, Pageable pageable) {
        return this.articleRepository.findAllByAnthologyIdOrderByCreateDate(anthologyId, pageable);
    }

    @Override
    public Page<Article> findAllByAnthologyIdOrderByUpdateDate(String anthologyId, Pageable pageable) {
        return this.articleRepository.findAllByAnthologyIdOrderByCreateDate(anthologyId, pageable);
    }

    @Override
    public Page<Article> findAllByAnthologyIdAndSystemConfirmedPublishOrderByUpdateDate(String anthologyId,
                                                                                        boolean systemConfirmedPublish,
                                                                                        Pageable pageable) {
        return this.articleRepository
                .findAllByAnthologyIdAndSystemConfirmedPublishOrderByUpdateDate(anthologyId, systemConfirmedPublish,
                        pageable);
    }

    @Override
    public Page<Article> findAllByAnthologyIdAndSystemConfirmedPublishOrderByCreateDate(String anthologyId,
                                                                                        boolean systemConfirmedPublish,
                                                                                        Pageable pageable) {
        return this.articleRepository
                .findAllByAnthologyIdAndSystemConfirmedPublishOrderByCreateDate(anthologyId, systemConfirmedPublish,
                        pageable);
    }

    @Override
    public Page<Article> findAllByAuthorIdOrderByCreateDate(String authorId, Pageable pageable) {
        return this.articleRepository.findAllByAuthorIdOrderByCreateDate(authorId, pageable);
    }

    @Override
    public Page<Article> findAllByAuthorIdOrderByUpdateDate(String authorId, Pageable pageable) {
        return this.articleRepository.findAllByAuthorIdOrderByUpdateDate(authorId, pageable);
    }

    @Override
    public Page<Article> findAllByAuthorIdAndSystemConfirmedPublishOrderByUpdateDate(String authorId,
                                                                                     boolean systemConfirmedPublish,
                                                                                     Pageable pageable) {
        return this.articleRepository
                .findAllByAuthorIdAndSystemConfirmedPublishOrderByUpdateDate(authorId, systemConfirmedPublish,
                        pageable);
    }

    @Override
    public Page<Article> findAllByAuthorIdAndSystemConfirmedPublishOrderByCreateDate(String authorId,
                                                                                     boolean systemConfirmedPublish,
                                                                                     Pageable pageable) {
        return this.articleRepository
                .findAllByAuthorIdAndSystemConfirmedPublishOrderByCreateDate(authorId, systemConfirmedPublish,
                        pageable);
    }

    @Override
    public Page<Article> findAllByCreateDateBeforeAndSystemConfirmedPublishOrderByCreateDateDesc(Date relativeDate,
                                                                                                 boolean systemConfirmedPublish,
                                                                                                 Pageable pageable) {
        return this.articleRepository
                .findAllByCreateDateBeforeAndSystemConfirmedPublishOrderByCreateDate(relativeDate,
                        systemConfirmedPublish, pageable);
    }

    @Override
    public Page<Article> findAllByUpdateDateBeforeAndSystemConfirmedPublishOrderByUpdateDateDesc(Date relativeDate,
                                                                                                 boolean systemConfirmedPublish,
                                                                                                 Pageable pageable) {
        return this.articleRepository
                .findAllByUpdateDateBeforeAndSystemConfirmedPublishOrderByUpdateDate(relativeDate,
                        systemConfirmedPublish, pageable);
    }

    @CachePut(value = "article", key = "#result.id", condition = "#result != null")
    @Override
    public Article save(Article article) {
        return this.articleRepository.save(article);
    }

    @CachePut(value = "article", key = "#p0.id", condition = "#result != null")
    @Override
    public void systemPublishArticle(Article article) {
        article.setSystemConfirmedPublish(true);
        article.setSystemConfirmedPublishDate(new Date());
        this.articleRepository.save(article);
    }
}
