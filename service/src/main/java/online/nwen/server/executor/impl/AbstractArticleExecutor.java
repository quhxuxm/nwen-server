package online.nwen.server.executor.impl;

import online.nwen.server.domain.Resource;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.service.api.ArticleContentAnalyzeResponse;
import online.nwen.server.service.api.IResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstractArticleExecutor<ResponsePayload, RequestPayload>
        implements IExecutor<ResponsePayload, RequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractArticleExecutor.class);
    private IResourceService resourceService;

    AbstractArticleExecutor(IResourceService resourceService) {
        this.resourceService = resourceService;
    }

    void saveMediaResources(ArticleContentAnalyzeResponse articleContentAnalyzeResponse,
                            String resourceSaveAuthorId) {
        logger.debug("Begin to save article media resources.");
        articleContentAnalyzeResponse.getMediaContents().forEach((md5, content) -> {
            if (resourceService.existsByMd5(md5)) {
                logger.debug("Media resource exist: md5 = [{}]", md5);
                return;
            }
            logger.debug("Save new media resource: md5 = [{}]", md5);
            Resource resource = new Resource();
            resource.setContent(content.getContent());
            resource.setContentType(content.getContentType());
            resource.setMd5(md5);
            resource.setSaveAuthorId(resourceSaveAuthorId);
            this.resourceService.save(resource);
        });
    }
}
