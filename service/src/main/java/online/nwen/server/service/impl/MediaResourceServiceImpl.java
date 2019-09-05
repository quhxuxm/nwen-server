package online.nwen.server.service.impl;

import online.nwen.server.bo.MediaResourceBo;
import online.nwen.server.bo.ResponseCode;
import online.nwen.server.dao.api.IMediaResourceDao;
import online.nwen.server.domain.MediaResource;
import online.nwen.server.service.api.IMediaResourceService;
import online.nwen.server.service.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.io.InputStream;

@Service
class MediaResourceServiceImpl implements IMediaResourceService {
    private IMediaResourceDao mediaResourceDao;

    public MediaResourceServiceImpl(IMediaResourceDao mediaResourceDao) {
        this.mediaResourceDao = mediaResourceDao;
    }

    @Override
    public MediaResourceBo getByMd5(String md5) {
        MediaResource mediaResource = this.mediaResourceDao.getByMd5(md5);
        if (mediaResource == null) {
            return null;
        }
        MediaResourceBo result = new MediaResourceBo();
        result.setMd5(mediaResource.getMd5());
        result.setContent(mediaResource.getContent());
        result.setContentType(mediaResource.getContentType());
        return result;
    }

    @Override
    public String save(String contentType, InputStream inputStream) {
        try {
            byte[] content = inputStream.readAllBytes();
            String mediaMd5 = DigestUtils.md5DigestAsHex(content);
            MediaResource existingResource = this.mediaResourceDao.getByMd5(mediaMd5);
            if (existingResource != null) {
                return mediaMd5;
            }
            MediaResource mediaResource = new MediaResource();
            mediaResource.setMd5(mediaMd5);
            mediaResource.setContentType(contentType);
            mediaResource.setContent(content);
            this.mediaResourceDao.save(mediaResource);
            return mediaMd5;
        } catch (IOException e) {
            throw new ServiceException(e, ResponseCode.MEDIA_RESOURCE_UPLOAD_FAIL);
        }
    }
}
