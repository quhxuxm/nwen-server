package online.nwen.server.service.api;

import online.nwen.server.domain.Anthology;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface IAnthologyService {
    Anthology findById(String id);

    Anthology findByIdAndSystemConfirmedPublish(String id, boolean systemConfirmedPublish);

    boolean isOwner(String authorId, String anthologyId);

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

    Anthology save(Anthology anthology);

    void systemPublishAnthology(Anthology anthology);
}
