package online.nwen.server.entry.controller.common;

import online.nwen.server.bo.CategoryBo;
import online.nwen.server.entry.controller.CommonApiController;
import online.nwen.server.service.api.ICategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CommonApiController
class CategoryController {
    private ICategoryService categoryService;

    CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category")
    Page<CategoryBo> getAll(Pageable pageable) {
        return this.categoryService.getAll(pageable);
    }

    @GetMapping("/category/{categoryId}")
    CategoryBo getById(@PathVariable("categoryId") Long categoryId) {
        return this.categoryService.getById(categoryId);
    }
}
