package org.tecky.nohorny.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tecky.nohorny.entities.RoleEntity;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, String> {

    public RoleEntity findByRoleid(Integer roleid);
}