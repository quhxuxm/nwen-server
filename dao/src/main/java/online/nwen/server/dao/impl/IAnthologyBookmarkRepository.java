package online.nwen.server.dao.impl;

import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.AnthologyBookmark;
import online.nwen.server.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface IAnthologyBookmarkRepository extends JpaRepository<AnthologyBookmark, Long> {
    Page<AnthologyBookmark> findByUser(User user, Pageable pageable);

    AnthologyBookmark findByUserAndAnthology(User user, Anthology anthology);

    Page<AnthologyBookmark> findByAnthology(Anthology anthology, Pageable pageable);
}
