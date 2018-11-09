package online.nwen.server.repository;

import online.nwen.server.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public interface IArticleRepository extends MongoRepository<Article, String> {
    boolean existsByIdAndAuthorId(String id, String authorId);

    Article findByIdAndSystemConfirmedPublish(String id, boolean systemConfirmedPublish);

    Page<Article> findAllByTagsContainingAndSystemConfirmedPublishOrderByUpdateDateDesc(String[] tags,
                                                                                        boolean systemConfirmedPublish,
                                                                                        Pageable pageable);

    Page<Article> findAllByAnthologyIdOrderByCreateDateDesc(String anthologyId,
                                                            Pageable pageable);

    Page<Article> findAllByAnthologyIdOrderByUpdateDateDesc(String anthologyId,
                                                            Pageable pageable);

    Page<Article> findAllByAnthologyIdAndSystemConfirmedPublishOrderByUpdateDateDesc(String anthologyId,
                                                                                     boolean systemConfirmedPublish,
                                                                                     Pageable pageable);

    Page<Article> findAllByAnthologyIdAndSystemConfirmedPublishOrderByCreateDateDesc(String anthologyId,
                                                                                     boolean systemConfirmedPublish,
                                                                                     Pageable pageable);

    Page<Article> findAllByAuthorIdOrderByCreateDateDesc(String authorId,
                                                         Pageable pageable);

    Page<Article> findAllByAuthorIdOrderByUpdateDateDesc(String authorId,
                                                         Pageable pageable);

    Page<Article> findAllByAuthorIdAndSystemConfirmedPublishOrderByUpdateDateDesc(String authorId,
                                                                                  boolean systemConfirmedPublish,
                                                                                  Pageable pageable);

    Page<Article> findAllByAuthorIdAndSystemConfirmedPublishOrderByCreateDateDesc(String authorId,
                                                                                  boolean systemConfirmedPublish,
                                                                                  Pageable pageable);

    Page<Article> findAllByCreateDateBeforeAndSystemConfirmedPublishOrderByCreateDateDesc(Date relativeDate,
                                                                                          boolean systemConfirmedPublish,
                                                                                          Pageable pageable);

    Page<Article> findAllByUpdateDateBeforeAndSystemConfirmedPublishOrderByUpdateDateDesc(Date relativeDate,
                                                                                          boolean systemConfirmedPublish,
                                                                                          Pageable pageable);
}
