package online.nwen.server.repository;

import online.nwen.server.domain.Anthology;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public interface IAnthologyRepository extends MongoRepository<Anthology, String> {
    boolean existsByIdAndAuthorId(String id, String authorId);

    Anthology findByIdAndSystemConfirmedPublish(String id, boolean systemConfirmedPublish);

    Page<Anthology> findAllByAuthorIdOrderByCreateDateDesc(String authorId,
                                                           Pageable pageable);

    Page<Anthology> findAllByAuthorIdOrderByUpdateDateDesc(String authorId,
                                                           Pageable pageable);

    Page<Anthology> findAllByAuthorIdAndSystemConfirmedPublishOrderByUpdateDateDesc(String authorId,
                                                                                    boolean systemConfirmedPublish,
                                                                                    Pageable pageable);

    Page<Anthology> findAllByTagsContainingAndSystemConfirmedPublishOrderByUpdateDateDesc(String[] tags,
                                                                                          boolean systemConfirmedPublish,
                                                                                          Pageable pageable);

    Page<Anthology> findAllByCreateDateBeforeAndSystemConfirmedPublishOrderByCreateDateDesc(Date relativeDate,
                                                                                            boolean systemConfirmedPublish,
                                                                                            Pageable pageable);

    Page<Anthology> findAllByUpdateDateBeforeAndSystemConfirmedPublishOrderByUpdateDateDesc(Date relativeDate,
                                                                                            boolean systemConfirmedPublish,
                                                                                            Pageable pageable);
}
