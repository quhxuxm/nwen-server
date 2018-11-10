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
    public Page<Anthology> findAllByAuthorIdOrderByCreateDate(String authorId, Pageable pageable) {
        return this.anthologyRepository.findAllByAuthorIdOrderByCreateDate(authorId, pageable);
    }

    @Override
    public Page<Anthology> findAllByAuthorIdOrderByUpdateDate(String authorId, Pageable pageable) {
        return this.anthologyRepository.findAllByAuthorIdOrderByUpdateDate(authorId, pageable);
    }

    @Override
    public Page<Anthology> findAllByAuthorIdAndSystemConfirmedPublishOrderByUpdateDate(String authorId,
                                                                                       boolean systemConfirmedPublish,
                                                                                       Pageable pageable) {
        return this.anthologyRepository
                .findAllByAuthorIdAndSystemConfirmedPublishOrderByUpdateDate(authorId, systemConfirmedPublish,
                        pageable);
    }

    @Override
    public Page<Anthology> findAllByTagsContainingAndSystemConfirmedPublishOrderByUpdateDate(String[] tags,
                                                                                             boolean systemConfirmedPublish,
                                                                                             Pageable pageable) {
        return this.anthologyRepository
                .findAllByTagsContainingAndSystemConfirmedPublishOrderByUpdateDate(tags, systemConfirmedPublish,
                        pageable);
    }

    @Override
    public Page<Anthology> findAllByCreateDateBeforeAndSystemConfirmedPublishOrderByCreateDate(Date relativeDate,
                                                                                               boolean systemConfirmedPublish,
                                                                                               Pageable pageable) {
        return this.anthologyRepository
                .findAllByCreateDateBeforeAndSystemConfirmedPublishOrderByCreateDate(relativeDate,
                        systemConfirmedPublish, pageable);
    }

    @Override
    public Page<Anthology> findAllByUpdateDateBeforeAndSystemConfirmedPublishOrderByUpdateDate(Date relativeDate,
                                                                                               boolean systemConfirmedPublish,
                                                                                               Pageable pageable) {
        return this.anthologyRepository
                .findAllByUpdateDateBeforeAndSystemConfirmedPublishOrderByUpdateDate(relativeDate,
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
