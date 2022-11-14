package org.tecky.nohorny.services.intf;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface ICoreOauthService {

    public ResponseEntity<?> loginCoreUser(String username, String email) throws JsonProcessingException;
}

