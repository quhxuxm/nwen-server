package online.nwen.server.repository;

import online.nwen.server.domain.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IAuthorRepository extends MongoRepository<Author, String> {
    Author findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);
}
