package org.tecky.nohorny.services.impl;

import org.faAnswer.web.util.dto.ConversionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.tecky.nohorny.dto.CurrentUserDTO;
import org.tecky.nohorny.entities.RoleEntity;
import org.tecky.nohorny.entities.UserEntity;
import org.tecky.nohorny.entities.UserRoleEntity;
import org.tecky.nohorny.mapper.RoleEntityRepository;
import org.tecky.nohorny.mapper.UserEntityRespostity;
import org.tecky.nohorny.mapper.UserRoleEntityRepository;
import org.tecky.nohorny.services.intf.IUserService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserEntityRespostity userEntityRespostity;

    @Autowired
    RoleEntityRepository roleEntityRepository;

    @Autowired
    UserRoleEntityRepository userRoleEntityRepository;

    @Override
    public CurrentUserDTO getCurrentUser(Authentication authentication) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        UserEntity userEntity = userEntityRespostity.findByUsername(authentication.getName());
        UserRoleEntity userRoleEntity = userRoleEntityRepository.findByUid(userEntity.getUid()).get(0);
        RoleEntity roleEntity = roleEntityRepository.findByRoleid(userRoleEntity.getRoleid());

        List<Object> userInfo = new ArrayList<>();

        userInfo.add(userEntity);
        userInfo.add(roleEntity);

        CurrentUserDTO currentUserDTO = ConversionUtil.convertM2S(CurrentUserDTO.class, userInfo);

        return currentUserDTO;
    }

    @Override
    public CurrentUserDTO getCurrentUser() {

        CurrentUserDTO currentUserDTO = new CurrentUserDTO();

        currentUserDTO.setUsername("Guest");
        currentUserDTO.setRolename("ROLE_GUEST");

        return currentUserDTO;
    }
}
