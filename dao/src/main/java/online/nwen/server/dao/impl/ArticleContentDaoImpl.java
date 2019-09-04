package online.nwen.server.dao.impl;

import online.nwen.server.dao.api.IArticleContentDao;
import online.nwen.server.domain.ArticleContent;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class ArticleContentDaoImpl implements IArticleContentDao {
    private IArticleContentRepository articleContentRepository;

    ArticleContentDaoImpl(IArticleContentRepository articleContentRepository) {
        this.articleContentRepository = articleContentRepository;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "article-content-by-id", key = "#p0.id", condition = "#p0 != null")
    })
    @Override
    public ArticleContent save(ArticleContent content) {
        return this.articleContentRepository.save(content);
    }

    @Cacheable(cacheNames = "article-content-by-id", key = "#p0", unless = "#result == null", condition = "#p0 != null")
    @Override
    public ArticleContent getById(Long id) {
        return this.articleContentRepository.findById(id).orElse(null);
    }
}
