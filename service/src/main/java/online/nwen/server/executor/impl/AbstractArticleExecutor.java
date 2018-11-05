package online.nwen.server.executor.impl;

import online.nwen.server.domain.Resource;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.repository.IResourceRepository;
import online.nwen.server.service.api.ArticleContentAnalyzeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstractArticleExecutor<ResponsePayload, RequestPayload>
        implements IExecutor<ResponsePayload, RequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractArticleExecutor.class);
    private IResourceRepository resourceRepository;

    AbstractArticleExecutor(IResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    void saveMediaResources(ArticleContentAnalyzeResponse articleContentAnalyzeResponse,
                            String resourceSaveAuthorId) {
        logger.debug("Begin to save article media resources.");
        articleContentAnalyzeResponse.getMediaContents().forEach((md5, content) -> {
            if (resourceRepository.existsByMd5(md5)) {
                logger.debug("Media resource exist: md5 = [{}]", md5);
                return;
            }
            logger.debug("Save new media resource: md5 = [{}]", md5);
            Resource resource = new Resource();
            resource.setContent(content.getContent());
            resource.setContentType(content.getContentType());
            resource.setMd5(md5);
            resource.setSaveAuthorId(resourceSaveAuthorId);
            this.resourceRepository.save(resource);
        });
    }
}
