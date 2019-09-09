package online.nwen.server.dao.impl;

import online.nwen.server.dao.api.IArticlePraiseDao;
import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Article;
import online.nwen.server.domain.ArticlePraise;
import online.nwen.server.domain.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
class ArticlePraiseDaoImpl implements IArticlePraiseDao {
    private IArticlePraiseRepository articlePraiseRepository;

    ArticlePraiseDaoImpl(IArticlePraiseRepository articlePraiseRepository) {
        this.articlePraiseRepository = articlePraiseRepository;
    }

    @Override
    public ArticlePraise save(ArticlePraise articlePraise) {
        return this.articlePraiseRepository.save(articlePraise);
    }

    @Override
    public Long countTotalPraiseOfAnthology(Anthology anthology) {
        return this.articlePraiseRepository.countByArticle_Anthology(anthology);
    }

    @Override
    public Long countTotalPraiseOfArticle(Article article) {
        return this.articlePraiseRepository.countByArticle(article);
    }

    @Cacheable(cacheNames = "article-praise-by-article-and-user", key = "#p0.id+'-'+#p1.id", unless = "#result == null", condition = "#p0 != null && #p1 != null")
    @Override
    public ArticlePraise getByArticleAndUser(Article article, User user) {
        return this.articlePraiseRepository.findByArticleAndUser(article, user);
    }
}
