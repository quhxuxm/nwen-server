package online.nwen.server.dao.api;

import online.nwen.server.domain.SecurityToken;

public interface ISecurityTokenDao {
    SecurityToken save(SecurityToken token);

    SecurityToken getById(Long id);

    SecurityToken getByToken(String token);

}
