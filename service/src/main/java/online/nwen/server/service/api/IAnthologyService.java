package online.nwen.server.service.api;

import online.nwen.server.bo.CreateAnthologyRequestBo;
import online.nwen.server.bo.CreateAnthologyResponseBo;
import online.nwen.server.bo.SecurityContextBo;

public interface IAnthologyService {
    CreateAnthologyResponseBo create(SecurityContextBo securityContextBo, CreateAnthologyRequestBo createAnthologyRequestBo);
}
