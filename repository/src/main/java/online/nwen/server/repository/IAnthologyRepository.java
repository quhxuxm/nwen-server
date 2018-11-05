package online.nwen.server.repository;

import online.nwen.server.domain.Anthology;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public interface IAnthologyRepository extends MongoRepository<Anthology, String> {
    Page<Anthology> findAllByAuthorId(String authorId, Pageable pageable);

    Page<Anthology> findAllByTagsContaining(String[] tags, Pageable pageable);

    Page<Anthology> findAllByCreateDateBeforeOrderByCreateDateDesc(Date relativeDate, Pageable pageable);

    Page<Anthology> findAllByUpdateDateBeforeOrderByUpdateDateDesc(Date relativeDate, Pageable pageable);
}
