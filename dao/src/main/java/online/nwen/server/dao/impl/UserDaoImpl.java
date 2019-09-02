package online.nwen.server.dao.impl;

import online.nwen.server.dao.api.IUserDao;
import online.nwen.server.domain.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class UserDaoImpl implements IUserDao {
    private IUserRepository userRepository;

    public UserDaoImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "user-by-id", key = "#p0.id", condition = "#p0 != null"),
            @CacheEvict(cacheNames = "user-by-username", key = "#p0.username", condition = "#p0 != null")
    })
    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Cacheable(cacheNames = "user-by-id", key = "#p0", unless = "#result == null", condition = "#p0 != null")
    @Override
    public User getById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Cacheable(cacheNames = "user-by-username", key = "#p0", unless = "#result == null", condition = "#p0 != null")
    @Override
    public User getByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Cacheable(cacheNames = "user-by-nickname", key = "#p0", unless = "#result == null", condition = "#p0 != null")
    @Override
    public User getByNickname(String nickname) {
        return this.userRepository.findByNickname(nickname);
    }
}
