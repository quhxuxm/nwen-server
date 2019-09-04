package online.nwen.server.domain;

import javax.persistence.*;

@Entity
@Table(name = "tbnwen_label")
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "label_id")
    private Long id;
    @Column(name = "text", unique = true, length = 16)
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
