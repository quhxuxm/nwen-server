package online.nwen.server.entry.controller.security;

import online.nwen.server.bo.CreateAnthologyRequestBo;
import online.nwen.server.bo.CreateAnthologyResponseBo;
import online.nwen.server.bo.UpdateAnthologyRequestBo;
import online.nwen.server.bo.UpdateAnthologyResponseBo;
import online.nwen.server.entry.controller.SecurityApi;
import online.nwen.server.service.api.IAnthologyService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@SecurityApi
class SecurityAnthologyController {
    private IAnthologyService anthologyService;

    SecurityAnthologyController(IAnthologyService anthologyService) {
        this.anthologyService = anthologyService;
    }

    @PostMapping("/anthology/create")
    CreateAnthologyResponseBo create(@RequestBody CreateAnthologyRequestBo createAnthologyRequestBo) {
        return this.anthologyService.create(createAnthologyRequestBo);
    }

    @PatchMapping(path = "/anthology/{anthologyId}")
    UpdateAnthologyResponseBo update(@PathVariable("anthologyId") Long anthologyId, @RequestBody UpdateAnthologyRequestBo updateAnthologyRequestBo) {
        updateAnthologyRequestBo.setAnthologyId(anthologyId);
        return this.anthologyService.update(updateAnthologyRequestBo);
    }
}
