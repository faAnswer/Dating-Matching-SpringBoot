package org.tecky.nohorny.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tecky.nohorny.entities.OrderEntity;

public interface OrderEntityRepository extends JpaRepository<OrderEntity, String> {
}