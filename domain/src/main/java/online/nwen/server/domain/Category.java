package online.nwen.server.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbnwen_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Long id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "description", length = 2000)
    private String description;
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
