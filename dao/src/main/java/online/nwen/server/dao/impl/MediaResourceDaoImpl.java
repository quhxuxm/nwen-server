package online.nwen.server.dao.impl;

import online.nwen.server.dao.api.IMediaResourceDao;
import online.nwen.server.domain.MediaResource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class MediaResourceDaoImpl implements IMediaResourceDao {
    private IMediaResourceRepository mediaResourceRepository;

    MediaResourceDaoImpl(IMediaResourceRepository mediaResourceRepository) {
        this.mediaResourceRepository = mediaResourceRepository;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "mediaresource-by-md5", key = "#p0.md5", condition = "#p0 != null")
    })
    @Override
    public MediaResource save(MediaResource mediaResource) {
        return this.mediaResourceRepository.save(mediaResource);
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "mediaresource-by-md5", key = "#p0", unless = "#result == null", condition = "#p0 != null")
    @Override
    public MediaResource getByMd5(String md5) {
        return this.mediaResourceRepository.findById(md5).orElse(null);
    }
}
