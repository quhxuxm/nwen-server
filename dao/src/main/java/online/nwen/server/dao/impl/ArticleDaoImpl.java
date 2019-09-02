package online.nwen.server.dao.impl;

import online.nwen.server.dao.api.IArticleDao;
import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Article;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
class ArticleDaoImpl implements IArticleDao {
    private IArticleRepository articleRepository;

    ArticleDaoImpl(IArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "article-by-id", key = "#p0.id", condition = "#p0 != null")
    })
    @Override
    public Article save(Article article) {
        return this.articleRepository.save(article);
    }

    @Cacheable(cacheNames = "article-by-id", key = "#p0", unless = "#result == null", condition = "#p0 != null")
    @Override
    public Article getById(Long id) {
        return this.articleRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Article> getByAnthology(Anthology anthology, Pageable pageable) {
        return this.articleRepository.findByAnthology(anthology, pageable);
    }

    @Override
    public Page<Long> getIdsByAnthology(Anthology anthology, Pageable pageable) {
        return this.articleRepository.findIdsByAnthology(anthology, pageable);
    }
}
