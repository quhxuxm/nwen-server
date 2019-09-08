package online.nwen.server.dao.impl;

import online.nwen.server.dao.api.IAnthologyBookmarkDao;
import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.AnthologyBookmark;
import online.nwen.server.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
class AnthologyBookmarkDaoImpl implements IAnthologyBookmarkDao {
    private IAnthologyBookmarkRepository anthologyBookmarkRepository;

    AnthologyBookmarkDaoImpl(IAnthologyBookmarkRepository anthologyBookmarkRepository) {
        this.anthologyBookmarkRepository = anthologyBookmarkRepository;
    }

    @Override
    public AnthologyBookmark save(AnthologyBookmark anthologyBookmark) {
        return this.anthologyBookmarkRepository.save(anthologyBookmark);
    }

    @Override
    public Page<AnthologyBookmark> getByUser(User user, Pageable pageable) {
        return this.anthologyBookmarkRepository.findByUser(user, pageable);
    }

    @Override
    public AnthologyBookmark getByUserAndAnthology(User user, Anthology anthology) {
        return this.anthologyBookmarkRepository.findByUserAndAnthology(user, anthology);
    }
}
