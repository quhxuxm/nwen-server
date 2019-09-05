package online.nwen.server.service.impl;

import online.nwen.server.service.api.IContentAnalyzeService;
import org.springframework.stereotype.Service;

@Service
class ContentAnalyzeServiceImpl implements IContentAnalyzeService {
    @Override
    public String analyze(String content) {
        return content;
    }
}
