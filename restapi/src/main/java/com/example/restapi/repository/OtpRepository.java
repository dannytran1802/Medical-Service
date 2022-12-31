package com.example.restapi.repository;

import com.example.restapi.model.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OTP, Long> {

    Optional<OTP> findByUsername(String username);

}
