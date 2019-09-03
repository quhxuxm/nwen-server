package online.nwen.server.dao.impl;

import online.nwen.server.dao.api.ISecurityTokenDao;
import online.nwen.server.domain.SecurityToken;
import org.springframework.stereotype.Service;

@Service
class SecurityTokenDaoImpl implements ISecurityTokenDao {
    private ISecurityTokenRepository securityTokenRepository;

    SecurityTokenDaoImpl(ISecurityTokenRepository securityTokenRepository) {
        this.securityTokenRepository = securityTokenRepository;
    }

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
}
