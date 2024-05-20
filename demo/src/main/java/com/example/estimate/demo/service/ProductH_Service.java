package com.example.estimate.demo.service;

import com.example.estimate.demo.dto.Product;
import com.example.estimate.demo.dto.ProductD_DTO;
import com.example.estimate.demo.dto.ProductH_DTO;
import com.example.estimate.demo.page.Searchable;
import com.example.estimate.demo.repository.ProductH_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductH_Service {
    @Autowired
    ProductH_Repository productH_Repository;

    public boolean isDuplicate(ProductH_DTO productH_dto){
        Optional<ProductH_DTO> optionalProductH_dto = productH_Repository.findById(productH_dto.getProdID());
        if(optionalProductH_dto.isPresent()){
            return true;
        }else{
            return false;
        }
    }

    public void addProductH(ProductH_DTO productH_dto){
        productH_Repository.save(productH_dto);
    }

    public void removeProductH(ProductH_DTO productHDto){
        productH_Repository.delete(productHDto);
    }

    public ArrayList<ProductH_DTO> getAllProductH(){
        ArrayList<ProductH_DTO> productH_DTOs = new ArrayList<ProductH_DTO>(productH_Repository.findAll());

        return productH_DTOs;
    }

    public String getProdName(String prodID){
        return productH_Repository.getProdName(prodID);
    }

    public Product getProduct(String prodID, Date fromDate){
        List<Object[]> productObjects = productH_Repository.searchProduct(prodID, fromDate);
        for(Object[] source : productObjects){
            ProductH_DTO h_dto = (ProductH_DTO) source[1];
            ProductD_DTO d_dto = (ProductD_DTO) source[0];
            Product product = new Product(d_dto,h_dto);

            return product;
        }
        return null;
    }

    public Map<String, Object> getAllProduct(Pageable pageable, Searchable searchable){
        String category = searchable.getSearchCategory();
        Page<Object[]> productObjects = null;
        if(category.equals("prodID")){
            productObjects = productH_Repository.joinProductDAndHByProdID(pageable, searchable.getSearchText());

        } else if(category.equals("prodName")){
            productObjects = productH_Repository.joinProductDAndHByProdName(pageable, searchable.getSearchText());
        }

        List<Product> products = new ArrayList<>();
        for(Object[] source : productObjects){
            ProductH_DTO h_dto = (ProductH_DTO) source[1];
            ProductD_DTO d_dto = (ProductD_DTO) source[0];
            Product product = new Product(d_dto,h_dto);

            products.add(product);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("products", products);
        resultMap.put("pages", productObjects.getTotalPages() - 1);
        resultMap.put("nowPage", productObjects.getNumber());

        return resultMap;
    }
}
