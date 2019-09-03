package online.nwen.server.service.api;

import online.nwen.server.bo.CreateArticleRequestBo;
import online.nwen.server.bo.CreateArticleResponseBo;
import online.nwen.server.bo.SecurityContextBo;

public interface IArticleService {
    CreateArticleResponseBo create(SecurityContextBo securityContextBo, CreateArticleRequestBo createArticleRequestBo);
}
