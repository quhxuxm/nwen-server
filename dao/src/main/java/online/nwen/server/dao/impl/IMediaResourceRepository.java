package online.nwen.server.dao.impl;

import online.nwen.server.domain.MediaResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface IMediaResourceRepository extends JpaRepository<MediaResource, String> {
}
