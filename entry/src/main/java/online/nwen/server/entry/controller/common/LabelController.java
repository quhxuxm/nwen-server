package online.nwen.server.entry.controller.common;

import online.nwen.server.bo.LabelBo;
import online.nwen.server.entry.controller.CommonApiController;
import online.nwen.server.service.api.ILabelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@CommonApiController
public class LabelController {
    private ILabelService labelService;

    public LabelController(ILabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping(path = "/label/top/{number}")
    List<LabelBo> getTopNLabels(@PathVariable(name = "number") Integer number) {
        return this.labelService.getTopNLabels(number);
    }

    @GetMapping(path = "/label/like")
    List<LabelBo> getByTextLikeOrderByPopularFactor(@RequestParam(name = "text") String text) {
        return this.labelService.getByTextLikeOrderByPopularFactor(text);
    }
}
