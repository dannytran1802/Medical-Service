package com.example.restapi.repository;
import com.example.restapi.model.entity.Booking;
import com.example.restapi.model.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query(value = "SELECT * FROM location where account_id = :accountId", nativeQuery = true)
    Optional<Location> findByAccountId(long accountId);

}
