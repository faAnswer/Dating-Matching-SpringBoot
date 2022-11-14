package org.tecky.nohorny.livechat.services.intf;

public interface ILiveChatService {

    public void sendMessage(Integer fromUid, Integer toUid, String message);

}
