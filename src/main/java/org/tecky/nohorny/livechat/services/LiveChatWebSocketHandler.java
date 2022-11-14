package org.tecky.nohorny.livechat.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;
import org.tecky.nohorny.livechat.services.intf.ILiveChatService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class LiveChatWebSocketHandler implements WebSocketHandler {

    private Map<String, WebSocketSession> liveChatMap = new HashMap<>();

    @Autowired
    ILiveChatService iLiveChatService;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        log.info("Connect Sucessfully: " + session.getPrincipal().getName());

        this.liveChatMap.put(session.getPrincipal().getName(), session);

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        String fromName = session.getPrincipal().getName();

        String msg = message.getPayload().toString();

        Pattern pattern = Pattern.compile(/(.*):(.*)/g);
        Matcher matcher = pattern.matcher(msg);

        String[] msgs = msg.split(":");




        log.info("Get Message from " + fromName);



        log.info("Message : " + msg);

        sendMessageToAllUsers(fromName + ":" + msg);
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
