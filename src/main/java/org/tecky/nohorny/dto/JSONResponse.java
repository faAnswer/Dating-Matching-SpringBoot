package org.tecky.nohorny.dto;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.HashMap;


public class JSONResponse<T> extends HashMap<String,Object> implements Serializable {


    public static <T>  ResponseEntity<JSONResponse<T>> fail(String error, HttpStatus code){

        JSONResponse<T> response = new JSONResponse<>();

        response.put("error",error);
        return new ResponseEntity<>(response,code);
    }

    public static <T> ResponseEntity<JSONResponse<T>> ok(T data){

        JSONResponse<T> response = new JSONResponse<>();

        response.put("data",data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
