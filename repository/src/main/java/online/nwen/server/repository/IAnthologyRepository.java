package online.nwen.server.repository;

import online.nwen.server.domain.Anthology;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public interface IAnthologyRepository extends MongoRepository<Anthology, String> {
    boolean existsByIdAndAuthorId(String id, String authorId);

    Anthology findByIdAndSystemConfirmedPublish(String id, boolean systemConfirmedPublish);

    Page<Anthology> findAllByAuthorIdOrderByCreateDate(String authorId,
                                                       Pageable pageable);

    Page<Anthology> findAllByAuthorIdOrderByUpdateDate(String authorId,
                                                       Pageable pageable);

    Page<Anthology> findAllByAuthorIdAndSystemConfirmedPublishOrderByUpdateDate(String authorId,
                                                                                boolean systemConfirmedPublish,
                                                                                Pageable pageable);

    Page<Anthology> findAllByTagsContainingAndSystemConfirmedPublishOrderByUpdateDate(String[] tags,
                                                                                      boolean systemConfirmedPublish,
                                                                                      Pageable pageable);

    Page<Anthology> findAllByCreateDateBeforeAndSystemConfirmedPublishOrderByCreateDate(Date relativeDate,
                                                                                        boolean systemConfirmedPublish,
                                                                                        Pageable pageable);

    Page<Anthology> findAllByUpdateDateBeforeAndSystemConfirmedPublishOrderByUpdateDate(Date relativeDate,
                                                                                        boolean systemConfirmedPublish,
                                                                                        Pageable pageable);
}
