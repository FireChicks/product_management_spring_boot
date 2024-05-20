package com.example.estimate.demo.dto;

import com.example.estimate.demo.service.ProductH_Service;
import com.example.estimate.demo.service.UserService;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;


public class Estimate {

    private String prodName;
    private String userName;

    private int prodPrice;

    private int prodStock;

    private SalePerfomanceDTO dto;


    public Estimate(SalePerfomanceDTO dto, String prodName, int price, String userName) {
        this.dto = dto;

        this.prodName = prodName;
        this.prodPrice = price;
        this.userName = userName;

    }
    public Estimate(SalePerfomanceDTO dto, String prodName, int price, String userName, int prodStock) {
        this.dto = dto;

        this.prodName = prodName;
        this.prodPrice = price;
        this.userName = userName;
        this.prodStock = prodStock;

    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(int prodPrice) {
        this.prodPrice = prodPrice;
    }

    public int getProdStock() {
        return prodStock;
    }

    public void setProdStock(int prodStock) {
        this.prodStock = prodStock;
    }

    public SalePerfomanceDTO getDto() {
        return dto;
    }

    public void setDto(SalePerfomanceDTO dto) {
        this.dto = dto;
    }
}
