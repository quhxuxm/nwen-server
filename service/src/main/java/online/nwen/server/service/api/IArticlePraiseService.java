package online.nwen.server.service.api;

import online.nwen.server.bo.CreateArticlePraiseResponseBo;

public interface IArticlePraiseService {
    CreateArticlePraiseResponseBo praise(Long articleId);

    public Long countTotalPraiseOfAnthology(Long anthologyId);

    public Long countTotalPraiseOfArticle(Long articleId);
}
