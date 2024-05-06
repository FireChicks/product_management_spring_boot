package com.example.estimate.demo.service;

import com.example.estimate.demo.dto.ProductD_DTO;
import com.example.estimate.demo.repository.ProductD_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ProductD_Service {
    @Autowired
    ProductD_Repository productD_repository;

    public ArrayList<ProductD_DTO> getAllProductD(){
        ArrayList<ProductD_DTO> productD_DTOs = new ArrayList<>(productD_repository.findAll());

        return productD_DTOs;
    }

    public ProductD_DTO getProductD(String prodID){
        Optional<ProductD_DTO> optionalProductDDTO = productD_repository.findById(prodID);

        if(optionalProductDDTO.isPresent()) {
            return optionalProductDDTO.get();
        } else{
            return null;
        }
    }
}
