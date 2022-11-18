package org.tecky.nohorny.services.intf;

import org.springframework.security.core.Authentication;
import org.tecky.nohorny.dto.CurrentUserDTO;

import java.lang.reflect.InvocationTargetException;

public interface IUserService {

    public CurrentUserDTO getCurrentUser(Authentication authentication) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    public CurrentUserDTO getCurrentUser();

}
