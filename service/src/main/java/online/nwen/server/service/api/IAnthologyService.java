package online.nwen.server.service.api;

import online.nwen.server.bo.*;

public interface IAnthologyService {
    CreateAnthologyResponseBo create(SecurityContextBo securityContextBo, CreateAnthologyRequestBo createAnthologyRequestBo);

    DeleteAnthologiesResponseBo deleteAll(SecurityContextBo securityContextBo, DeleteAnthologiesRequestBo deleteAnthologiesRequestBo);
}
