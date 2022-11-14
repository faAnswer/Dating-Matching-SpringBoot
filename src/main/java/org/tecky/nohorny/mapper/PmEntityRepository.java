package org.tecky.nohorny.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tecky.nohorny.entities.PmEntity;

public interface PmEntityRepository extends JpaRepository<PmEntity, String> {

    public PmEntity findByUuid(String uuid);
}