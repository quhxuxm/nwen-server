package online.nwen.server.dao.impl;

import online.nwen.server.domain.Label;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ILabelRepository extends JpaRepository<Label, Long> {
    Label findByText(String text);

    @Query("select l from Label l")
    List<Label> findLabels(Pageable pageable);

    List<Label> findByTextLikeOrderByPopularFactor(String text);
}
