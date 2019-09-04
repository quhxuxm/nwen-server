package online.nwen.server.service.impl;

import online.nwen.server.bo.LabelBo;
import online.nwen.server.dao.api.ILabelDao;
import online.nwen.server.domain.Label;
import online.nwen.server.service.api.ILabelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class LabelServiceImpl implements ILabelService {
    private static final Logger logger = LoggerFactory.getLogger(LabelServiceImpl.class);
    private ILabelDao labelDao;

    LabelServiceImpl(ILabelDao labelDao) {
        this.labelDao = labelDao;
    }

    @Override
    public Label getAndCreateIfAbsent(String text) {
        Label label = this.labelDao.getByText(text);
        if (label == null) {
            Label newLabel = new Label();
            newLabel.setText(text);
            try {
                label = this.labelDao.save(newLabel);
            } catch (Exception e) {
                logger.debug("Fail to create label [{}] because of exception.", text, e);
                label = this.labelDao.getByText(text);
            }
        }
        return label;
    }

    @Override
    public LabelBo convert(Label label) {
        LabelBo labelBo = new LabelBo();
        labelBo.setId(label.getId());
        labelBo.setText(label.getText());
        labelBo.setPopularFactor(label.getPopularFactor());
        return labelBo;
    }

    @Override
    public Set<Label> getWithTexts(Set<String> labelTexts) {
        Set<Label> labels = new HashSet<>();
        labelTexts.forEach(text -> {
            labels.add(this.labelDao.getByText(text));
        });
        return labels;
    }

    @Override
    public List<LabelBo> getTopNLabels(Integer number) {
        return this.labelDao.getTopNLabels(number).stream().map(this::convert).collect(Collectors.toList());
    }
}
