package online.nwen.server.dao.api;

import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IArticleDao {
    Article save(Article article);

    Article getById(Long id);

    Page<Article> getByAnthology(Anthology anthology, Pageable pageable);

    Page<Long> getIdsByAnthology(Anthology anthology, Pageable pageable);

    int countArticleNumberInAnthology(Anthology anthology);
}
