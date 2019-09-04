package online.nwen.server.dao.api;

import online.nwen.server.domain.Label;

public interface ILabelDao {
    Label save(Label label);

    Label getById(Long id);

    Label getByText(String text);
}
