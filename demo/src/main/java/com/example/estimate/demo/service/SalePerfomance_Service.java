package com.example.estimate.demo.service;

import com.example.estimate.demo.dto.SalePerfomanceDTO;
import com.example.estimate.demo.page.Searchable;
import com.example.estimate.demo.repository.SalePerfomance_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SalePerfomance_Service {
    @Autowired
    SalePerfomance_Repository salePerfomanceRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProductH_Service productHService;

    public List<SalePerfomanceDTO> getAllPerfomance() {
        List<SalePerfomanceDTO> salePerfomanceDTOList = salePerfomanceRepository.findAll();

        return salePerfomanceDTOList;
    }

    public Page<SalePerfomanceDTO> getAllPerfomanceBySearch(Pageable pageable, Searchable searchable) {
        String category = searchable.getSearchCategory();
        Page<SalePerfomanceDTO> sales = null;

        if(category.equals("saleID")) {
            sales = salePerfomanceRepository.searchSalesByID(pageable, searchable.getSearchText());
        } else if(category.equals("saleTarget")){
            sales = salePerfomanceRepository.searchSalesByTarget(pageable, searchable.getSearchText());
        } else if(category.equals("userName")) {
            sales = salePerfomanceRepository.searchSalesByUserName(pageable, searchable.getSearchText());
        }

        return sales;
    }

    public SalePerfomanceDTO getPerfomance(int id){
        Optional<SalePerfomanceDTO> optionalSalePerfomanceDTO = salePerfomanceRepository.findById(id);

        if(optionalSalePerfomanceDTO.isPresent()){
            return optionalSalePerfomanceDTO.get();
        } else {
            return null;
        }
    }

    public boolean checkIsExistSaleID(String saleID){
        List<SalePerfomanceDTO> dto = salePerfomanceRepository.findBySaleID(saleID);

        if(dto.size() == 0) {
            return true;
        }else {
            return false;
        }
    }

    public List<SalePerfomanceDTO> getSalesBySaleID(String saleID){
        List<SalePerfomanceDTO> sales = salePerfomanceRepository.getSalesByID(saleID);

        if (sales.size() == 0) {
            return null;
        } else {
            return sales;
        }
    }

    public int saveAllSales(List<SalePerfomanceDTO> dtos) {
        try {
            salePerfomanceRepository.saveAll(dtos);
            return 1;
        }catch(Exception e) {
            return -1;
        }
    }

    public int updateAllSales(List<SalePerfomanceDTO> dtos) {
        Date nowDate = new Date();
        for(SalePerfomanceDTO dto : dtos) {
            dto.setUpdateDate(nowDate);
        }

        try {
            salePerfomanceRepository.saveAll(dtos);
            return 1;
        }catch(Exception e) {
            return -1;
        }
    }

    public int deleteSales(List<SalePerfomanceDTO> dtos){
        try {
            for (SalePerfomanceDTO dto : dtos) {
                salePerfomanceRepository.delete(dto);
            }
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }


}
