package online.nwen.server.service.api;

import online.nwen.server.bo.MediaResourceBo;
import online.nwen.server.domain.MediaResource;

import java.io.File;
import java.io.InputStream;

public interface IMediaResourceService {
    MediaResourceBo getByMd5(String md5);

    String save(String contentType, InputStream inputStream);
}
