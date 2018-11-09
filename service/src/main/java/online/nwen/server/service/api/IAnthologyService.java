package online.nwen.server.service.api;

import online.nwen.server.domain.Anthology;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface IAnthologyService {
    Anthology findById(String id);

    Anthology findByIdAndSystemConfirmedPublish(String id, boolean systemConfirmedPublish);

    boolean isOwner(String authorId, String anthologyId);

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

    Anthology save(Anthology anthology);
}
