package org.tecky.nohorny.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.tecky.nohorny.entities.UserContactEntity;
import org.tecky.nohorny.entities.UserLikeEntity;
import org.tecky.nohorny.livechat.services.LiveChatWebSocketHandler;
import org.tecky.nohorny.livechat.services.intf.ILiveChatService;
import org.tecky.nohorny.mapper.UserContactEntityRepository;
import org.tecky.nohorny.mapper.UserEntityRespostity;
import org.tecky.nohorny.mapper.UserLikeEntityRepository;
import org.tecky.nohorny.services.intf.ILikeService;

import java.util.*;

@Service
public class LikeServiceImpl implements ILikeService {

    @Autowired
    ILiveChatService iLiveChatService;

    @Autowired
    UserLikeEntityRepository userLikeEntityRepository;

    @Autowired
    UserEntityRespostity userEntityRespostity;

    @Autowired
    LiveChatWebSocketHandler liveChatWebSocketHandler;

    @Autowired
    UserContactEntityRepository userContactEntityRepository;

    @Override
    public boolean checkLike(Authentication authentication, String username) {

        int fromUid = getUid(authentication.getName());
        int toUid = getUid(username);

        UserLikeEntity userLikeEntity = userLikeEntityRepository.findByFromuidAndTouid(fromUid, toUid);

        if(userLikeEntity == null){

            return false;
        } else {

            return true;
        }
    }

    @Override
    public boolean changeLikeStatus(Authentication authentication, String username) {

        int fromUid = getUid(authentication.getName());
        int toUid = getUid(username);

        UserLikeEntity userLikeEntity = userLikeEntityRepository.findByFromuidAndTouid(fromUid, toUid);

        if(userLikeEntity == null){

            UserLikeEntity newUserLikeEntity = new UserLikeEntity();

            newUserLikeEntity.setFromuid(fromUid);
            newUserLikeEntity.setTouid(toUid);
            userLikeEntityRepository.save(newUserLikeEntity);

            UserLikeEntity checkLiked = userLikeEntityRepository.findByFromuidAndTouid(toUid, fromUid);

            if(checkLiked != null){

                liveChatInvite(username, authentication.getName());
            }

            return true;
        } else {

            userLikeEntityRepository.delete(userLikeEntity);
            return false;
        }
    }

    private int getUid(String username){

        return userEntityRespostity.findByUsername(username).getUid();
    }

    private void liveChatInvite(String userName, String contactUserName){

        Map<String, String> pairUser = new HashMap<>();

        pairUser.put(userName, contactUserName);
        pairUser.put(contactUserName, userName);
        Set<String> keySet = pairUser.keySet();

        for(String key: keySet){

            String msg = "You pressed the Like button of " + pairUser.get(key) + " also to you pressed the receive button, ";
            msg = msg + "let invite the other to have a live chat !";

            liveChatWebSocketHandler.sendMessageToUser("System", key, msg);

            UserContactEntity userContact = new UserContactEntity();

            int selfUid = userEntityRespostity.findByUsername(key).getUid();
            int contactUid = userEntityRespostity.findByUsername(pairUser.get(key)).getUid();

            userContact.setUid(selfUid);
            userContact.setContactuid(contactUid);
            userContact.setStatuscode(1);
            userContactEntityRepository.saveAndFlush(userContact);

            String msgForPair = "Hello !";

            liveChatWebSocketHandler.sendMessageToUser(key, pairUser.get(key), msgForPair);

        }
    }
}
