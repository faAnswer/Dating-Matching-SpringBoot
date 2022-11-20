package org.tecky.nohorny.services.impl;

import org.faAnswer.web.util.dto.ConversionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.tecky.nohorny.dto.CurrentUserDTO;
import org.tecky.nohorny.dto.UserProfileDTO;
import org.tecky.nohorny.entities.UserDailyMatchEntity;
import org.tecky.nohorny.entities.UserEntity;
import org.tecky.nohorny.entities.UserInfoEntity;
import org.tecky.nohorny.entities.UserMatchEntity;
import org.tecky.nohorny.mapper.UserDailyMatchEntityRepository;
import org.tecky.nohorny.mapper.UserEntityRespostity;
import org.tecky.nohorny.mapper.UserInfoEntityRepository;
import org.tecky.nohorny.mapper.UserMatchEntityRepository;
import org.tecky.nohorny.services.intf.IMatchService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MatchServiceImpl implements IMatchService {

    @Autowired
    UserDailyMatchEntityRepository userDailyMatchEntityRepository;

    @Autowired
    UserInfoEntityRepository userInfoEntityRepository;

    @Autowired
    UserEntityRespostity userEntityRespostity;

    @Autowired
    UserMatchEntityRepository userMatchEntityRepository;

    @Value("${minio.endpoint}")
    String minioEndpoint;

    @Override
    public boolean dailyMatch(Authentication authentication) {

        Integer uid = userEntityRespostity.findByUsername(authentication.getName()).getUid();

        UserDailyMatchEntity userDailyMatchEntity = userDailyMatchEntityRepository.findByUid(uid);

        if(userDailyMatchEntity == null){

            userDailyMatchEntity = new UserDailyMatchEntity();

            userDailyMatchEntity.setUid(uid);
            userDailyMatchEntity.setIsmatch(0);

            userDailyMatchEntityRepository.saveAndFlush(userDailyMatchEntity);

        }

        return userDailyMatchEntity.getIsmatch() == 0;
    }


    @Override
    public List<UserProfileDTO> getMatchUser(Authentication authentication) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        List<UserProfileDTO> currentUserDTOList = new ArrayList<>();

        Integer selfUid = userEntityRespostity.findByUsername(authentication.getName()).getUid();

        List<UserMatchEntity> userMatchEntityList = userMatchEntityRepository.findByUid(selfUid);

        for(UserMatchEntity userMatchEntity: userMatchEntityList){

            UserInfoEntity userInfoEntity = userInfoEntityRepository.findByUid(userMatchEntity.getMatchuid());
            UserEntity userEntity = userEntityRespostity.findByUid(userMatchEntity.getMatchuid());

            List<Object> entitiesList = new ArrayList<>();

            entitiesList.add(userInfoEntity);
            entitiesList.add(userEntity);

            UserProfileDTO userProfileDTO = ConversionUtil.convertM2S(UserProfileDTO.class, entitiesList);

            if(userInfoEntity.getPicId() == null) {

                userProfileDTO.setAvatarUrl(null);

            } else {

                userProfileDTO.setAvatarUrl(minioEndpoint + userInfoEntity.getPicId());
            }

            currentUserDTOList.add(userProfileDTO);

        }

        return currentUserDTOList;
    }

    @Override
    public void matching(Map<String, String> map, Authentication authentication) {

        Integer selfUid = userEntityRespostity.findByUsername(authentication.getName()).getUid();

        String gender = map.get("gender");
        String sexual = map.get("sexual");
        Integer agebottom = Integer.valueOf(map.get("agebottom"));
        Integer agetop = Integer.valueOf(map.get("agetop"));
        Integer districtid = Integer.valueOf(map.get("districtid"));

        List<UserInfoEntity> userInfoEntityList = userInfoEntityRepository.matchingUsers(gender, sexual, agebottom, agetop, districtid, selfUid);

        for(UserInfoEntity userInfoEntity : userInfoEntityList){

            UserMatchEntity userMatchEntity = new UserMatchEntity();

            userMatchEntity.setUid(selfUid);
            userMatchEntity.setMatchuid(userInfoEntity.getUid());

            userMatchEntityRepository.saveAndFlush(userMatchEntity);
        }

        UserDailyMatchEntity userDailyMatchEntity = new UserDailyMatchEntity();

        userDailyMatchEntity.setUid(selfUid);
        userDailyMatchEntity.setIsmatch(1);

        userDailyMatchEntityRepository.saveAndFlush(userDailyMatchEntity);
    }

    @Override
    public List<UserProfileDTO> getMockUser(int[] uidArr, Authentication authentication) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        List<UserProfileDTO> currentUserDTOList = new ArrayList<>();

        Integer selfUid = userEntityRespostity.findByUsername(authentication.getName()).getUid();

        List<UserMatchEntity> userMatchEntityList = userMatchEntityRepository.findByUid(selfUid);

        for(int matchUid: uidArr){

            if(matchUid == selfUid){

                continue;
            }

            UserInfoEntity userInfoEntity = userInfoEntityRepository.findByUid(matchUid);
            UserEntity userEntity = userEntityRespostity.findByUid(matchUid);

            List<Object> entitiesList = new ArrayList<>();

            entitiesList.add(userInfoEntity);
            entitiesList.add(userEntity);

            UserProfileDTO userProfileDTO = ConversionUtil.convertM2S(UserProfileDTO.class, entitiesList);

            if(userInfoEntity.getPicId() == null) {

                userProfileDTO.setAvatarUrl(null);

            } else {

                userProfileDTO.setAvatarUrl(minioEndpoint + userInfoEntity.getPicId());
            }


            currentUserDTOList.add(userProfileDTO);
        }

        return currentUserDTOList;
    }
}
