package online.nwen.server.dao.api;

import online.nwen.server.domain.Label;

import java.util.List;

public interface ILabelDao {
    Label save(Label label);

    Label getById(Long id);

    Label getByText(String text);

    List<Label> getTopNLabels(int n);

    List<Label> getByTextLikeOrderByPopularFactor(String text);
}
