package org.tecky.nohorny.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.tecky.nohorny.entities.UserLikeEntity;
import org.tecky.nohorny.livechat.services.intf.ILiveChatService;
import org.tecky.nohorny.mapper.UserEntityRespostity;
import org.tecky.nohorny.mapper.UserLikeEntityRepository;
import org.tecky.nohorny.services.intf.ILikeService;

@Service
public class LikeServiceImpl implements ILikeService {

    @Autowired
    ILiveChatService iLiveChatService;

    @Autowired
    UserLikeEntityRepository userLikeEntityRepository;

    @Autowired
    UserEntityRespostity userEntityRespostity;

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

                liveChatInvite(authentication.getName(), username);
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

    private void liveChatInvite(String userName, String msgReceiverName){

        String msg = "You pressed the Like button of " + userName + " also to you pressed the receive button, ";

        msg = msg + "let invite the other to have a live chat !";

        iLiveChatService.sendOfflineMessage("System", msgReceiverName, msg);
    }

}
