package com.example.estimate.demo.repository;

import com.example.estimate.demo.dto.ProductH_DTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductH_Repository extends JpaRepository<ProductH_DTO, String> {

    /*@Query("SELECT d" +
            "FROM ProductD_DTO AS d " +
            "LEFT JOIN ProductH_DTO AS h ON h.prodID = d.prodID")
    List<Object> joinProductDAndH();*/
}
