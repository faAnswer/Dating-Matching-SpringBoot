package org.tecky.nohorny.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tecky.nohorny.entities.UserInfoEntity;

import java.util.List;

public interface UserInfoEntityRepository extends JpaRepository<UserInfoEntity, String> {

    public UserInfoEntity findByUid(Integer uid);

    @Query(nativeQuery = true, value = "SELECT * FROM t_user_info WHERE gender = ?2 AND sexual = ?1 AND age > ?3 AND age < ?4 AND districtId = ?5 AND uid != ?6 LIMIT 6;")
    public List<UserInfoEntity> matchingUsers(String gender, String sexual, Integer ageBottom, Integer ageTop, Integer districtId, Integer selfUid);

}