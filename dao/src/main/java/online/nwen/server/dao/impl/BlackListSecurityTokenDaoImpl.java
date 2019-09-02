package online.nwen.server.dao.impl;

import online.nwen.server.dao.api.IBlackListSecurityTokenDao;
import online.nwen.server.domain.BlackListSecurityToken;
import org.springframework.stereotype.Service;

@Service
class BlackListSecurityTokenDaoImpl implements IBlackListSecurityTokenDao {
    private IBlackListSecurityTokenRepository blackListSecurityTokenRepository;

    BlackListSecurityTokenDaoImpl(IBlackListSecurityTokenRepository blackListSecurityTokenRepository) {
        this.blackListSecurityTokenRepository = blackListSecurityTokenRepository;
    }

    @Override
    public BlackListSecurityToken save(BlackListSecurityToken token) {
        return this.blackListSecurityTokenRepository.save(token);
    }

    @Override
    public BlackListSecurityToken getById(Long id) {
        return this.blackListSecurityTokenRepository.findById(id).orElse(null);
    }

    @Override
    public BlackListSecurityToken getByToken(String token) {
        return this.blackListSecurityTokenRepository.findByToken(token);
    }
}
