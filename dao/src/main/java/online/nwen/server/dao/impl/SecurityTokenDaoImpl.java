package online.nwen.server.dao.impl;

import online.nwen.server.dao.api.ISecurityTokenDao;
import online.nwen.server.domain.SecurityToken;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
class SecurityTokenDaoImpl implements ISecurityTokenDao {
    private ISecurityTokenRepository securityTokenRepository;

    SecurityTokenDaoImpl(ISecurityTokenRepository securityTokenRepository) {
        this.securityTokenRepository = securityTokenRepository;
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "security-token-by-id", key = "#p0.id", condition = "#p0 != null"),
            @CacheEvict(cacheNames = "security-token-by-token", key = "#p0.token", condition = "#p0 != null")
    })
    @Transactional
    @Override
    public SecurityToken save(SecurityToken token) {
        return this.securityTokenRepository.save(token);
    }

    @Cacheable(cacheNames = "security-token-by-id", key = "#p0", unless = "#result == null", condition = "#p0 != null")
    @Override
    public SecurityToken getById(Long id) {
        return this.securityTokenRepository.findById(id).orElse(null);
    }

    @Cacheable(cacheNames = "security-token-by-token", key = "#p0", unless = "#result == null", condition = "#p0 != null")
    @Override
    public SecurityToken getByToken(String token) {
        return this.securityTokenRepository.findByToken(token);
    }

    @Override
    public Page<SecurityToken> getTokensExceedRefreshTill(Date currentTime, Pageable pageable) {
        return this.securityTokenRepository.findByRefreshableTillBefore(currentTime, pageable);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "security-token-by-id", key = "#p0.id", condition = "#p0 != null"),
            @CacheEvict(cacheNames = "security-token-by-token", key = "#p0.token", condition = "#p0 != null")
    })
    @Transactional
    @Override
    public void delete(SecurityToken token) {
        this.securityTokenRepository.delete(token);
    }
}
