package online.nwen.server.dao.impl;

import online.nwen.server.domain.ArticleContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface IArticleContentRepository extends JpaRepository<ArticleContent, Long> {
}
