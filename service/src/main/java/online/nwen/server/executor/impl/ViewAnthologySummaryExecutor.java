package online.nwen.server.executor.impl;

import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Author;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.ViewAnthologySummaryRequestPayload;
import online.nwen.server.executor.api.payload.ViewAnthologySummaryResponsePayload;
import online.nwen.server.service.api.IAnthologyService;
import online.nwen.server.service.api.IAuthorService;
import online.nwen.server.service.api.ISecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ViewAnthologySummaryExecutor implements
        IExecutor<ViewAnthologySummaryResponsePayload, ViewAnthologySummaryRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(ViewAnthologySummaryExecutor.class);
    private IAuthorService authorService;
    private IAnthologyService anthologyService;

    public ViewAnthologySummaryExecutor(
            IAuthorService authorService,
            IAnthologyService anthologyService) {
        this.authorService = authorService;
        this.anthologyService = anthologyService;
    }

    @Override
    public void exec(IExecutorRequest<ViewAnthologySummaryRequestPayload> request,
                     IExecutorResponse<ViewAnthologySummaryResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        logger.debug("Begin to view anthology summary.");
        ViewAnthologySummaryRequestPayload requestPayload = request.getPayload();
        if (StringUtils.isEmpty(requestPayload.getAnthologyId())) {
            logger.error("Fail to view anthology summary because of anthology id is empty.");
            throw new ExecutorException(ExecutorException.Code.ANTHOLOGY_ID_IS_EMPTY);
        }
        boolean currentAuthorIsOwner = false;
        if (securityContext != null) {
            currentAuthorIsOwner =
                    this.anthologyService.isOwner(securityContext.getAuthorId(), requestPayload.getAnthologyId());
        }
        Anthology targetAnthology = null;
        if (currentAuthorIsOwner) {
            targetAnthology = this.anthologyService.findById(requestPayload.getAnthologyId());
        } else {
            targetAnthology =
                    this.anthologyService.findByIdAndSystemConfirmedPublish(requestPayload.getAnthologyId(), true);
        }
        if (targetAnthology == null) {
            logger.error("Fail to view anthology summary because of anthology not exist, anthology id = [{}].",
                    requestPayload.getAnthologyId());
            throw new ExecutorException(ExecutorException.Code.ANTHOLOGY_NOT_EXIST);
        }
        Author anthologyOwner = this.authorService.findById(targetAnthology.getAuthorId());
        if (anthologyOwner == null) {
            throw new ExecutorException(ExecutorException.Code.ARTICLE_AUTHOR_NOT_EXIST);
        }
        ViewAnthologySummaryResponsePayload viewAnthologySummaryResponsePayload =
                new ViewAnthologySummaryResponsePayload();
        viewAnthologySummaryResponsePayload.setTitle(targetAnthology.getTitle());
        viewAnthologySummaryResponsePayload.setId(targetAnthology.getId());
        viewAnthologySummaryResponsePayload.setCoverImageId(targetAnthology.getCoverImageId());
        viewAnthologySummaryResponsePayload.setSummary(targetAnthology.getSummary());
        viewAnthologySummaryResponsePayload.setBookmarksNumber(targetAnthology.getBookmarksNumber());
        viewAnthologySummaryResponsePayload.setCommentNumber(targetAnthology.getCommentsNumber());
        viewAnthologySummaryResponsePayload.setViewersNumber(targetAnthology.getViewersNumber());
        viewAnthologySummaryResponsePayload.setCreateDate(targetAnthology.getCreateDate());
        viewAnthologySummaryResponsePayload.setAuthorNickname(anthologyOwner.getNickname());
        viewAnthologySummaryResponsePayload.setAuthorId(targetAnthology.getId());
        viewAnthologySummaryResponsePayload.setAuthorIconImageId(anthologyOwner.getIconImageId());
        viewAnthologySummaryResponsePayload.setArticleNumber(targetAnthology.getArticleNumber());
        viewAnthologySummaryResponsePayload.setPublishDate(targetAnthology.getAuthorConfirmedPublishDate());
        viewAnthologySummaryResponsePayload.setPublish(targetAnthology.isAuthorConfirmedPublish());
        viewAnthologySummaryResponsePayload.setPraisesNumber(targetAnthology.getPraisesNumber());
        viewAnthologySummaryResponsePayload.setUpdateDate(targetAnthology.getUpdateDate());
        response.setPayload(viewAnthologySummaryResponsePayload);
    }
}
