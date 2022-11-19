package org.tecky.nohorny.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tecky.nohorny.entities.PmContentEntity;

public interface PmContentEntityRepository extends JpaRepository<PmContentEntity, String> {

    public PmContentEntity findByPmid(Integer pmid);

}