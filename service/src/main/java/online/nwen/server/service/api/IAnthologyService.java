package online.nwen.server.service.api;

import online.nwen.server.domain.Anthology;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface IAnthologyService {
    Anthology findById(String id);

    Page<Anthology> findAllByAuthorIdAndPublishOrderByUpdateDateDesc(String authorId, boolean publish,
                                                                     Pageable pageable);

    Page<Anthology> findAllByTagsContainingAndPublishOrderByUpdateDateDesc(String[] tags, boolean publish,
                                                                           Pageable pageable);

    Page<Anthology> findAllByCreateDateBeforeAndPublishOrderByCreateDateDesc(Date relativeDate, boolean publish,
                                                                             Pageable pageable);

    Page<Anthology> findAllByUpdateDateBeforeAndPublishOrderByUpdateDateDesc(Date relativeDate, boolean publish,
                                                                             Pageable pageable);

    Anthology save(Anthology anthology);
}
