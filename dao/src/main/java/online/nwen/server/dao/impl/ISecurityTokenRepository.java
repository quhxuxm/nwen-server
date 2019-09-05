package online.nwen.server.dao.impl;

import online.nwen.server.domain.SecurityToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
interface ISecurityTokenRepository extends JpaRepository<SecurityToken, Long> {
    SecurityToken findByToken(String token);

    Page<SecurityToken> findByRefreshableTillBefore(Date currentTime, Pageable pageable);
}
