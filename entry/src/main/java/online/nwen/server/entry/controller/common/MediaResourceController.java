package online.nwen.server.entry.controller.common;

import online.nwen.server.bo.MediaResourceBo;
import online.nwen.server.bo.ResponseCode;
import online.nwen.server.entry.controller.CommonMediaResource;
import online.nwen.server.service.api.IMediaResourceService;
import online.nwen.server.service.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@CommonMediaResource
class MediaResourceController {
    private static final Logger logger = LoggerFactory.getLogger(MediaResourceController.class);
    private IMediaResourceService mediaResourceService;

    MediaResourceController(IMediaResourceService mediaResourceService) {
        this.mediaResourceService = mediaResourceService;
    }

    @GetMapping(path = "{md5}")
    void getResource(String md5, HttpServletResponse httpServletResponse) {
        MediaResourceBo result = this.mediaResourceService.getByMd5(md5);
        if (result == null) {
            logger.error("Fail to write media resource to response because of not exist, md5 = [{}].", md5);
            throw new ServiceException(ResponseCode.MEDIA_RESOURCE_NOT_EXIT);
        }
        httpServletResponse.setContentType(result.getContentType());
        try {
            httpServletResponse.getOutputStream().write(result.getContent());
        } catch (IOException e) {
            logger.error("Fail to write media resource to response because of exception, md5 = [{}].", md5, e);
            throw new ServiceException(ResponseCode.MEDIA_RESOURCE_READ_FAIL);
        }
    }
}
