package com.cacib.messageservice.repositories;

import com.cacib.messageservice.entities.Message;
import com.cacib.messageservice.enums.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IMessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByStatus(MessageStatus status);

    @Query("SELECT m FROM Message m WHERE m.receivedAt BETWEEN :startDate AND :endDate")
    List<Message> findMessagesInTimeRange(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT m FROM Message m WHERE m.status = :status AND m.queueName = :queueName")
    List<Message> findByStatusAndQueue(MessageStatus status, String queueName);
}