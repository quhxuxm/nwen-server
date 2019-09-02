package online.nwen.server.service.api;

import online.nwen.server.bo.RegisterRequestBo;
import online.nwen.server.bo.RegisterResponseBo;

public interface IRegisterService {
    RegisterResponseBo register(RegisterRequestBo registerRequestBo);
}
