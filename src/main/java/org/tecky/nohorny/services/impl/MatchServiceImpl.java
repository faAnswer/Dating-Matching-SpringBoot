package org.tecky.nohorny.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.tecky.nohorny.dto.CurrentUserDTO;
import org.tecky.nohorny.dto.UserProfileDTO;
import org.tecky.nohorny.entities.UserDailyMatchEntity;
import org.tecky.nohorny.entities.UserInfoEntity;
import org.tecky.nohorny.entities.UserMatchEntity;
import org.tecky.nohorny.mapper.UserDailyMatchEntityRepository;
import org.tecky.nohorny.mapper.UserEntityRespostity;
import org.tecky.nohorny.mapper.UserInfoEntityRepository;
import org.tecky.nohorny.mapper.UserMatchEntityRepository;
import org.tecky.nohorny.services.intf.IMatchService;

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

        return userDailyMatchEntity.getIsMatch() == 0;
    }

    @Override
    public List<UserProfileDTO> getMatchUser(Authentication authentication) {

        List<UserProfileDTO> currentUserDTOList = new ArrayList<>();


        Integer selfUid = userEntityRespostity.findByUsername(authentication.getName()).getUid();


        List<UserMatchEntity> userMatchEntityList = userMatchEntityRepository.findByUid(selfUid);


        for(UserMatchEntity userMatchEntity: userMatchEntityList){

            UserInfoEntity userInfoEntity = userInfoEntityRepository.findByUid(userMatchEntity.getMatchuid());


            UserProfileDTO userProfileDTO = ConversionUtil.convertS2S(UserProfileDTO.class, userInfoEntity);

            userProfileDTO.setAvatarUrl(minioEndpoint + userInfoEntity.getPicId());

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
        userDailyMatchEntity.setIsMatch(1);

        userDailyMatchEntityRepository.saveAndFlush(userDailyMatchEntity);
    }
}
