package online.nwen.server.entry.controller.security;

import online.nwen.server.bo.ResponseCode;
import online.nwen.server.entry.controller.SecurityMediaResource;
import online.nwen.server.service.api.IMediaResourceService;
import online.nwen.server.service.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@SecurityMediaResource
class SecurityMediaResourceController {
    private static final Logger logger = LoggerFactory.getLogger(SecurityMediaResourceController.class);
    private IMediaResourceService mediaResourceService;

    SecurityMediaResourceController(IMediaResourceService mediaResourceService) {
        this.mediaResourceService = mediaResourceService;
    }

    @PostMapping(path = "/upload")
    @ResponseBody
    String uploadResource(@RequestParam("file") MultipartFile uploadFile) {
        try {
            return this.mediaResourceService.save(uploadFile.getContentType(), uploadFile.getInputStream());
        } catch (IOException e) {
            throw new ServiceException(ResponseCode.MEDIA_RESOURCE_UPLOAD_FAIL);
        }
    }
}
