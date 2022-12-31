package com.example.restapi.repository;

import com.example.restapi.model.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

    @Query(value = "SELECT * FROM pharmacy where account_id = :id", nativeQuery = true)
    Pharmacy findByAccountId(long id);

}
