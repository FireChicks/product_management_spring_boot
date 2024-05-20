package com.example.estimate.demo.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.text.DecimalFormat;
import java.util.Date;

@Entity
public class Product {

    @Id
    private Long ID;
    private String prodID;
    private String prodName;
    private int prodPrice;
    private Date fromDate;
    private Date toDate;
    private int prodStock;

    public Product() {
    }

    public Product(ProductD_DTO productD, ProductH_DTO productH) {
        this.ID = productD.getId();
        this.prodID = productD.getProdID();
        this.prodName = productH.getProdName();
        this.prodPrice = productD.getProdPrice();
        this.fromDate = productD.getFromDate();
        this.toDate = productD.getToDate();
        this.prodStock = productD.getProdStock();
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getProdID() {
        return prodID;
    }

    public void setProdID(String prodID) {
        this.prodID = prodID;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdPrice() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedAmount = decimalFormat.format(this.prodPrice);

        // 일본 통화 기호(￥)를 붙여서 반환
        return "￥" + formattedAmount;
    }

    public int getPrice() {
       return this.prodPrice;
    }

    public void setProdPrice(int prodPrice) {
        this.prodPrice = prodPrice;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public int getProdStock() {
        return prodStock;
    }

    public void setProdStock(int prodStock) {
        this.prodStock = prodStock;
    }
}
