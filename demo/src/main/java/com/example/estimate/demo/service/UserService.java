package com.example.estimate.demo.service;

import com.example.estimate.demo.dto.UserDTO;
import com.example.estimate.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public int login(String userID,String userPW) {
        Optional<UserDTO> userDTOOptional = userRepository.findById(userID);
        if(userDTOOptional.isPresent()) {
            UserDTO userDTO = userDTOOptional.get();
            if(userDTO.getUserID().equals(userID)){
                if(userDTO.checkPasswordMatch(userPW)) {
                    return 1;
                }else{
                    return 0;
                }
            }
        } else {
           return 0;
        }
        return -1;
    }

    public String getUserName(String userID){
        return userRepository.getUserName(userID);
    }

    public UserDTO getUserInfo(String userID){
        Optional<UserDTO> userDTOOptional = userRepository.findById(userID);

        if(userDTOOptional.isPresent()) {
            return userDTOOptional.get();
        } else{
            return null;
        }
    }


}
