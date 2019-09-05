package online.nwen.server.dao.api;

import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Label;
import online.nwen.server.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface IAnthologyDao {
    Anthology save(Anthology anthology);

    Anthology getById(Long id);

    Page<Anthology> getByAuthor(User author, Pageable pageable);

    Page<Long> getIdsByAuthor(User author, Pageable pageable);

    void delete(Anthology anthology);

    Page<Anthology> getByLabels(Set<Label> labels, Pageable pageable);
}
