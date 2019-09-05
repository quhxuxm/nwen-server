package online.nwen.server.dao.impl;

import online.nwen.server.dao.api.IAnthologyDao;
import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Label;
import online.nwen.server.domain.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
class AnthologyDaoImpl implements IAnthologyDao {
    private IAnthologyRepository anthologyRepository;

    AnthologyDaoImpl(IAnthologyRepository anthologyRepository) {
        this.anthologyRepository = anthologyRepository;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "anthology-by-id", key = "#p0.id", condition = "#p0 != null")
    })
    @Override
    public Anthology save(Anthology anthology) {
        return this.anthologyRepository.save(anthology);
    }

    @Cacheable(cacheNames = "anthology-by-id", key = "#p0", unless = "#result == null", condition = "#p0 != null")
    @Override
    public Anthology getById(Long id) {
        return this.anthologyRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Anthology> getByAuthor(User author, Pageable pageable) {
        return this.anthologyRepository.findByAuthor(author, pageable);
    }

    @Override
    public Page<Long> getIdsByAuthor(User author, Pageable pageable) {
        return this.anthologyRepository.findIdsByAuthor(author, pageable);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "anthology-by-id", key = "#p0.id", condition = "#p0 != null")
    })
    @Override
    public void delete(Anthology anthology) {
        this.anthologyRepository.delete(anthology);
    }

    @Override
    public Page<Anthology> getByLabels(Set<Label> labels, Pageable pageable) {
        return this.anthologyRepository.findByLabels(labels, pageable);
    }
}
