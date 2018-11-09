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

    @Cacheable("article")
    @Override
    public Article findById(String id) {
        Optional<Article> resultOptional = this.articleRepository.findById(id);
        return resultOptional.orElse(null);
    }

    @Override
    public boolean isOwner(String authorId, String articleId) {
        return this.articleRepository.existsByIdAndAuthorId(articleId, authorId);
    }

    @Override
    public Article findByIdAndSystemConfirmedPublish(String id, boolean systemConfirmedPublish) {
        return this.articleRepository.findByIdAndSystemConfirmedPublish(id, systemConfirmedPublish);
    }

    @Override
    public Page<Article> findAllByTagsContainingAndSystemConfirmedPublishOrderByUpdateDateDesc(String[] tags,
                                                                                               boolean systemConfirmedPublish,
                                                                                               Pageable pageable) {
        return this.articleRepository
                .findAllByTagsContainingAndSystemConfirmedPublishOrderByUpdateDateDesc(tags, systemConfirmedPublish,
                        pageable);
    }

    @Override
    public Page<Article> findAllByAnthologyIdOrderByCreateDateDesc(String anthologyId, Pageable pageable) {
        return this.articleRepository.findAllByAnthologyIdOrderByCreateDateDesc(anthologyId, pageable);
    }

    @Override
    public Page<Article> findAllByAnthologyIdOrderByUpdateDateDesc(String anthologyId, Pageable pageable) {
        return this.articleRepository.findAllByAnthologyIdOrderByCreateDateDesc(anthologyId, pageable);
    }

    @Override
    public Page<Article> findAllByAnthologyIdAndSystemConfirmedPublishOrderByUpdateDateDesc(String anthologyId,
                                                                                            boolean systemConfirmedPublish,
                                                                                            Pageable pageable) {
        return this.articleRepository
                .findAllByAnthologyIdAndSystemConfirmedPublishOrderByUpdateDateDesc(anthologyId, systemConfirmedPublish,
                        pageable);
    }

    @Override
    public Page<Article> findAllByAnthologyIdAndSystemConfirmedPublishOrderByCreateDateDesc(String anthologyId,
                                                                                            boolean systemConfirmedPublish,
                                                                                            Pageable pageable) {
        return this.articleRepository
                .findAllByAnthologyIdAndSystemConfirmedPublishOrderByCreateDateDesc(anthologyId, systemConfirmedPublish,
                        pageable);
    }

    @Override
    public Page<Article> findAllByAuthorIdOrderByCreateDateDesc(String authorId, Pageable pageable) {
        return this.articleRepository.findAllByAuthorIdOrderByCreateDateDesc(authorId, pageable);
    }

    @Override
    public Page<Article> findAllByAuthorIdOrderByUpdateDateDesc(String authorId, Pageable pageable) {
        return this.articleRepository.findAllByAuthorIdOrderByUpdateDateDesc(authorId, pageable);
    }

    @Override
    public Page<Article> findAllByAuthorIdAndSystemConfirmedPublishOrderByUpdateDateDesc(String authorId,
                                                                                         boolean systemConfirmedPublish,
                                                                                         Pageable pageable) {
        return this.articleRepository
                .findAllByAuthorIdAndSystemConfirmedPublishOrderByUpdateDateDesc(authorId, systemConfirmedPublish,
                        pageable);
    }

    @Override
    public Page<Article> findAllByAuthorIdAndSystemConfirmedPublishOrderByCreateDateDesc(String authorId,
                                                                                         boolean systemConfirmedPublish,
                                                                                         Pageable pageable) {
        return this.articleRepository
                .findAllByAuthorIdAndSystemConfirmedPublishOrderByCreateDateDesc(authorId, systemConfirmedPublish,
                        pageable);
    }

    @Override
    public Page<Article> findAllByCreateDateBeforeAndSystemConfirmedPublishOrderByCreateDateDesc(Date relativeDate,
                                                                                                 boolean systemConfirmedPublish,
                                                                                                 Pageable pageable) {
        return this.articleRepository
                .findAllByCreateDateBeforeAndSystemConfirmedPublishOrderByCreateDateDesc(relativeDate,
                        systemConfirmedPublish, pageable);
    }

    @Override
    public Page<Article> findAllByUpdateDateBeforeAndSystemConfirmedPublishOrderByUpdateDateDesc(Date relativeDate,
                                                                                                 boolean systemConfirmedPublish,
                                                                                                 Pageable pageable) {
        return this.articleRepository
                .findAllByUpdateDateBeforeAndSystemConfirmedPublishOrderByUpdateDateDesc(relativeDate,
                        systemConfirmedPublish, pageable);
    }

    @CachePut(value = "article", key = "#result.id")
    @Override
    public Article save(Article article) {
        return this.articleRepository.save(article);
    }
}
