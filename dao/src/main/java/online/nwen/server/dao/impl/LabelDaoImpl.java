package online.nwen.server.dao.impl;

import online.nwen.server.dao.api.ILabelDao;
import online.nwen.server.domain.Label;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class LabelDaoImpl implements ILabelDao {
    private ILabelRepository labelRepository;

    LabelDaoImpl(ILabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "label-by-id", key = "#p0.id", condition = "#p0 != null"),
            @CacheEvict(cacheNames = "label-by-text", key = "#p0.text", condition = "#p0 != null")
    })
    @Override
    public Label save(Label label) {
        return this.labelRepository.save(label);
    }

    @Cacheable(cacheNames = "label-by-id", key = "#p0", unless = "#result == null", condition = "#p0 != null")
    @Override
    public Label getById(Long id) {
        return this.labelRepository.findById(id).orElse(null);
    }

    @Cacheable(cacheNames = "label-by-text", key = "#p0", unless = "#result == null", condition = "#p0 != null")
    @Override
    public Label getByText(String text) {
        return this.labelRepository.findByText(text);
    }

    @Override
    public List<Label> getTopNLabels(int n) {
        Pageable pageable = PageRequest.of(0, n, Sort.by(Sort.Direction.DESC, "popularFactor"));
        return this.labelRepository.findLabels(pageable);
    }
}
