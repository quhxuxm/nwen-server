package online.nwen.server.dao.impl;

import online.nwen.server.dao.api.ICategoryDao;
import online.nwen.server.domain.Category;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
class CategoryDaoImpl implements ICategoryDao {
    private ICategoryRepository categoryRepository;

    CategoryDaoImpl(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "category-by-name", key = "#p0", condition = "#p0 != null")
    })
    @Override
    public Category getByName(String name) {
        return this.categoryRepository.findByName(name);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "category-by-id", key = "#p0", condition = "#p0 != null")
    })
    @Override
    public Category getById(Long id) {
        return this.categoryRepository.findById(id).orElse(null);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "category-all", key = "#p0", condition = "#p0 != null")
    })
    @Override
    public Page<Category> getAll(Pageable pageable) {
        return this.categoryRepository.findAll(pageable);
    }
}
