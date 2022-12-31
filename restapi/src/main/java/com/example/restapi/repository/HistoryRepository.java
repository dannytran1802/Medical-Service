package com.example.restapi.repository;

import com.example.restapi.model.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    @Query(value = "SELECT * FROM history where ambulance_id = :ambulanceId AND code LIKE :code", nativeQuery = true)
    Optional<History> findByAmbulance(long ambulanceId, String code);

    @Query(value = "SELECT * FROM history where ambulance_id = :ambulanceId AND code LIKE :code AND created_on > NOW() - INTERVAL 10 MINUTE", nativeQuery = true)
    List<History> findByAmbulanceAndTime(long ambulanceId, String code);

}
