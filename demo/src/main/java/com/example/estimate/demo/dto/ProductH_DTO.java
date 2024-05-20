package com.example.estimate.demo.dto;

import jakarta.persistence.*;

@Entity(name = "product_h")
@Table
public class ProductH_DTO {

    @Id
    @Column(name = "prod_id")
    private String prodID;

    @Column(name = "prod_name", columnDefinition = "varchar(20)")
    private String prodName;

    public ProductH_DTO() {
    }

    public ProductH_DTO(String prodId, String prodName) {
        this.prodID = prodId;
        this.prodName = prodName;
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
}
