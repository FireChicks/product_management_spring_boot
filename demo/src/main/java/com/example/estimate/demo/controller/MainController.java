package com.example.estimate.demo.controller;

import com.example.estimate.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {

    private Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    UserService userService;

    private String menuDirectory = "menu/";

    @GetMapping("/login")
    public String login(){
        return menuDirectory + "login";
    }

    @GetMapping("/loginAction")
    public String loginAction(HttpServletRequest request, @RequestParam(required = true, name = "inputID") String userID,
                                                                                    @RequestParam(required = true, name = "inputPW") String userPW){
        userID = getUserID(userID);
        userPW = getUserPW(userPW);
        HttpSession session = request.getSession();

        int isLoginCorrect = userService.login(userID, userPW);

        if(isLoginCorrect == 1){
            session.setAttribute("userID", userID);
            logger.info("userID: " + userID + "がログインしました。");
        }else if (isLoginCorrect == 0){
            session.setAttribute("message", "ID/PWが一緒しません。");
            return "redirect:login";
        } else{
            session.setAttribute("message", "予想出来ない理由でログイン出来ませんでした。");
            return "redirect:login";
        }

        return "redirect:menu";
    }

    @GetMapping("/menu")
    public String menu(){
        return menuDirectory + "menu";
    }


    public String getUserID(String userID){
        int userIDLength = 7;
        userID = userID.replace("-","");

        if(userID.length() == userIDLength){
            return userID;
        } else {
            return "";
        }
    }
    public String getUserPW(String userPW){
        int userPWMinLength = 8;
        int userPWMaxLength = 20;
        userPW = userPW.replaceAll("[^a-zA-Z0-9!@#$]", "").trim();

        if(userPW.length() <= userPWMaxLength && userPW.length() >= userPWMinLength){
            return userPW;
        } else {
            return "";
        }
    }
}
