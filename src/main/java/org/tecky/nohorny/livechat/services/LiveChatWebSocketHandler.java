package org.tecky.nohorny.livechat.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class LiveChatWebSocketHandler implements WebSocketHandler {

    private Map<String, WebSocketSession> liveChatMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        log.info("Connect Sucessfully: " + session.getId());

        this.liveChatMap.put(session.getId(), session);

        log.info("Current Users："+ liveChatMap.size());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        log.info("Get Message: " + session.getId());

        String msg = message.getPayload().toString();

        log.info("Message : " + msg);

//        session.sendMessage(message);

        sendMessageToAllUsers(session.getId() + ":" + msg);


    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

        log.info("Connect Error : " + session.getId());

        if (!session.isOpen()) {

            liveChatMap.remove(session.getId());

            session.close();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

        log.info("Connect Closed : " + session.getId());

        if(!session.isOpen()){

            liveChatMap.remove(session.getId());
            log.info("Current Users："+ liveChatMap.size());
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public void sendMessageToUser(String userId, String contents) {

        WebSocketSession session = liveChatMap.get(userId);

        if (session != null && session.isOpen()) {
            try {
                TextMessage message = new TextMessage(contents);
                session.sendMessage(message);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageToAllUsers(String contents) {

        Set<String> userIds = liveChatMap.keySet();

        for (String userId : userIds) {

            this.sendMessageToUser(userId, contents);
        }
    }
}
