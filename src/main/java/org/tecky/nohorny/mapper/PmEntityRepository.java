package org.tecky.nohorny.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tecky.nohorny.entities.PmEntity;
import org.tecky.nohorny.entities.PmStatusEntity;

import java.util.List;

public interface PmEntityRepository extends JpaRepository<PmEntity, String> {

    public PmEntity findByUuid(String uuid);

    public List<PmEntity> findByFromuidInAndTouidIn(@Param(value = "fromuid") List<Integer> bothId,
                                                                @Param(value =  "touid") List<Integer> bothId2);



}