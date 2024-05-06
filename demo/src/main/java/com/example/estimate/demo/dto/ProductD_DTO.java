package com.example.estimate.demo.dto;

import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "product_d")
@Table
public class ProductD_DTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id", columnDefinition = "char(9)")
    private String prodID;

    @Column(name = "prod_price", columnDefinition = "int")
    private int prodPrice;

    @Column(name = "from_date", columnDefinition = "timestamp")
    private Date fromDate;

    @Column(name = "to_date", columnDefinition = "timestamp")
    private Date toDate;

    @Column(name = "prod_stock", columnDefinition = "int")
    private int prodStock;

    @ManyToOne
    @JoinColumn(name = "prod_id", referencedColumnName = "prod_id", insertable = false, updatable = false)
    private ProductH_DTO productH;

    public ProductD_DTO() {
    }

    public ProductD_DTO(String prodID, int prodPrice, Date fromDate, Date toDate, int prodStock) {
        this.prodID = prodID;
        this.prodPrice = prodPrice;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.prodStock = prodStock;
    }

    public String getProdID() {
        return prodID;
    }

    public void setProdID(String prodID) {
        this.prodID = prodID;
    }

    public int getProdPrice() {
        return prodPrice;
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
