package online.nwen.server.service.impl;

import online.nwen.server.domain.Resource;
import online.nwen.server.repository.IResourceRepository;
import online.nwen.server.service.api.IResourceService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class ResourceService implements IResourceService {
    private IResourceRepository resourceRepository;

    ResourceService(IResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Override
    public Resource findById(String id) {
        Optional<Resource> resultOptional = this.resourceRepository.findById(id);
        return resultOptional.orElse(null);
    }

    @Override
    public Resource findByMd5(String md5) {
        return this.resourceRepository.findByMd5(md5);
    }

    @Override
    public boolean existsByMd5(String md5) {
        return this.resourceRepository.existsByMd5(md5);
    }

    @Override
    public Resource save(Resource resource) {
        return this.resourceRepository.save(resource);
    }
}
