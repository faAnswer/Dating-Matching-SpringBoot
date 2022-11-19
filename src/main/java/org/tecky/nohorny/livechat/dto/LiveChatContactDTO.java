package org.tecky.nohorny.livechat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LiveChatContactDTO {

    private String username;

    private Integer unReadMsgNum;

    private String avatarUrl;

    private String recentMsg;
}
