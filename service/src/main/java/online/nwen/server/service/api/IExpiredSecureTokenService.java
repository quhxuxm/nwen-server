package online.nwen.server.service.api;

import online.nwen.server.domain.ExpiredSecureToken;

public interface IExpiredSecureTokenService {
    ExpiredSecureToken findById(String id);

    ExpiredSecureToken findByToken(String token);

    boolean existsByToken(String token);
}
