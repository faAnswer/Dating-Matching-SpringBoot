package org.tecky.nohorny.services.intf;

import org.springframework.security.core.Authentication;
import org.tecky.nohorny.dto.CurrentUserDTO;
import org.tecky.nohorny.dto.UserProfileDTO;
import org.tecky.nohorny.entities.UserInfoEntity;

import java.lang.reflect.InvocationTargetException;

public interface IUserService {

    public CurrentUserDTO getCurrentUser(Authentication authentication) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    public CurrentUserDTO getCurrentUser();
    public boolean upDateProfile(UserInfoEntity userInfoEntity, Authentication authentication);

    public UserProfileDTO getProfile(Authentication authentication) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    public UserProfileDTO getProfile(String username) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;


}
