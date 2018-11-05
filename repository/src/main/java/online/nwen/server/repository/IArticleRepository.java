package online.nwen.server.repository;

import online.nwen.server.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public interface IArticleRepository extends MongoRepository<Article, String> {
    Page<Article> findAllByTagsContainingAndPublish(String[] tags, boolean publish, Pageable pageable);

    Page<Article> findAllByAnthologyIdAndPublish(String anthologyId, boolean publish, Pageable pageable);

    Page<Article> findAllByAuthorIdAndPublish(String authorId, boolean publish, Pageable pageable);

    Page<Article> findAllByCreateDateBeforeAndPublishOrderByCreateDateDesc(Date relativeDate, boolean publish,
                                                                           Pageable pageable);

    Page<Article> findAllByUpdateDateBeforeAndPublishOrderByUpdateDateDesc(Date relativeDate, boolean publish,
                                                                           Pageable pageable);
}
