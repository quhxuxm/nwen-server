package online.nwen.server.service.impl;

import online.nwen.server.domain.ExpiredSecureToken;
import online.nwen.server.repository.IExpiredSecureTokenRepository;
import online.nwen.server.service.api.IExpiredSecureTokenService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class ExpiredSecureTokenService implements IExpiredSecureTokenService {
    private IExpiredSecureTokenRepository expiredSecureTokenRepository;

    public ExpiredSecureTokenService(
            IExpiredSecureTokenRepository expiredSecureTokenRepository) {
        this.expiredSecureTokenRepository = expiredSecureTokenRepository;
    }

    @Override
    public ExpiredSecureToken findById(String id) {
        Optional<ExpiredSecureToken> resultOptional = this.expiredSecureTokenRepository.findById(id);
        return resultOptional.orElse(null);
    }

    @Override
    public ExpiredSecureToken findByToken(String token) {
        return this.expiredSecureTokenRepository.findByToken(token);
    }

    @Override
    public boolean existsByToken(String token) {
        return this.expiredSecureTokenRepository.existsByToken(token);
    }
}
