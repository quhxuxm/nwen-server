package online.nwen.server.entry.controller;

import online.nwen.server.bo.LabelBo;
import online.nwen.server.service.api.ILabelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LabelController {
    private ILabelService labelService;

    public LabelController(ILabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping("/label/top/{number}")
    List<LabelBo> getTopNLabels(@PathVariable(name = "number") Integer number) {
        return this.labelService.getTopNLabels(number);
    }

    @GetMapping("/label/like")
    List<LabelBo> getByTextLikeOrderByPopularFactor(@RequestParam(name = "text") String text) {
        return this.labelService.getByTextLikeOrderByPopularFactor(text);
    }
}
