package com.example.estimate.demo.repository;

import com.example.estimate.demo.dto.SalePerfomanceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SalePerfomance_Repository extends JpaRepository<SalePerfomanceDTO,Integer> {

    @Query("SELECT s " +
    "FROM sale_perfomance s " +
    "WHERE INSTR(s.saleID, :searchText) > 0 " +
    "GROUP BY saleID")
    Page<SalePerfomanceDTO> searchSalesByID(Pageable pageable, String searchText);

    @Query("SELECT s " +
            "FROM sale_perfomance s " +
            "WHERE INSTR(s.saleTarget, :searchText) > 0 " +
            "GROUP BY saleID")
    Page<SalePerfomanceDTO> searchSalesByTarget(Pageable pageable, String searchText);

    @Query("SELECT s " +
            "FROM sale_perfomance s " +
            "LEFT JOIN com.example.estimate.demo.dto.UserDTO u on s.userID = u.userID " +
            "WHERE INSTR(u.userName, :searchText) > 0 " +
            "GROUP BY saleID")
    Page<SalePerfomanceDTO> searchSalesByUserName(Pageable pageable, String searchText);

    /*@Query("SELECT s " +
            "FROM sale_perfomance s " +
            "LEFT JOIN s.com.example.estimate.demo.dto.UserDTO h " +
            "WHERE INSTR(u.userName, :searchText) > 0 " +
            "GROUP BY saleID")
    Page<SalePerfomanceDTO> searchSalesByUserName(Pageable pageable, String searchText);*/

    @Query("SELECT s " +
            "FROM sale_perfomance s " +
            "WHERE saleID = :saleID")
    List<SalePerfomanceDTO> getSalesByID(String saleID);

    List<SalePerfomanceDTO> findBySaleID(String saleID);

}
