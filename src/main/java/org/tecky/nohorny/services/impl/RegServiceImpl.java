package org.tecky.nohorny.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tecky.nohorny.dto.JSONResponse;
import org.tecky.nohorny.entities.UserEntity;
import org.tecky.nohorny.mapper.UserEntityRespostity;
import org.tecky.nohorny.services.intf.IRegService;

@Service
public class RegServiceImpl implements IRegService{

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserEntityRespostity userEntityRespostity;

    @Override
    public ResponseEntity<JSONResponse<Object>> regNewUser(UserEntity userEntity) {

        UserEntity checkUserEntity;

        checkUserEntity = userEntityRespostity.findByUsername(userEntity.getUsername());

        if(checkUserEntity != null) {

            return JSONResponse.fail("Username is already in use", HttpStatus.CONFLICT);
        }

        checkUserEntity = userEntityRespostity.findByEmail(userEntity.getEmail());

        if(checkUserEntity != null) {

            return JSONResponse.fail("Email is already in use", HttpStatus.CONFLICT);
        }

        userEntity.setShapassword(passwordEncoder.encode(userEntity.getShapassword()));

        userEntityRespostity.save(userEntity);

        return JSONResponse.ok(userEntity);
    }
}
