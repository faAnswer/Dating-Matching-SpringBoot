package org.tecky.nohorny.livechat.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LiveChatMsgDTO {

    private String fromUser;
    private String toUser;
    private String msg;
}
