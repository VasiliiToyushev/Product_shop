package com.geekbrains.demoboot.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "products_history")
public class ProductHistory {
    @Id
    private Integer id;

    private String title;

    private BigDecimal price;

    private int views;


    @Override
    public String toString() {
        return String.format("id: %d название: %s цена: %s просмотров: %d ", id, title, price.toString(), views);
    }
}
