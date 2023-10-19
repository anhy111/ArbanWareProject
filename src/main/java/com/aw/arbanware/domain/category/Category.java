package com.aw.arbanware.domain.category;

import com.aw.arbanware.domain.common.DeleteYn;
import com.aw.arbanware.domain.common.baseentity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category extends BaseEntity implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
                        generator = "category_seq")
    @SequenceGenerator(name = "category_seq",sequenceName = "CATEGORY_SEQUENCE",allocationSize = 1)
    @Column(name = "CATEGORY_ID")
    private Long id;    // 카테고리 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Category parent;    // 상위카테고리

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Category> childs = new ArrayList<>();

    private String name;    // 카테고리명

    @Enumerated(EnumType.STRING)
    private DeleteYn deleteYn; //삭제여부

    public Category() {
    }

    public Category(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", parent=" + parent +
                ", name='" + name + '\'' +
                ", deleteYn=" + deleteYn +
                "} " + super.toString();
    }
}
