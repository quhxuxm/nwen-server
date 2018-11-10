package online.nwen.server.repository;

import online.nwen.server.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public interface IArticleRepository extends MongoRepository<Article, String> {
    boolean existsByIdAndAuthorId(String id, String authorId);

    Article findByIdAndSystemConfirmedPublish(String id, boolean systemConfirmedPublish);

    Page<Article> findAllByTagsContainingAndSystemConfirmedPublishOrderByUpdateDate(String[] tags,
                                                                                    boolean systemConfirmedPublish,
                                                                                    Pageable pageable);

    Page<Article> findAllByAnthologyIdOrderByCreateDate(String anthologyId,
                                                        Pageable pageable);

    Page<Article> findAllByAnthologyIdOrderByUpdateDateDesc(String anthologyId,
                                                            Pageable pageable);

    Page<Article> findAllByAnthologyIdAndSystemConfirmedPublishOrderByUpdateDate(String anthologyId,
                                                                                 boolean systemConfirmedPublish,
                                                                                 Pageable pageable);

    Page<Article> findAllByAnthologyIdAndSystemConfirmedPublishOrderByCreateDate(String anthologyId,
                                                                                 boolean systemConfirmedPublish,
                                                                                 Pageable pageable);

    Page<Article> findAllByAuthorIdOrderByCreateDate(String authorId,
                                                     Pageable pageable);

    Page<Article> findAllByAuthorIdOrderByUpdateDate(String authorId,
                                                     Pageable pageable);

    Page<Article> findAllByAuthorIdAndSystemConfirmedPublishOrderByUpdateDate(String authorId,
                                                                              boolean systemConfirmedPublish,
                                                                              Pageable pageable);

    Page<Article> findAllByAuthorIdAndSystemConfirmedPublishOrderByCreateDate(String authorId,
                                                                              boolean systemConfirmedPublish,
                                                                              Pageable pageable);

    Page<Article> findAllByCreateDateBeforeAndSystemConfirmedPublishOrderByCreateDate(Date relativeDate,
                                                                                      boolean systemConfirmedPublish,
                                                                                      Pageable pageable);

    Page<Article> findAllByUpdateDateBeforeAndSystemConfirmedPublishOrderByUpdateDate(Date relativeDate,
                                                                                      boolean systemConfirmedPublish,
                                                                                      Pageable pageable);
}
