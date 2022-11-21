package org.tecky.nohorny.security.services;

import java.io.Serializable;

public class JwtResponseImpl implements Serializable {

    private static final long serialVersionUID = -809187904046844L;

    //private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;

    public JwtResponseImpl(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }
}