package org.tecky.nohorny.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.tecky.nohorny.dto.JSONResponse;
import org.tecky.nohorny.entities.UserEntity;
import org.tecky.nohorny.mapper.IUserEntityRespostity;
import org.tecky.nohorny.services.intf.IRegService;

@Service
public class RegServiceImpl implements IRegService{

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    IUserEntityRespostity iUserEntityRespostity;

    @Override
    public ResponseEntity<JSONResponse<Object>> regNewUser(UserEntity userEntity) {

        UserEntity checkUserEntity;

        checkUserEntity = iUserEntityRespostity.findByUsername(userEntity.getUsername());

        if(checkUserEntity != null) {

            return JSONResponse.fail("Username is already in use", HttpStatus.CONFLICT);
        }

        checkUserEntity = iUserEntityRespostity.findByEmail(userEntity.getEmail());

        if(checkUserEntity != null) {

            return JSONResponse.fail("Email is already in use", HttpStatus.CONFLICT);
        }

        userEntity.setShapassword(passwordEncoder.encode(userEntity.getShapassword()));

        iUserEntityRespostity.save(userEntity);

        return JSONResponse.ok(userEntity);
    }
}
