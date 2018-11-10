package online.nwen.server.service.impl;

import online.nwen.server.domain.Anthology;
import online.nwen.server.repository.IAnthologyRepository;
import online.nwen.server.service.api.IAnthologyService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
class AnthologyService implements IAnthologyService {
    private IAnthologyRepository anthologyRepository;

    AnthologyService(IAnthologyRepository anthologyRepository) {
        this.anthologyRepository = anthologyRepository;
    }

    @Cacheable(value = "anthology", key = "#p0", unless = "#result == null")
    @Override
    public Anthology findById(String id) {
        Optional<Anthology> resultOptional = this.anthologyRepository.findById(id);
        return resultOptional.orElse(null);
    }

    @Cacheable(value = "anthology", key = "#p0", unless = "#result == null")
    @Override
    public Anthology findByIdAndSystemConfirmedPublish(String id, boolean systemConfirmedPublish) {
        return this.anthologyRepository.findByIdAndSystemConfirmedPublish(id, systemConfirmedPublish);
    }

    @Override
    public boolean isOwner(String authorId, String anthologyId) {
        return this.anthologyRepository.existsByIdAndAuthorId(anthologyId, authorId);
    }

    @Override
    public Page<Anthology> findAllByAuthorIdOrderByCreateDateDesc(String authorId, Pageable pageable) {
        return this.anthologyRepository.findAllByAuthorIdOrderByCreateDateDesc(authorId, pageable);
    }

    @Override
    public Page<Anthology> findAllByAuthorIdOrderByUpdateDateDesc(String authorId, Pageable pageable) {
        return this.anthologyRepository.findAllByAuthorIdOrderByUpdateDateDesc(authorId, pageable);
    }

    @Override
    public Page<Anthology> findAllByAuthorIdAndSystemConfirmedPublishOrderByUpdateDateDesc(String authorId,
                                                                                           boolean systemConfirmedPublish,
                                                                                           Pageable pageable) {
        return this.anthologyRepository
                .findAllByAuthorIdAndSystemConfirmedPublishOrderByUpdateDateDesc(authorId, systemConfirmedPublish,
                        pageable);
    }

    @Override
    public Page<Anthology> findAllByTagsContainingAndSystemConfirmedPublishOrderByUpdateDateDesc(String[] tags,
                                                                                                 boolean systemConfirmedPublish,
                                                                                                 Pageable pageable) {
        return this.anthologyRepository
                .findAllByTagsContainingAndSystemConfirmedPublishOrderByUpdateDateDesc(tags, systemConfirmedPublish,
                        pageable);
    }

    @Override
    public Page<Anthology> findAllByCreateDateBeforeAndSystemConfirmedPublishOrderByCreateDateDesc(Date relativeDate,
                                                                                                   boolean systemConfirmedPublish,
                                                                                                   Pageable pageable) {
        return this.anthologyRepository
                .findAllByCreateDateBeforeAndSystemConfirmedPublishOrderByCreateDateDesc(relativeDate,
                        systemConfirmedPublish, pageable);
    }

    @Override
    public Page<Anthology> findAllByUpdateDateBeforeAndSystemConfirmedPublishOrderByUpdateDateDesc(Date relativeDate,
                                                                                                   boolean systemConfirmedPublish,
                                                                                                   Pageable pageable) {
        return this.anthologyRepository
                .findAllByUpdateDateBeforeAndSystemConfirmedPublishOrderByUpdateDateDesc(relativeDate,
                        systemConfirmedPublish, pageable);
    }

    @CachePut(value = "anthology", key = "#result.id", unless = "#result == null")
    @Override
    public Anthology save(Anthology anthology) {
        return this.anthologyRepository.save(anthology);
    }

    @CachePut(value = "anthology", key = "#p0.id", unless = "#result == null")
    @Override
    public void systemPublishAnthology(Anthology anthology) {
        anthology.setSystemConfirmedPublish(true);
        anthology.setSystemConfirmedPublishDate(new Date());
        this.anthologyRepository.save(anthology);
    }
}
