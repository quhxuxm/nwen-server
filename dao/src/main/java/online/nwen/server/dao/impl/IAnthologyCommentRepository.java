package online.nwen.server.dao.impl;

import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.AnthologyComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface IAnthologyCommentRepository extends JpaRepository<AnthologyComment, Long> {
    Page<AnthologyComment> findByAnthologyAndReplyToIsNull(Anthology anthology, Pageable pageable);

    Page<AnthologyComment> findByReplyTo(AnthologyComment replyTo, Pageable pageable);

    @Query("select ac.id from AnthologyComment  ac where ac.replyTo=:replyTo")
    Page<Long> findIdsByReplyTo(@Param("replyTo") AnthologyComment replyTo, Pageable pageable);

    @Query("select ac.id from AnthologyComment  ac where ac.replyTo is null and ac.anthology=:anthology")
    Page<Long> findIdsByArticleAndReplyToIsNull(@Param("anthology") Anthology article, Pageable pageable);
}
