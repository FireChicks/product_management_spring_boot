package com.example.estimate.demo.service;

import com.example.estimate.demo.dto.ProductH_DTO;
import com.example.estimate.demo.repository.ProductH_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ProductH_Service {
    @Autowired
    ProductH_Repository productH_Repository;

    public ArrayList<ProductH_DTO> getAllProductH(){
        ArrayList<ProductH_DTO> productH_DTOs = new ArrayList<ProductH_DTO>(productH_Repository.findAll());

        return productH_DTOs;
    }

    public ProductH_DTO getProductH(String prodID){
        Optional<ProductH_DTO> optionalProductHDTO = productH_Repository.findById(prodID);

        if(optionalProductHDTO.isPresent()) {
            return optionalProductHDTO.get();
        } else {
            return null;
        }
    }
}
