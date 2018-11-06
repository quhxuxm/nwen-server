package online.nwen.server.service.impl;

import online.nwen.server.domain.Article;
import online.nwen.server.repository.IArticleRepository;
import online.nwen.server.service.api.IArticleService;
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

    @Override
    public Article findById(String id) {
        Optional<Article> resultOptional = this.articleRepository.findById(id);
        return resultOptional.orElse(null);
    }

    @Override
    public Page<Article> findAllByTagsContainingAndPublishOrderByUpdateDateDesc(String[] tags, boolean publish,
                                                                                Pageable pageable) {
        return this.articleRepository.findAllByTagsContainingAndPublishOrderByUpdateDateDesc(tags, publish,
                pageable);
    }

    @Override
    public Page<Article> findAllByAnthologyIdAndPublishOrderByUpdateDateDesc(String anthologyId, boolean publish,
                                                                             Pageable pageable) {
        return this.articleRepository
                .findAllByAnthologyIdAndPublishOrderByUpdateDateDesc(anthologyId, publish, pageable);
    }

    @Override
    public Page<Article> findAllByAuthorIdAndPublishOrderByUpdateDateDesc(String authorId, boolean publish,
                                                                          Pageable pageable) {
        return this.articleRepository.findAllByAuthorIdAndPublishOrderByUpdateDateDesc(authorId, publish, pageable);
    }

    @Override
    public Page<Article> findAllByCreateDateBeforeAndPublishOrderByCreateDateDesc(Date relativeDate, boolean publish,
                                                                                  Pageable pageable) {
        return this.articleRepository
                .findAllByCreateDateBeforeAndPublishOrderByCreateDateDesc(relativeDate, publish, pageable);
    }

    @Override
    public Page<Article> findAllByUpdateDateBeforeAndPublishOrderByUpdateDateDesc(Date relativeDate, boolean publish,
                                                                                  Pageable pageable) {
        return this.articleRepository
                .findAllByUpdateDateBeforeAndPublishOrderByUpdateDateDesc(relativeDate, publish, pageable);
    }

    @Override
    public Article save(Article article) {
        return this.articleRepository.save(article);
    }
}
