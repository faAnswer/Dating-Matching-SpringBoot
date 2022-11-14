package org.tecky.nohorny.livechat.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.tecky.nohorny.entities.PmContentEntity;
import org.tecky.nohorny.entities.PmEntity;
import org.tecky.nohorny.entities.PmStatusEntity;
import org.tecky.nohorny.entities.UserEntity;
import org.tecky.nohorny.livechat.services.intf.ILiveChatService;
import org.tecky.nohorny.mapper.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LiveChatServiceImpl implements ILiveChatService {

    @Autowired
    PmContentEntityRepository pmContentEntityRepository;

    @Autowired
    PmEntityRepository pmEntityRepository;

    @Autowired
    PmStatusEntityRepository pmStatusEntityRepository;

    @Autowired
    UserEntityRespostity userEntityRespostity;

    private int messageType;

    private void sendMessage(String fromUser, String toUser, String message) {

        // Shall put to Interceptor?
        PmEntity pmEntity = new PmEntity();
        String uuid = UUID.randomUUID().toString();

        int fromUid = userEntityRespostity.findByUsername(fromUser).getUid();
        int toUid = userEntityRespostity.findByUsername(toUser).getUid();

        pmEntity.setFromuid(fromUid);
        pmEntity.setTouid(toUid);
        pmEntity.setUuid(uuid);

        pmEntityRepository.saveAndFlush(pmEntity);

        // get back pmid
        // but seen to be little stupid
        pmEntity = pmEntityRepository.findByUuid(uuid);

        Integer pmId = pmEntity.getPmid();

        PmContentEntity pmContentEntity = new PmContentEntity();
        pmContentEntity.setPmid(pmId);
        pmContentEntity.setContent(message);
        pmContentEntityRepository.saveAndFlush(pmContentEntity);

        long millis = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(new Date(millis));

        PmStatusEntity pmStatusEntity = new PmStatusEntity();

        pmStatusEntity.setPmid(pmId);
        pmStatusEntity.setIsread(this.messageType);
        pmStatusEntity.setSendtime(sd);

        pmStatusEntityRepository.saveAndFlush(pmStatusEntity);

    }

    @Override
    public void sendOfflineMessage(String fromUser, String toUser, String message) {

        this.messageType = 0;
        sendMessage(fromUser, toUser, message);
    }

    @Override
    public void sendOnlineMessage(String fromUser, String toUser, String message) {

        this.messageType = 1;
        sendMessage(fromUser, toUser, message);
    }

    @Override
    public List<String> unReadMsgFrom(Authentication authentication) {

        UserEntity userEntity = userEntityRespostity.findByUsername(authentication.getName());

        int uid = userEntity.getUid();

        List<UserEntity> userEntityList = userEntityRespostity.findUnReadbyUid(uid);

        List<String> userList = new ArrayList<>();

        if(userEntityList.size() == 0){

            return userList;
        } else {

            for(UserEntity user : userEntityList){

                userList.add(user.getUsername());
            }

            return userList;
        }
    }
}
