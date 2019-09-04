package online.nwen.server.dao.impl;

import online.nwen.server.domain.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ILabelRepository extends JpaRepository<Label, Long> {
    Label findByText(String text);
}
