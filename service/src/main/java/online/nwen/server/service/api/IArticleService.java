package online.nwen.server.service.api;

import online.nwen.server.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface IArticleService {
    Article findById(String id);

    boolean isOwner(String authorId, String articleId);

    Article findByIdAndSystemConfirmedPublish(String id, boolean systemConfirmedPublish);

    Page<Article> findAllByTagsContainingAndSystemConfirmedPublishOrderByUpdateDate(String[] tags,
                                                                                    boolean systemConfirmedPublish,
                                                                                    Pageable pageable);

    Page<Article> findAllByAnthologyIdOrderByCreateDate(String anthologyId,
                                                        Pageable pageable);

    Page<Article> findAllByAnthologyIdOrderByUpdateDate(String anthologyId,
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

    Page<Article> findAllByCreateDateBeforeAndSystemConfirmedPublishOrderByCreateDateDesc(Date relativeDate,
                                                                                          boolean systemConfirmedPublish,
                                                                                          Pageable pageable);

    Page<Article> findAllByUpdateDateBeforeAndSystemConfirmedPublishOrderByUpdateDateDesc(Date relativeDate,
                                                                                          boolean systemConfirmedPublish,
                                                                                          Pageable pageable);

    Article save(Article article);

    void systemPublishArticle(Article article);
}
