package org.tecky.nohorny.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tecky.nohorny.entities.UserEntity;

import java.util.List;

public interface UserEntityRespostity extends JpaRepository<UserEntity, String> {

    public UserEntity findByEmail(String email);

    public UserEntity findByUid(Integer uid);


    public UserEntity findByUsername(String email);

    @Query(nativeQuery = true, value = "SELECT tu.uid, tu.username, tu.email, tu.shapassword FROM t_user AS tu INNER JOIN (SELECT DISTINCT tp.fromuid FROM t_pm AS tp INNER JOIN t_pm_status AS tps ON tp.pmid = tps.pmid WHERE tps.isread = 0 AND tp.touid = ?1 ) AS t1 ON t1.fromuid = tu.uid" )
    public List<UserEntity> findUnReadbyUid(int uid);




}
