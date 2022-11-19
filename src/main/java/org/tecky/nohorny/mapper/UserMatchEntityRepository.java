package org.tecky.nohorny.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tecky.nohorny.entities.UserMatchEntity;

public interface UserMatchEntityRepository extends JpaRepository<UserMatchEntity, String> {
}