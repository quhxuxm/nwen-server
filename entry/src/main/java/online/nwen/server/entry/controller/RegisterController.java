package online.nwen.server.entry.controller;

import online.nwen.server.bo.RegisterRequestBo;
import online.nwen.server.bo.RegisterResponseBo;
import online.nwen.server.service.api.IRegisterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
class RegisterController {
    private IRegisterService registerService;

    RegisterController(IRegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register")
    RegisterResponseBo register(@RequestBody RegisterRequestBo registerRequestBo) {
        return this.registerService.register(registerRequestBo);
    }
}
