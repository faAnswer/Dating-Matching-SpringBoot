package org.tecky.nohorny.services.intf;

import org.springframework.http.ResponseEntity;
import org.tecky.nohorny.dto.JSONResponse;
import org.tecky.nohorny.entities.UserEntity;

public interface IRegService {

    public ResponseEntity<JSONResponse<Object>> regNewUser(UserEntity userEntity);


}
