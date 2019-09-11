package online.nwen.server.dao.impl;

import online.nwen.server.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ICategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
