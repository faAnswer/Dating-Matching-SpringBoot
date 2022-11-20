package org.tecky.nohorny.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tecky.nohorny.entities.PaymentEntity;

public interface PaymentEntityRepository extends JpaRepository<PaymentEntity, String> {
}