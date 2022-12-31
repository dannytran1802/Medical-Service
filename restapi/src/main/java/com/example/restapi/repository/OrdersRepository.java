package com.example.restapi.repository;

import com.example.restapi.model.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query(value = "SELECT * FROM orders WHERE account_id = :accountId", nativeQuery = true)
    List<Orders> findByAccountId(long accountId);

    @Query(value = "SELECT * FROM orders WHERE pharmacy_id = :pharmacyId", nativeQuery = true)
    List<Orders> findByPharmacyId(long pharmacyId);

    List<Orders> findByProgress(String progress);

    @Query(value = "SELECT * FROM orders where created_on BETWEEN :startDate and :endDate order by created_on DESC", nativeQuery = true)
    List<Orders> findReportOrder(String startDate, String endDate);
}
