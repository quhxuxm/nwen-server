package online.nwen.server.service.api;

import online.nwen.server.bo.ContentAnalyzeResultBo;

public interface IContentAnalyzeService {
    ContentAnalyzeResultBo analyze(String content);
}
