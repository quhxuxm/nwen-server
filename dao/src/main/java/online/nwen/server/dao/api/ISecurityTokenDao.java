package online.nwen.server.dao.api;

import online.nwen.server.domain.SecurityToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface ISecurityTokenDao {
    SecurityToken save(SecurityToken token);

    SecurityToken getById(Long id);

    SecurityToken getByToken(String token);

    Page<SecurityToken> getTokensExceedRefreshTill(Date currentTime, Pageable pageable);

    void delete(SecurityToken token);
}
