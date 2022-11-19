package org.tecky.nohorny.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tecky.nohorny.entities.UserContactEntity;

import java.util.List;

public interface UserContactEntityRepository extends JpaRepository<UserContactEntity, String> {

    public List<UserContactEntity> findByUid(Integer uid);
}