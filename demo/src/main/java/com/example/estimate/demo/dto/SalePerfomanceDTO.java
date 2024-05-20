package com.example.estimate.demo.dto;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Entity(name = "sale_perfomance")
public class SalePerfomanceDTO {

    @Id
    @Column(name = "record_id", columnDefinition = "char(9)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recordID;

    @Column(name="sale_id", columnDefinition = "char(9)")
    private String saleID;

    @Column(name = "prod_id", columnDefinition = "char(9)")
    private String prodID;

    @Column(name = "prod_inp_date", columnDefinition = "timestamp")
    private Date prodInpDate;

    @Column(name = "user_id", columnDefinition = "char(7)")
    private String userID;

    @Column(name = "sale_target", columnDefinition = "varchar(30)")
    private String saleTarget;

    @Column(name = "sale_count", columnDefinition = "int")
    private int saleCount;

    @Column(name="sale_date", columnDefinition = "timestamp")
    private Date saleDate;

    @Column(name="update_date", columnDefinition = "timestamp")
    private Date updateDate;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private UserDTO userDTO;

    public SalePerfomanceDTO() {
    }

    public SalePerfomanceDTO(int recordID, String saleID, String prodID, Date prodInpDate, String userID, String saleTarget, int saleCount, Date saleDate, Date updateDate) {
        this.recordID = recordID;
        this.saleID = saleID;
        this.prodID = prodID;
        this.prodInpDate = prodInpDate;
        this.userID = userID;
        this.saleTarget = saleTarget;
        this.saleCount = saleCount;
        this.saleDate = saleDate;
        this.updateDate = updateDate;
    }

    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public String getSaleID() {
        return saleID;
    }

    public void setSaleID(String saleID) {
        this.saleID = saleID;
    }

    public String getProdID() {
        return prodID;
    }

    public void setProdID(String prodID) {
        this.prodID = prodID;
    }

    public Date getProdInpDate() {
        return prodInpDate;
    }

    public void setProdInpDate(Date prodInpDate) {
        this.prodInpDate = truncateTime(prodInpDate);
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSaleTarget() {
        return saleTarget;
    }

    public void setSaleTarget(String saleTarget) {
        this.saleTarget = saleTarget;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = truncateTime(saleDate);
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public static Date truncateTime(Date date) {
        if (date == null) {
            return null;
        }
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
