package com.example.estimate.demo.repository;

import com.example.estimate.demo.dto.Product;
import com.example.estimate.demo.dto.ProductD_DTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProductD_Repository extends JpaRepository<ProductD_DTO, Long>{

    List<ProductD_DTO> findByProdIDAndFromDate(String prodID, Date fromDate);

    @Query("SELECT count(d) " +
            "FROM com.example.estimate.demo.dto.ProductD_DTO d " +
            "WHERE d.prodID = :prodID")
    int findByProdIDByList(String prodID);
    Optional<ProductD_DTO> findByProdID(String prodID);

    @Query("SELECT d " +
            "FROM com.example.estimate.demo.dto.ProductD_DTO d")
    Page<ProductD_DTO> joinProductDAndH(Pageable pageable);


    @Query("SELECT max(d.ID) " +
            "FROM com.example.estimate.demo.dto.ProductD_DTO d")
    Long checkID();

}
