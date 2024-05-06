package com.example.estimate.demo.service;

import com.example.estimate.demo.dto.Product;
import com.example.estimate.demo.dto.ProductD_DTO;
import com.example.estimate.demo.dto.ProductH_DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {
    @Autowired
    ProductD_Service productDService;
    @Autowired
    ProductH_Service productHService;

   /*public ArrayList<Product> getAllProduct() {
       ArrayList<Product> products;
       return products;
   }*/
}
