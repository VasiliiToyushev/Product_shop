package com.geekbrains.demoboot.services;

import com.geekbrains.demoboot.entities.Basket;
import com.geekbrains.demoboot.entities.Product;
import com.geekbrains.demoboot.entities.ProductHistory;
import com.geekbrains.demoboot.repositories.BasketRepository;
import com.geekbrains.demoboot.repositories.ProductHistoryRepository;
import com.geekbrains.demoboot.repositories.ProductRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Data
@RequiredArgsConstructor
@Service
public class BasketService {
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;
    private final ProductHistoryRepository productHistoryRepository;

    private List<Product> products = new ArrayList<>();

    public void addBasket(Product product, int count){
        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        products.add(optionalProduct.get());
    }

    public Basket showBasket(){
        BigDecimal sum = new BigDecimal("0.0");
        Basket basket = new Basket();

        for (Product product : products) {
            BigDecimal productPrice = product.getPrice();
            sum = sum.add(productPrice);
        }
        basket.setSum(sum);
        basket.setCount(products.size());
        return basket;
    }
    public List<Product> showListProduct(){
        return products;
    }
    public void saveBasket(){
        BigDecimal sum = new BigDecimal("0.0");
        Basket basket = new Basket();

        List<ProductHistory> productHistoryList = new ArrayList<>();

        for (Product product : products) {
            BigDecimal productPrice = product.getPrice();
            sum = sum.add(productPrice);

            addProductHistoryList(product, productHistoryList);
        }
        basket.setBasketProduct(productHistoryList);
        basket.setSum(sum);
        basket.setCount(products.size());
        basketRepository.save(basket);
        products.clear();
        productHistoryList.clear();
    }

    private void addProductHistoryList(Product product, List<ProductHistory> productHistoryList){
        ProductHistory productHistory = new ProductHistory();
        productHistory.setId(product.getId());
        productHistory.setTitle(product.getTitle());
        productHistory.setPrice(product.getPrice());
        productHistory.setViews(product.getViews());
        productHistoryRepository.save(productHistory);
        productHistoryList.add(productHistory);
    }

    public List<Basket> showAllBasket(){
        return basketRepository.findAll();
    }

    public void deleteProductBasket(int id){
        Product product = null;
        for (Product p : products){
            if (p.getId() == id)product = p;
        }
        products.remove(product);
    }
}
