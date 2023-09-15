package com.aw.arbanware.domain.category;

import com.aw.arbanware.domain.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category extends BaseEntity implements Serializable {
    @Id @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Long id;

    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

//    @OneToMany(mappedBy = "parent", orphanRemoval = true)
//    private List<Category> childs = new ArrayList<>();

    public Category() {
    }

    public Category(final String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", parent=" + parent +
                "} " + super.toString();
    }
}
