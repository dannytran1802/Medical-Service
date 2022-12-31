package com.example.restapi.repository;

import com.example.restapi.model.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

    @Query(value = "SELECT * FROM order_detail WHERE order_id = :orderId", nativeQuery = true)
    List<OrderDetails> findByOrderId(long orderId);

}

