package online.nwen.server.service.api;

import online.nwen.server.bo.CategoryBo;
import online.nwen.server.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoryService {
    CategoryBo convert(Category category);

    Page<CategoryBo> getAll(Pageable pageable);

    CategoryBo getById(Long categoryId);
}
