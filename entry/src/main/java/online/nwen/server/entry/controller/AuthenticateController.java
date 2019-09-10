package online.nwen.server.entry.controller;

import online.nwen.server.bo.AuthenticationRequestBo;
import online.nwen.server.bo.AuthenticationResponseBo;
import online.nwen.server.service.api.ISecurityService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api
class AuthenticateController {
    private ISecurityService securityService;

    public AuthenticateController(ISecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping(path = "/authenticate")
    AuthenticationResponseBo authenticate(@RequestBody AuthenticationRequestBo authenticationRequestBo) {
        return this.securityService.authenticate(authenticationRequestBo);
    }
}
