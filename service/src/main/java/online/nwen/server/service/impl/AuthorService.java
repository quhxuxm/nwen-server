package online.nwen.server.service.impl;

import online.nwen.server.domain.Author;
import online.nwen.server.repository.IAuthorRepository;
import online.nwen.server.service.api.IAuthorService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class AuthorService implements IAuthorService {
    private IAuthorRepository authorRepository;

    AuthorService(IAuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Cacheable("author")
    @Override
    public Author findById(String id) {
        Optional<Author> resultOptional = this.authorRepository.findById(id);
        return resultOptional.orElse(null);
    }

    @Cacheable(value = "author", key = "#result.id")
    @Override
    public Author findByUsername(String username) {
        return this.authorRepository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return this.authorRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return this.authorRepository.existsByNickname(nickname);
    }

    @CachePut(value = "author", key = "#result.id")
    @Override
    public Author save(Author author) {
        return this.authorRepository.save(author);
    }
}
