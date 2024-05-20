package com.example.estimate.demo.service;

import com.example.estimate.demo.dto.Product;
import com.example.estimate.demo.dto.ProductD_DTO;
import com.example.estimate.demo.page.Searchable;
import com.example.estimate.demo.repository.ProductD_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductD_Service {
    @Autowired
    ProductD_Repository productD_repository;

    public boolean isDuplicated(String prodID){
       int count = productD_repository.findByProdIDByList(prodID);
        if(count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isDuplicated(String prodID, Date fromDate){
        List<ProductD_DTO> dtoList;
        dtoList = productD_repository.findByProdIDAndFromDate(prodID, fromDate);
        if(dtoList.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public void addProductD(ProductD_DTO productD_dto){
        productD_repository.save(productD_dto);
    }

    public void editProductD(ProductD_DTO productD_dto){
        Long ID = productD_repository.checkID();
        productD_dto.setId(ID + 1);
        productD_repository.save(productD_dto);
    }

    public void removeProductD(ProductD_DTO productD_dto){
        productD_repository.delete(productD_dto);
        return;
    }

    public ArrayList<ProductD_DTO> getAllProductD(){
        ArrayList<ProductD_DTO> productD_DTOs = new ArrayList<>(productD_repository.findAll());

        return productD_DTOs;
    }

    public ProductD_DTO getProductD(String prodID){
        Optional<ProductD_DTO> optionalProductDDTO = productD_repository.findByProdID(prodID);

        if(optionalProductDDTO.isPresent()) {
            return optionalProductDDTO.get();
        } else{
            return null;
        }
    }

    public Map<String, Object> getAllProduct(Pageable pageable, Searchable searchable){
        String category = searchable.getSearchCategory();
        Page<ProductD_DTO> productObjects;

        productObjects = productD_repository.joinProductDAndH(pageable);

        List<Product> products = new ArrayList<>();
        for(ProductD_DTO source : productObjects){
            ProductD_DTO d_dto = source;
            Product product = new Product(d_dto,d_dto.getProductH());

            products.add(product);
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("products", products);
        resultMap.put("pages", productObjects.getTotalPages() - 1);
        resultMap.put("nowPage", productObjects.getNumber());

        return resultMap;
    }
}
