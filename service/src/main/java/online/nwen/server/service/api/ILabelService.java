package online.nwen.server.service.api;

import online.nwen.server.bo.LabelBo;
import online.nwen.server.domain.Label;

import java.util.List;
import java.util.Set;

public interface ILabelService {
    Label getAndCreateIfAbsent(String text);

    LabelBo convert(Label label);

    Set<Label> getWithTexts(Set<String> labelTexts);

    List<LabelBo> getTopNLabels(Integer number);

    List<LabelBo> getByTextLikeOrderByPopularFactor(String text);
}
