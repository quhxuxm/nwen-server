package online.nwen.server.entry.controller;

import online.nwen.server.bo.AuthenticationRequestBo;
import online.nwen.server.bo.AuthenticationResponseBo;
import online.nwen.server.service.api.ISecurityService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
class AuthenticateController {
    private ISecurityService securityService;

    public AuthenticateController(ISecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/authenticate")
    AuthenticationResponseBo authenticate(@RequestBody AuthenticationRequestBo authenticationRequestBo) {
        return this.securityService.authenticate(authenticationRequestBo);
    }
}
