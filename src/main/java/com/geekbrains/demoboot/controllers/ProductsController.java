package com.geekbrains.demoboot.controllers;

import com.geekbrains.demoboot.entities.Basket;
import com.geekbrains.demoboot.entities.Product;
import com.geekbrains.demoboot.entities.User;
import com.geekbrains.demoboot.services.BasketService;
import com.geekbrains.demoboot.services.ProductsService;
import com.geekbrains.demoboot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ProductsController {
    private final ProductsService productsService;
    private final UserService userService;
    private final BasketService basketService;

    @GetMapping
    public String showProductsList(Principal principal, Model model,
                                   @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "word", required = false) String word,
                                   @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
                                   @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice) {

        if (principal != null) {
            User user = userService.principalGetUser(principal);
            model.addAttribute("username", user.getName());
        }
        if (page == null)page = 1;
        Page<Product> productPage = productsService.getProductWithPagingAndFiltering(
                word, minPrice, maxPrice, PageRequest.of(page - 1, 5));
        model.addAttribute("top3List", productsService.getTop3List());
        model.addAttribute("allProducts", productsService.allProducts());
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("word", word);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        return "products";
    }

    @GetMapping("/show/{id}")
    public String showOneProduct(Model model, @PathVariable(value = "id") Integer id) {
        Product product = productsService.getById(id);
        product = productsService.incrementViewsCounter(product);
        model.addAttribute("product", product);
        return "product-page";
    }

    @GetMapping("/delete/{id}")
    @Secured(value = "ROLE_ADMIN")
    public String deleteOneProduct(@PathVariable(value = "id") Integer id) {
        productsService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/deleteProductBasket/{id}")
    @Secured(value = "ROLE_USER")
    public String deleteProductBasket(@PathVariable(value = "id") Integer id){
        basketService.deleteProductBasket(id);
        return "redirect:/basket";
    }

    @GetMapping("/add")
    @Secured(value = "ROLE_ADMIN")
    public String showAddProductForm(Model model){
        Product product = new Product();
        model.addAttribute("product", product);
        return "product-edit";
    }

    @GetMapping("/edit/{id}")
    @Secured(value = "ROLE_ADMIN")
    public String showEditProductForm(Model model, @PathVariable(value = "id") Integer id){
        Product product = productsService.getById(id);
        model.addAttribute("product", product);
        return "product-edit";
    }

    @PostMapping("/edit")
    @Secured(value = "ROLE_ADMIN")
    public String addProduct(@ModelAttribute(value = "product")Product product) {
        productsService.saveOrUpdate(product);
        return "redirect:/";
    }

    @GetMapping("/reg")
    public String showAddUser(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "reg";
    }

    @PostMapping("/reg")
    public String addUser(@RequestParam(value = "userName", required = false) String userName,
                          @RequestParam(value = "name", required = false) String name,
                          @RequestParam(value = "password", required = false) String password,
                          @RequestParam(value = "email", required = false) String email){
        userService.saveUser(userName, name, password, email);

        return "redirect:/";
    }

    @GetMapping("/addBasket/{id}")
    public String addBasket(@ModelAttribute(value = "product")Product product){
        basketService.addBasket(product,1);
        return "redirect:/";
    }

    @GetMapping("/basket")
    public String showBasket(Model model){
        Basket basket = basketService.showBasket();
        model.addAttribute("basket", basket);
        model.addAttribute("showListProduct", basketService.showListProduct());
        return "basket";
    }

    @PostMapping("/basket")
    public String saveBasket() {
        basketService.saveBasket();
        return "redirect:/";
    }

    @GetMapping("/allBasket")
    public String showAllBasket(Model model){
        model.addAttribute("showAllBasket", basketService.showAllBasket());
        return "showAllBasket";
    }
}
