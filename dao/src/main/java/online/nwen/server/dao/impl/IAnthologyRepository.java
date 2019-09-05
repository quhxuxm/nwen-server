package online.nwen.server.dao.impl;

import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Label;
import online.nwen.server.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
interface IAnthologyRepository extends JpaRepository<Anthology, Long> {
    Page<Anthology> findByAuthor(User author, Pageable pageable);

    @Query("select anthology.id from Anthology anthology where anthology.author=:author")
    Page<Long> findIdsByAuthor(@Param("author") User author, Pageable pageable);

    boolean existsByAuthorAndId(User author, Long id);

    @Query("select a from Anthology a , in(a.labels) l where l in :labels")
    Page<Anthology> findByLabels(@Param("labels") Set<Label> labels, Pageable pageable);
}
