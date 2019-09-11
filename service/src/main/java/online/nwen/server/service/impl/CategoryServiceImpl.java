package online.nwen.server.service.impl;

import online.nwen.server.bo.CategoryBo;
import online.nwen.server.bo.ResponseCode;
import online.nwen.server.dao.api.ICategoryDao;
import online.nwen.server.domain.Category;
import online.nwen.server.service.api.ICategoryService;
import online.nwen.server.service.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
class CategoryServiceImpl implements ICategoryService {
    private ICategoryDao categoryDao;

    CategoryServiceImpl(ICategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public CategoryBo convert(Category category) {
        CategoryBo result = new CategoryBo();
        result.setId(category.getId());
        result.setName(category.getName());
        result.setDescription(category.getDescription());
        result.setCreateTime(category.getCreateTime());
        return result;
    }

    @Override
    public Page<CategoryBo> getAll(Pageable pageable) {
        return this.categoryDao.getAll(pageable).map(this::convert);
    }

    @Override
    public CategoryBo getById(Long categoryId) {
        Category category = this.categoryDao.getById(categoryId);
        if (category == null) {
            throw new ServiceException(ResponseCode.CATEGORY_NOT_EXIST);
        }
        return this.convert(category);
    }
}
