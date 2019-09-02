package online.nwen.server.service.api;

import online.nwen.server.bo.CreateAnthologyRequestBo;
import online.nwen.server.bo.CreateAnthologyResponseBo;

public interface IAnthologyService {
    CreateAnthologyResponseBo create(String secureToken,  CreateAnthologyRequestBo createAnthologyRequestBo);
}
