package online.nwen.server.repository;

import online.nwen.server.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public interface IArticleRepository extends MongoRepository<Article, String> {
    Page<Article> findAllByTagsContainingAndPublishOrderByUpdateDateDesc(String[] tags, boolean publish,
                                                                         Pageable pageable);

    Page<Article> findAllByAnthologyIdAndPublishOrderByUpdateDateDesc(String anthologyId, boolean publish,
                                                                      Pageable pageable);

    Page<Article> findAllByAuthorIdAndPublishOrderByUpdateDateDesc(String authorId, boolean publish, Pageable pageable);

    Page<Article> findAllByCreateDateBeforeAndPublishOrderByCreateDateDesc(Date relativeDate, boolean publish,
                                                                           Pageable pageable);

    Page<Article> findAllByUpdateDateBeforeAndPublishOrderByUpdateDateDesc(Date relativeDate, boolean publish,
                                                                           Pageable pageable);
}
