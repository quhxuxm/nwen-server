package online.nwen.server.service.api;

import online.nwen.server.domain.Author;

public interface IAuthorService {
    Author findById(String id);

    Author findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);

    Author save(Author author);
}
