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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private final Pattern pattern = Pattern.compile("(?<toUser>.*):(?<msgContent>.*)");
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        String fromUser = session.getPrincipal().getName();

        String msg = message.getPayload().toString();

        Matcher matcher = this.pattern.matcher(msg);

        String toUser = "";
        String msgContent = "";

        if(matcher.matches()){

            toUser = matcher.group("toUser");
            msgContent = matcher.group("msgContent");
        }

        log.info("Message from : " + fromUser);
        log.info("Message to : " + toUser);
        log.info("Message : " + msgContent);

        sendMessageToUser(fromUser, toUser, msgContent);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

        log.info("Connect Error : " + session.getPrincipal().getName());

        if (!session.isOpen()) {

            liveChatMap.remove(session.getPrincipal().getName());

            session.close();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

        log.info("Connect Closed : " + session.getPrincipal().getName());

        if(!session.isOpen()){

            liveChatMap.remove(session.getPrincipal().getName());
            log.info("Current Users："+ liveChatMap.size());
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public void sendMessageToUser(String fromUser, String toUser, String contents) {

        WebSocketSession session = liveChatMap.get(toUser);

        String msg = fromUser + ":" + contents;

        if (session != null && session.isOpen()) {
            try {
                TextMessage message = new TextMessage(msg);
                session.sendMessage(message);
                iLiveChatService.sendOnlineMessage(fromUser, toUser, contents);

            } catch (IOException e) {

                e.printStackTrace();
            }
        } else {

            iLiveChatService.sendOfflineMessage(fromUser, toUser, contents);
        }
    }
}
