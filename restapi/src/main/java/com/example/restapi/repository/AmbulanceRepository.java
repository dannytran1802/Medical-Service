package com.example.restapi.repository;

import com.example.restapi.model.entity.Account;
import com.example.restapi.model.entity.Ambulance;
import com.example.restapi.model.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AmbulanceRepository extends JpaRepository<Ambulance, Long> {

    Ambulance findByNumberPlate(String numberPlate);
    Ambulance findByAccount(Account account);
}
