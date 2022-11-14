package org.tecky.nohorny.livechat.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tecky.nohorny.entities.PmContentEntity;
import org.tecky.nohorny.entities.PmEntity;
import org.tecky.nohorny.entities.PmStatusEntity;
import org.tecky.nohorny.livechat.services.intf.ILiveChatService;
import org.tecky.nohorny.mapper.*;

import java.sql.Date;
import java.sql.Timestamp;
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


    @Override
    public void sendMessage(String fromUser, String toUser, String message) {

        // Shall put to Interceptor?
        PmEntity pmEntity = new PmEntity();
        String uuid = UUID.randomUUID().toString();

        int fromUid = userEntityRespostity.findByUsername(fromUser).getUid();
        int toUid = userEntityRespostity.findByUsername(toUser).getUid();

        pmEntity.setFromuid(fromUid);
        pmEntity.setTouid(toUid);
        pmEntity.setUuid(uuid);

        pmEntityRepository.save(pmEntity);

        // get back pmid
        // but seen to be little stupid
        pmEntity = pmEntityRepository.findByUuid(uuid);

        Integer pmId = pmEntity.getPmid();

        PmContentEntity pmContentEntity = new PmContentEntity();
        pmContentEntity.setPmid(pmId);
        pmContentEntity.setContent(message);
        pmContentEntityRepository.save(pmContentEntity);

        long millis = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(millis);

        PmStatusEntity pmStatusEntity = new PmStatusEntity();

        pmStatusEntity.setPmid(pmId);
        pmStatusEntity.setRead(0);
        pmStatusEntity.setSendtime(timestamp);
        pmStatusEntityRepository.save(pmStatusEntity);

    }
}
