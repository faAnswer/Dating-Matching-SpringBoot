package org.tecky.nohorny.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tecky.nohorny.entities.UserInfoEntity;

public interface UserInfoEntityRepository extends JpaRepository<UserInfoEntity, String> {

    public UserInfoEntity findByUid(Integer uid);

}