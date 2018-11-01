package online.nwen.server.repository;

import online.nwen.server.domain.ExpiredSecureToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IExpiredSecureTokenRepository extends MongoRepository<ExpiredSecureToken, String> {
    boolean existsByToken(String token);
}
