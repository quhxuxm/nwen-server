package online.nwen.server.dao.impl;

import online.nwen.server.domain.SecurityToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ISecurityTokenRepository extends JpaRepository<SecurityToken, Long> {
    SecurityToken findByToken(String token);
}
