package org.tecky.nohorny.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tecky.nohorny.entities.UserEntity;

public interface UserEntityRespostity extends JpaRepository<UserEntity, String> {

    public UserEntity findByEmail(String email);

    public UserEntity findByUsername(String email);

}
