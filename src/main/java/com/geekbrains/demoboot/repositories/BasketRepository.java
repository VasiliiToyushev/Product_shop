package com.geekbrains.demoboot.repositories;

import com.geekbrains.demoboot.entities.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Integer> {

}
