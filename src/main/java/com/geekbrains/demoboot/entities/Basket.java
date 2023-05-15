package com.geekbrains.demoboot.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private BigDecimal sum;
    private int count;

    @ManyToMany
    @JoinTable(name = "basket_products_history",
            joinColumns = {@JoinColumn(name = "basket_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_history_id")}
    )
    private List<ProductHistory> basketProduct = new ArrayList<>();
}
