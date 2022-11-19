package org.tecky.nohorny.livechat.services.intf;

import org.springframework.security.core.Authentication;
import org.tecky.nohorny.livechat.dto.LiveChatContactDTO;
import org.tecky.nohorny.livechat.dto.LiveChatMsgDTO;

import java.util.List;

public interface ILiveChatService {

    public void sendOfflineMessage(String fromUser, String toUser, String message);
    public void sendOnlineMessage(String fromUser, String toUser, String message);
    public List<String> unReadMsgFrom(Authentication authentication);
    public List<LiveChatMsgDTO> getAllMsg(String contactUser, Authentication authentication);

    public List<LiveChatContactDTO> getAllContacts(Authentication authentication);

}
