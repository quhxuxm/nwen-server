package online.nwen.server.executor.impl;

import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Author;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.ViewAnthologySummaryRequestPayload;
import online.nwen.server.executor.api.payload.ViewAnthologySummaryResponsePayload;
import online.nwen.server.repository.IAnthologyRepository;
import online.nwen.server.repository.IAuthorRepository;
import online.nwen.server.service.api.ISecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class ViewAnthologySummaryExecutor implements
        IExecutor<ViewAnthologySummaryResponsePayload, ViewAnthologySummaryRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(ViewAnthologySummaryExecutor.class);
    private IAuthorRepository authorRepository;
    private IAnthologyRepository anthologyRepository;

    public ViewAnthologySummaryExecutor(
            IAuthorRepository authorRepository,
            IAnthologyRepository anthologyRepository) {
        this.authorRepository = authorRepository;
        this.anthologyRepository = anthologyRepository;
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
        Optional<Anthology> targetAnthologyOptional =
                this.anthologyRepository.findById(requestPayload.getAnthologyId());
        if (!targetAnthologyOptional.isPresent()) {
            logger.error("Fail to view anthology summary because of anthology not exist, anthology id = [{}].",
                    requestPayload.getAnthologyId());
            throw new ExecutorException(ExecutorException.Code.ANTHOLOGY_NOT_EXIST);
        }
        Anthology targetAnthology = targetAnthologyOptional.get();
        Optional<Author> anthologyOwnerOptional = this.authorRepository.findById(targetAnthology.getAuthorId());
        if (!anthologyOwnerOptional.isPresent()) {
            throw new ExecutorException(ExecutorException.Code.ARTICLE_AUTHOR_NOT_EXIST);
        }
        Author anthologyOwner = anthologyOwnerOptional.get();
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
        viewAnthologySummaryResponsePayload.setPublishDate(targetAnthology.getPublishDate());
        viewAnthologySummaryResponsePayload.setPublish(targetAnthology.isPublish());
        viewAnthologySummaryResponsePayload.setPraisesNumber(targetAnthology.getPraisesNumber());
        viewAnthologySummaryResponsePayload.setUpdateDate(targetAnthology.getUpdateDate());
        response.setPayload(viewAnthologySummaryResponsePayload);
    }
}
