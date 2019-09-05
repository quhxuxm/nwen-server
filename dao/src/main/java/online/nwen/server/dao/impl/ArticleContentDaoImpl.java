package online.nwen.server.dao.impl;

import online.nwen.server.dao.api.IArticleContentDao;
import online.nwen.server.domain.Article;
import online.nwen.server.domain.ArticleContent;
import online.nwen.server.domain.ArticleContentId;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            @CacheEvict(cacheNames = "article-content-by-article_id-and-version", key = "#p0.id.article.id+'-'+#p0.id.version",
                    condition = "#p0 != null && #p0.id != null && #p0.id.article !=null")
    })
    @Override
    public ArticleContent save(ArticleContent content) {
        return this.articleContentRepository.save(content);
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "article-content-by-article_id-and-version", key = "#p0.article.id+'-'+#p0.version", unless = "#result == null",
            condition = "#p0 != null && #p0.article !=null")
    @Override
    public ArticleContent getById(ArticleContentId id) {
        return this.articleContentRepository.findById(id).orElse(null);
    }

    @Override
    public Long getArticleContentLastVersion(Article article) {
        return this.articleContentRepository.findArticleContentLastVersion(article);
    }

    @Transactional
    @Override
    public Page<ArticleContent> getByArticle(Article article, Pageable pageable) {
        return this.articleContentRepository.findByArticle(article, pageable);
    }
}
