package org.tecky.nohorny.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tecky.nohorny.entities.PmEntity;
import org.tecky.nohorny.entities.PmStatusEntity;
import org.tecky.nohorny.entities.UserEntity;

import java.util.List;

public interface PmEntityRepository extends JpaRepository<PmEntity, String> {

    public PmEntity findByUuid(String uuid);

    public List<PmEntity> findByFromuidInAndTouidInOrderByPmidAsc(@Param(value = "fromuid") List<Integer> bothId,
                                                                @Param(value =  "touid") List<Integer> bothId2);

    public List<PmEntity> findByFromuidInAndTouidInOrderByPmidDesc(@Param(value = "fromuid") List<Integer> bothId,
                                                                    @Param(value =  "touid") List<Integer> bothId2);


    @Query(nativeQuery = true, value = "SELECT tpm.pmid, tpm.fromUid, tpm.toUid, tpm.uuid FROM t_pm AS tpm INNER JOIN t_pm_status AS tps ON tps.pmId = tpm.pmid WHERE tpm.fromUid = ?2 AND tpm.toUid = ?1 AND tps.isread = 0" )
    public List<PmEntity> findAllUnReadbyUid(int selfUid, int contractUid);

}