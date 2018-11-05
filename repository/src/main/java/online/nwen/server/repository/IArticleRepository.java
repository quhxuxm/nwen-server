package online.nwen.server.repository;

import online.nwen.server.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public interface IArticleRepository extends MongoRepository<Article, String> {
    Page<Article> findAllByTagsContaining(String[] tags, Pageable pageable);

    Page<Article> findAllByAnthologyId(String anthologyId, Pageable pageable);

    Page<Article> findAllByAuthorId(String authorId, Pageable pageable);

    Page<Article> findAllByCreateDateBeforeOrderByCreateDateDesc(Date relativeDate, Pageable pageable);

    Page<Article> findAllByUpdateDateBeforeOrderByUpdateDateDesc(Date relativeDate, Pageable pageable);
}
