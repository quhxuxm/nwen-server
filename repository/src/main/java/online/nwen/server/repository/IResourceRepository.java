package online.nwen.server.repository;

import online.nwen.server.domain.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IResourceRepository extends MongoRepository<Resource, String> {
    Resource findByMd5(String md5);
}
