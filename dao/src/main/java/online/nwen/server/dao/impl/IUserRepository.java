package online.nwen.server.dao.impl;

import online.nwen.server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
