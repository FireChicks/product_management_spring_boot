package com.example.estimate.demo.controller;

import com.example.estimate.demo.dto.Product;
import com.example.estimate.demo.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductController {
    private Logger logger = LoggerFactory.getLogger(MainController.class);
    private String productDirectory = "product/";

    @Autowired
    ProductService productService;
    @GetMapping("/productSearch")
    public String productSearch(){
        /*List<Product> products = productService.getAllProduct();
        for(Product product : products){
            logger.info("prodID : " + product.getProdID());
        }*/
        return productDirectory + "productSearch";
    }
}
