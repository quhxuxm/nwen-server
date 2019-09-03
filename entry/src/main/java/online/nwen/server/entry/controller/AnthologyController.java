package online.nwen.server.entry.controller;

import online.nwen.server.bo.CreateAnthologyRequestBo;
import online.nwen.server.bo.CreateAnthologyResponseBo;
import online.nwen.server.service.api.IAnthologyService;
import online.nwen.server.service.api.ISecurityService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
class AnthologyController {
    private IAnthologyService anthologyService;
    private ISecurityService securityService;

    AnthologyController(IAnthologyService anthologyService, ISecurityService securityService) {
        this.anthologyService = anthologyService;
        this.securityService = securityService;
    }

    @PostMapping("/security/anthology/create")
    CreateAnthologyResponseBo create(@RequestBody CreateAnthologyRequestBo createAnthologyRequestBo) {
        return this.anthologyService.create(this.securityService.getSecurityTokenFromCurrentThread(), createAnthologyRequestBo);
    }
}
