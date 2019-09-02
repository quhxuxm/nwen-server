package online.nwen.server.dao.api;

import online.nwen.server.domain.BlackListSecurityToken;

public interface IBlackListSecurityTokenDao {
    BlackListSecurityToken save(BlackListSecurityToken token);

    BlackListSecurityToken getById(Long id);

    BlackListSecurityToken getByToken(String token);

}
