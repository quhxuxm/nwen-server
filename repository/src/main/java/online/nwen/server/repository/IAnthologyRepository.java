package online.nwen.server.repository;

import online.nwen.server.domain.Anthology;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public interface IAnthologyRepository extends MongoRepository<Anthology, String> {
    Page<Anthology> findAllByAuthorIdAndPublishOrderByUpdateDateDesc(String authorId, boolean publish, Pageable pageable);

    Page<Anthology> findAllByTagsContainingAndPublishOrderByUpdateDateDesc(String[] tags, boolean publish, Pageable pageable);

    Page<Anthology> findAllByCreateDateBeforeAndPublishOrderByCreateDateDesc(Date relativeDate, boolean publish,
                                                                             Pageable pageable);

    Page<Anthology> findAllByUpdateDateBeforeAndPublishOrderByUpdateDateDesc(Date relativeDate, boolean publish,
                                                                             Pageable pageable);
}
