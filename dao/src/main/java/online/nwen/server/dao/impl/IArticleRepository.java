package online.nwen.server.dao.impl;

import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Article;
import online.nwen.server.domain.Label;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
interface IArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByAnthology(Anthology anthology, Pageable pageable);

    @Query("select article.id from Article article where article.anthology=:anthology")
    Page<Long> findIdsByAnthology(@Param("anthology") Anthology anthology, Pageable pageable);

    int countByAnthology(Anthology anthology);

    Page<Article> findByLabelsContaining(Set<Label> labels, Pageable pageable);
}
