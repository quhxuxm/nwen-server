package online.nwen.server.dao.api;

import online.nwen.server.domain.MediaResource;

public interface IMediaResourceDao {
    MediaResource save(MediaResource mediaResource);

    MediaResource getByMd5(String md5);
}
