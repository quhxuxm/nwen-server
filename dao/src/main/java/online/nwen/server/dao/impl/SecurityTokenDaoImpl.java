package online.nwen.server.dao.impl;

import online.nwen.server.dao.api.ISecurityTokenDao;
import online.nwen.server.domain.SecurityToken;
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

    @Transactional
    @Override
    public SecurityToken save(SecurityToken token) {
        return this.securityTokenRepository.save(token);
    }

    @Override
    public SecurityToken getById(Long id) {
        return this.securityTokenRepository.findById(id).orElse(null);
    }

    @Override
    public SecurityToken getByToken(String token) {
        return this.securityTokenRepository.findByToken(token);
    }

    @Override
    public Page<SecurityToken> getTokensExceedRefreshTill(Date currentTime, Pageable pageable) {
        return this.securityTokenRepository.findByRefreshableTillBefore(currentTime, pageable);
    }

    @Transactional
    @Override
    public void delete(SecurityToken token) {
        this.securityTokenRepository.delete(token);
    }
}
