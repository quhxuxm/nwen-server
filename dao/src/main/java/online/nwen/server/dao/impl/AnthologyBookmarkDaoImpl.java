package online.nwen.server.dao.impl;

import online.nwen.server.dao.api.IAnthologyBookmarkDao;
import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.AnthologyBookmark;
import online.nwen.server.domain.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
class AnthologyBookmarkDaoImpl implements IAnthologyBookmarkDao {
    private IAnthologyBookmarkRepository anthologyBookmarkRepository;

    AnthologyBookmarkDaoImpl(IAnthologyBookmarkRepository anthologyBookmarkRepository) {
        this.anthologyBookmarkRepository = anthologyBookmarkRepository;
    }

    @CacheEvict(cacheNames = "anthology-bookmark-by-user-and-anthology", key = "#p0.user.id+'-'+#p0.anthology.id", condition = "#p0 != null && #p0.user != " +
            "null && #p0.anthology != null")
    @Override
    public AnthologyBookmark save(AnthologyBookmark anthologyBookmark) {
        return this.anthologyBookmarkRepository.save(anthologyBookmark);
    }

    @Override
    public Page<AnthologyBookmark> getByUser(User user, Pageable pageable) {
        return this.anthologyBookmarkRepository.findByUser(user, pageable);
    }

    @Cacheable(cacheNames = "anthology-bookmark-by-user-and-anthology", key = "#p0.id+'-'+#p1.id", unless = "#result == null", condition = "#p0 != null && #p1 != null")
    @Override
    public AnthologyBookmark getByUserAndAnthology(User user, Anthology anthology) {
        return this.anthologyBookmarkRepository.findByUserAndAnthology(user, anthology);
    }

    @Override
    public Page<AnthologyBookmark> getByAnthology(Anthology anthology, Pageable pageable) {
        return this.anthologyBookmarkRepository.findByAnthology(anthology, pageable);
    }
}
