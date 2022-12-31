package com.example.restapi.repository;

import com.example.restapi.model.entity.MessageManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageManagerRepository extends JpaRepository<MessageManager, Long> {

    Optional<MessageManager> findByTitle(String title);

}
