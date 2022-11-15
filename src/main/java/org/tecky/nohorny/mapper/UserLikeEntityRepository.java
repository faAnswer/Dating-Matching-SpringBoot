package org.tecky.nohorny.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.tecky.nohorny.entities.UserLikeEntity;

public interface UserLikeEntityRepository extends JpaRepository<UserLikeEntity, String> {

    public UserLikeEntity findByFromuidAndTouid(@Param("fromuid") int fromUid, @Param("touid") int toUid);
}