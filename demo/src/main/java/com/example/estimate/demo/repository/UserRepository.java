package com.example.estimate.demo.repository;

import com.example.estimate.demo.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDTO, String> {

    @Query("SELECT userName " +
            "FROM user " +
            "WHERE userID = :userID")
    String getUserName(String userID);

}
