package online.nwen.server.dao.api;

import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Article;
import online.nwen.server.domain.Category;
import online.nwen.server.domain.Label;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface IArticleDao {
    Article save(Article article);

    Article getById(Long id);

    Page<Article> getByAnthology(Anthology anthology, Pageable pageable);

    Page<Long> getIdsByAnthology(Anthology anthology, Pageable pageable);

    int countArticleNumberInAnthology(Anthology anthology);

    void delete(Article article);

    Page<Article> getByLabels(Set<Label> labels, Pageable pageable);

    Page<Article> getByCategory(Category category, Pageable pageable);
}
