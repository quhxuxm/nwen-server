package online.nwen.server.repository;

import online.nwen.server.domain.Anthology;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IAnthologyRepository extends MongoRepository<Anthology, String> {
}
