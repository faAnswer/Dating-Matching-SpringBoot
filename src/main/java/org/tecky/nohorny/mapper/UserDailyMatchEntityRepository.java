package org.tecky.nohorny.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tecky.nohorny.entities.UserDailyMatchEntity;

public interface UserDailyMatchEntityRepository extends JpaRepository<UserDailyMatchEntity, String> {

    public UserDailyMatchEntity findByUid(Integer uid);

}