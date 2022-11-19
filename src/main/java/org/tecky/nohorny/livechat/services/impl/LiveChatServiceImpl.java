package org.tecky.nohorny.livechat.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.tecky.nohorny.entities.*;
import org.tecky.nohorny.livechat.dto.LiveChatContactDTO;
import org.tecky.nohorny.livechat.dto.LiveChatMsgDTO;
import org.tecky.nohorny.livechat.services.intf.ILiveChatService;
import org.tecky.nohorny.mapper.*;

import java.sql.Date;
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

    @Autowired
    UserContactEntityRepository userContactEntityRepository;

    @Autowired
    UserInfoEntityRepository userInfoEntityRepository;

    @Value("${minio.endpoint}")
    String minioEndpoint;

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

    @Override
    public List<LiveChatMsgDTO> getAllMsg(String contactUser, Authentication authentication) {

        List<LiveChatMsgDTO> liveChatMsgDTOList = new ArrayList<>();

        String selfUserName = authentication.getName();

        UserEntity self = userEntityRespostity.findByUsername(selfUserName);
        UserEntity sender = userEntityRespostity.findByUsername(contactUser);

        List<Integer> uidList = new ArrayList<>();

        uidList.add(sender.getUid());
        uidList.add(self.getUid());

        List<PmEntity> pmEntityList = pmEntityRepository.findByFromuidInAndTouidInOrderByPmidAsc(uidList, uidList);

        for(PmEntity pm : pmEntityList){

            Integer pmId = pm.getPmid();
            LiveChatMsgDTO liveChatMsgDTO = new LiveChatMsgDTO();

            if(pm.getFromuid() == self.getUid()){


                liveChatMsgDTO.setFromUser(selfUserName);
                liveChatMsgDTO.setToUser(contactUser);
                liveChatMsgDTO.setMsg(pmContentEntityRepository.findByPmid(pmId).getContent());

            } else {

                liveChatMsgDTO.setFromUser(contactUser);
                liveChatMsgDTO.setToUser(selfUserName);
                liveChatMsgDTO.setMsg(pmContentEntityRepository.findByPmid(pmId).getContent());
            }

            liveChatMsgDTOList.add(liveChatMsgDTO);

            PmStatusEntity pmStatusEntity = pmStatusEntityRepository.findByPmidAndIsreadIs(pmId, 0);

            if(pmStatusEntity != null){

                pmStatusEntity.setIsread(1);
                pmStatusEntityRepository.saveAndFlush(pmStatusEntity);

            }
        }
        return liveChatMsgDTOList;
    }

    @Override
    public List<LiveChatContactDTO> getAllContacts(Authentication authentication) {

        List<LiveChatContactDTO> liveChatContactDTOList = new ArrayList<>();

        UserEntity self = userEntityRespostity.findByUsername(authentication.getName());
        Integer selfUid = self.getUid();

        List<UserContactEntity> userContactEntityList = userContactEntityRepository.findByUid(selfUid);

        Integer contactUserUid = null;
        UserEntity contactUserEntity = null;

        for(UserContactEntity userContactEntity: userContactEntityList){

            LiveChatContactDTO liveChatContactDTO = new LiveChatContactDTO();

            contactUserUid = userContactEntity.getContactuid();
            contactUserEntity = userEntityRespostity.findByUid(contactUserUid);

            liveChatContactDTO.setUsername(contactUserEntity.getUsername());


            if(userInfoEntityRepository.findByUid(contactUserUid).getPicId() == null) {

                liveChatContactDTO.setAvatarUrl(null);

            } else {

                liveChatContactDTO.setAvatarUrl(this.minioEndpoint + userInfoEntityRepository.findByUid(contactUserUid).getPicId());
            }

            List<Integer> uidList = new ArrayList<>();
            uidList.add(selfUid);
            uidList.add(contactUserUid);

            List<PmEntity> pmEntityList = pmEntityRepository.findByFromuidInAndTouidInOrderByPmidDesc(uidList, uidList);

            if(pmEntityList.size() == 0){

                liveChatContactDTO.setUnReadMsgNum(0);
                liveChatContactDTO.setRecentMsg(null);

            } else {

                Integer recentPmid = pmEntityList.get(0).getPmid();
                liveChatContactDTO.setRecentMsg(pmContentEntityRepository.findByPmid(recentPmid).getContent());

                List<PmEntity> pmTest = pmEntityRepository.findAllUnReadbyUid(selfUid, contactUserUid);
                liveChatContactDTO.setUnReadMsgNum(pmTest.size());


            }

            liveChatContactDTOList.add(liveChatContactDTO);
        }

        return liveChatContactDTOList;
    }
}
