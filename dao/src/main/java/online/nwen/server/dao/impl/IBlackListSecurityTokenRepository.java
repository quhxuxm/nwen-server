package online.nwen.server.dao.impl;

import online.nwen.server.domain.BlackListSecurityToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface IBlackListSecurityTokenRepository extends JpaRepository<BlackListSecurityToken, Long> {
    BlackListSecurityToken findByToken(String token);
}
