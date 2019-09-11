package online.nwen.server.entry.controller.common;

import online.nwen.server.bo.RegisterRequestBo;
import online.nwen.server.bo.RegisterResponseBo;
import online.nwen.server.entry.controller.CommonApi;
import online.nwen.server.service.api.IRegisterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CommonApi
class RegisterController {
    private IRegisterService registerService;

    RegisterController(IRegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping(path = "/register")
    RegisterResponseBo register(@RequestBody RegisterRequestBo registerRequestBo) {
        return this.registerService.register(registerRequestBo);
    }
}
