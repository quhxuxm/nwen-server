package online.nwen.server.service.api;

import online.nwen.server.bo.LabelBo;
import online.nwen.server.domain.Label;

public interface ILabelService {
    Label getAndCreateIfAbsent(String text);

    LabelBo convert(Label label);
}
