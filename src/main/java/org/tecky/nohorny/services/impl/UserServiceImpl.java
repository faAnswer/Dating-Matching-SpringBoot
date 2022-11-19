package org.tecky.nohorny.services.impl;

import org.faAnswer.web.util.dto.ConversionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.tecky.nohorny.dto.CurrentUserDTO;
import org.tecky.nohorny.dto.UserProfileDTO;
import org.tecky.nohorny.entities.RoleEntity;
import org.tecky.nohorny.entities.UserEntity;
import org.tecky.nohorny.entities.UserInfoEntity;
import org.tecky.nohorny.entities.UserRoleEntity;
import org.tecky.nohorny.mapper.RoleEntityRepository;
import org.tecky.nohorny.mapper.UserEntityRespostity;
import org.tecky.nohorny.mapper.UserInfoEntityRepository;
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

    @Autowired
    UserInfoEntityRepository userInfoEntityRepository;

    @Value("${minio.endpoint}")
    String minioEndpoint;

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

    @Override
    public boolean upDateProfile(UserInfoEntity userInfoEntity, Authentication authentication) {

        boolean res = false;

        UserEntity userEntity = userEntityRespostity.findByUsername(authentication.getName());
        int uid = userEntity.getUid();

        userInfoEntity.setUid(uid);
        userInfoEntityRepository.saveAndFlush(userInfoEntity);

        res = true;

        return res;
    }

    @Override
    public UserProfileDTO getProfile(Authentication authentication) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        UserEntity userEntity = userEntityRespostity.findByUsername(authentication.getName());

        UserInfoEntity userInfoEntity = userInfoEntityRepository.findByUid(userEntity.getUid());

        UserProfileDTO userProfileDTO = ConversionUtil.convertS2S(UserProfileDTO.class, userInfoEntity);

        if(userInfoEntity.getPicId() == null) {

            userProfileDTO.setAvatarUrl(null);

        } else {

            userProfileDTO.setAvatarUrl(minioEndpoint + userInfoEntity.getPicId());
        }

        userProfileDTO.setUsername(authentication.getName());

        return userProfileDTO;
    }

    @Override
    public UserProfileDTO getProfile(String username) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {


        return findProfile(username);
    }

    private UserProfileDTO findProfile(String username) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        UserEntity userEntity = userEntityRespostity.findByUsername(username);

        UserInfoEntity userInfoEntity = userInfoEntityRepository.findByUid(userEntity.getUid());

        UserProfileDTO userProfileDTO = ConversionUtil.convertS2S(UserProfileDTO.class, userInfoEntity);

        if(userInfoEntity.getPicId() == null) {

            userProfileDTO.setAvatarUrl(null);

        } else {

            userProfileDTO.setAvatarUrl(minioEndpoint + userInfoEntity.getPicId());
        }
        userProfileDTO.setUsername(username);

        return userProfileDTO;
    }

}
