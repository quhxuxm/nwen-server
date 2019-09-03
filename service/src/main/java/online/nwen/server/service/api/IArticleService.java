package online.nwen.server.service.api;

import online.nwen.server.bo.*;

public interface IArticleService {
    CreateArticleResponseBo create(CreateArticleRequestBo createArticleRequestBo);

    DeleteArticlesResponseBo deleteAll(DeleteArticlesRequestBo deleteArticlesRequestBo);
}
