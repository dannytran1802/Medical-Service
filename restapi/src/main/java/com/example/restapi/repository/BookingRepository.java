package com.example.restapi.repository;

import com.example.restapi.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByProgress(String progress);

    @Query(value = "SELECT * FROM booking where time_order BETWEEN :startDate and :endDate order by time_order DESC", nativeQuery = true)
    List<Booking> findReportBooking(String startDate, String endDate);

    @Query(value = "SELECT * FROM booking where account_id = :accountId AND progress LIKE :progress", nativeQuery = true)
    List<Booking> findAllByAccountProgress(long accountId, String progress);

    @Query(value = "SELECT * FROM booking where ambulance_id = :ambulanceId", nativeQuery = true)
    List<Booking> findAllByAmbulance(long ambulanceId);

    @Query(value = "SELECT * FROM booking where ambulance_id = :ambulanceId AND progress LIKE :progress", nativeQuery = true)
    List<Booking> findAllByAmbulanceAndProgress(long ambulanceId, String progress);

}
