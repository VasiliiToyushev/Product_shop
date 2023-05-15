package com.geekbrains.demoboot.entities;


import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Component
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private BigDecimal price;

    private int views;

    @Override
    public String toString() {
        return String.format("id: %d название: %s цена: %s просмотров: %d ", id, title, price.toString(), views);
    }


}
