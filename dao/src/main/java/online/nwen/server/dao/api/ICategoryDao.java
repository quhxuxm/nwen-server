package online.nwen.server.dao.api;

import online.nwen.server.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoryDao {
    Category getByName(String name);

    Category getById(Long id);

    Page<Category> getAll(Pageable pageable);
}
