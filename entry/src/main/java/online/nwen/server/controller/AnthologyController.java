package online.nwen.server.controller;

import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.*;
import online.nwen.server.executor.impl.*;
import online.nwen.server.service.api.IExecutorService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/anthology")
class AnthologyController extends AbstractEntryController {
    private CreateAnthologyExecutor createAnthologyExecutor;
    private UpdateAnthologyExecutor updateAnthologyExecutor;
    private PublishAnthologyExecutor publishAnthologyExecutor;
    private ViewAnthologySummaryExecutor viewAnthologySummaryExecutor;
    private SearchAnthologyExecutor searchAnthologyExecutor;

    public AnthologyController(IExecutorService executorInvoker,
                               CreateAnthologyExecutor createAnthologyExecutor,
                               UpdateAnthologyExecutor updateAnthologyExecutor,
                               PublishAnthologyExecutor publishAnthologyExecutor,
                               ViewAnthologySummaryExecutor viewAnthologySummaryExecutor) {
        super(executorInvoker);
        this.createAnthologyExecutor = createAnthologyExecutor;
        this.updateAnthologyExecutor = updateAnthologyExecutor;
        this.publishAnthologyExecutor = publishAnthologyExecutor;
        this.viewAnthologySummaryExecutor = viewAnthologySummaryExecutor;
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    HttpExecutorResponse<CreateAnthologyResponsePayload> create(
            @RequestBody HttpExecutorRequest<CreateAnthologyRequestPayload> request)
            throws ExecutorException {
        return this.service(request, this.createAnthologyExecutor, true);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    HttpExecutorResponse<UpdateAnthologyResponsePayload> update(
            @RequestBody HttpExecutorRequest<UpdateAnthologyRequestPayload> request)
            throws ExecutorException {
        return this.service(request, this.updateAnthologyExecutor, true);
    }

    @PostMapping(value = "/publish", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    HttpExecutorResponse<PublishAnthologyResponsePayload> publish(
            @RequestBody HttpExecutorRequest<PublishAnthologyRequestPayload> request)
            throws ExecutorException {
        return this.service(request, this.publishAnthologyExecutor, true);
    }

    @PostMapping(value = "/view/summary", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    HttpExecutorResponse<ViewAnthologySummaryResponsePayload> viewSummary(
            @RequestBody HttpExecutorRequest<ViewAnthologySummaryRequestPayload> request)
            throws ExecutorException {
        return this.service(request, this.viewAnthologySummaryExecutor, false);
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    HttpExecutorResponse<SearchAnthologyResponsePayload> search(
            @RequestBody HttpExecutorRequest<SearchAnthologyRequestPayload> request)
            throws ExecutorException {
        return this.service(request, this.searchAnthologyExecutor, false);
    }
}

