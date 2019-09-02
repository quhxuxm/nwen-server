package online.nwen.server.dao.api;

import online.nwen.server.domain.User;

public interface IUserDao {
    User save(User user);

    User getById(Long id);

    User getByUsername(String username);
}
