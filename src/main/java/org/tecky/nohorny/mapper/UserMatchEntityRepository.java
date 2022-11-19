package org.tecky.nohorny.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tecky.nohorny.entities.UserMatchEntity;

import java.util.List;

public interface UserMatchEntityRepository extends JpaRepository<UserMatchEntity, String> {

    List<UserMatchEntity> findByUid(Integer uid);
}