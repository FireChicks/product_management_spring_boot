package com.example.estimate.demo.repository;

import com.example.estimate.demo.dto.ProductD_DTO;
import com.example.estimate.demo.dto.ProductH_DTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProductH_Repository extends JpaRepository<ProductH_DTO, String> {

    @Query("SELECT prodName " +
            "FROM product_h " +
            "WHERE prodID = :prodID")
    String getProdName(String prodID);

    @Query("SELECT d " +
            "FROM com.example.estimate.demo.dto.ProductD_DTO d")
    Page<ProductD_DTO> joinProductDAndH(Pageable pageable);

    @Query("SELECT d, h " +
            "FROM com.example.estimate.demo.dto.ProductD_DTO d " +
            "LEFT JOIN d.productH h " +
            "WHERE INSTR(h.prodID, :searchText) > 0 ")
    Page<Object[]> joinProductDAndHByProdID(Pageable pageable, String searchText);

    @Query("SELECT d, h " +
            "FROM com.example.estimate.demo.dto.ProductD_DTO d " +
            "LEFT JOIN d.productH h " +
            "WHERE INSTR(h.prodName, :searchText) > 0")
    Page<Object[]> joinProductDAndHByProdName(Pageable pageable, String searchText);

    @Query("SELECT d, h " +
            "FROM com.example.estimate.demo.dto.ProductD_DTO d " +
            "LEFT JOIN d.productH h " +
            "WHERE d.prodID = :prodID and d.fromDate = :fromDate")
    List<Object[]> searchProduct(String prodID , @Param("fromDate") Date fromDate);

}
