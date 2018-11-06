package online.nwen.server.service.impl;

import online.nwen.server.domain.Anthology;
import online.nwen.server.repository.IAnthologyRepository;
import online.nwen.server.service.api.IAnthologyService;
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

    @Override
    public Anthology findById(String id) {
        Optional<Anthology> resultOptional = this.anthologyRepository.findById(id);
        return resultOptional.orElse(null);
    }

    @Override
    public Page<Anthology> findAllByAuthorIdAndPublishOrderByUpdateDateDesc(String authorId, boolean publish,
                                                                            Pageable pageable) {
        return this.anthologyRepository.findAllByAuthorIdAndPublishOrderByUpdateDateDesc(authorId, publish, pageable);
    }

    @Override
    public Page<Anthology> findAllByTagsContainingAndPublishOrderByUpdateDateDesc(String[] tags, boolean publish,
                                                                                  Pageable pageable) {
        return this.anthologyRepository.findAllByTagsContainingAndPublishOrderByUpdateDateDesc(tags, publish, pageable);
    }

    @Override
    public Page<Anthology> findAllByCreateDateBeforeAndPublishOrderByCreateDateDesc(Date relativeDate, boolean publish,
                                                                                    Pageable pageable) {
        return this.anthologyRepository
                .findAllByCreateDateBeforeAndPublishOrderByCreateDateDesc(relativeDate, publish, pageable);
    }

    @Override
    public Page<Anthology> findAllByUpdateDateBeforeAndPublishOrderByUpdateDateDesc(Date relativeDate, boolean publish,
                                                                                    Pageable pageable) {
        return this.anthologyRepository
                .findAllByUpdateDateBeforeAndPublishOrderByUpdateDateDesc(relativeDate, publish, pageable);
    }

    @Override
    public Anthology save(Anthology anthology) {
        return this.anthologyRepository.save(anthology);
    }
}
