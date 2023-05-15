package com.geekbrains.demoboot.services;

import com.geekbrains.demoboot.entities.Product;
import com.geekbrains.demoboot.repositories.ProductRepository;
import com.geekbrains.demoboot.repositories.specification.ProductSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductRepository productRepository;


    private Specification<Product> productSpecification(String word, BigDecimal minPrice, BigDecimal maxPrice) {
        Specification<Product> specification = Specification.where(null);
        if (word != null) specification = specification.and(ProductSpecs.titleContains(word));

        if (minPrice != null) specification = specification.and(ProductSpecs.priceGreaterThanOrEq(minPrice));

        if (maxPrice != null) specification = specification.and(ProductSpecs.priceLesserThanOrEq(maxPrice));

        return specification;
    }

    public Page<Product> getProductWithPagingAndFiltering(String word, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        Specification<Product> specification = productSpecification(word, minPrice, maxPrice);
        return productRepository.findAll(specification, pageable);
    }

    public Product getById(Integer id) { return productRepository.findById(id).orElse(null); }

    public void saveOrUpdate(Product product) { productRepository.save(product); }

    public void delete(Integer id) { productRepository.deleteById(id); }

    public Product incrementViewsCounter(Product product) {
        product.setViews(product.getViews() + 1);
        return productRepository.save(product);
    }

    public List<Product> getTop3List(){
        return productRepository.findTop3ByOrderByViewsDesc();
    }

    public long allProducts(){
        return productRepository.count();
    }


}
