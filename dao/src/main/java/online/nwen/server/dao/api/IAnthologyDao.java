package online.nwen.server.dao.api;

import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Article;
import online.nwen.server.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAnthologyDao {
    Anthology save(Anthology anthology);

    Anthology getById(Long id);

    Page<Anthology> getByAuthor(User author, Pageable pageable);

    Page<Long> getIdsByAuthor(User author, Pageable pageable);

    void delete(Anthology anthology);
}
