package online.nwen.server.service.api;

import online.nwen.server.domain.Resource;

public interface IResourceService {
    Resource findById(String id);

    Resource findByMd5(String md5);

    boolean existsByMd5(String md5);

    Resource save(Resource resource);
}
