package com.geekbrains.demoboot.repositories;

import com.geekbrains.demoboot.entities.ProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Integer> {
}
