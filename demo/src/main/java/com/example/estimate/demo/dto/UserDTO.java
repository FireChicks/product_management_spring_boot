package com.example.estimate.demo.dto;

import jakarta.persistence.*;

@Entity(name = "user")
@Table
public class UserDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", columnDefinition = "char(7)")
    private String userID;

    @Column(name = "user_pwd", columnDefinition = "varchar(40)")
    private String userPWD;

    @Column(name = "user_name", columnDefinition = "varchar(20)")
    private String userName;

    @Column(name = "user_affiliation", columnDefinition = "varchar(60)")
    private String userAffiliation;

    public UserDTO(){

    }

    public UserDTO(String userID, String userPWD, String userName, String userAffiliation){
        this.userID = userID;
        this.userPWD = userPWD;
        this.userName = userName;
        this.userAffiliation = userAffiliation;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPWD() {
        return userPWD;
    }

    public void setUserPWD(String userPWD) {
        this.userPWD = userPWD;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAffiliation() {
        return userAffiliation;
    }

    public void setUserAffiliation(String userAffiliation) {
        this.userAffiliation = userAffiliation;
    }

    public boolean checkPasswordMatch(String inputPassword){
        if(this.userPWD.equals(inputPassword)){
            return true;
        }else{
            return false;
        }
    }



}
