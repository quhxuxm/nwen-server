package online.nwen.server.service.api;

import online.nwen.server.bo.*;

public interface IArticleService {
    CreateArticleResponseBo create(SecurityContextBo securityContextBo, CreateArticleRequestBo createArticleRequestBo);

    DeleteArticlesResponseBo deleteAll(SecurityContextBo securityContextBo, DeleteArticlesRequestBo deleteArticlesRequestBo);
}
