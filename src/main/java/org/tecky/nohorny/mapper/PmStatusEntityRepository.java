package org.tecky.nohorny.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tecky.nohorny.entities.PmStatusEntity;
import org.tecky.nohorny.entities.UserEntity;

import java.util.List;

public interface PmStatusEntityRepository extends JpaRepository<PmStatusEntity, String> {

    public PmStatusEntity findByPmidAndIsreadIs(@Param("pmid") int pmid, @Param("isread") int isread);


}