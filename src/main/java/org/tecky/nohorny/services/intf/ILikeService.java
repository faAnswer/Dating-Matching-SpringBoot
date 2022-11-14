package org.tecky.nohorny.services.intf;


import org.springframework.security.core.Authentication;

public interface ILikeService {

    public boolean checkLike(Authentication authentication, String username);

    public boolean changeLikeStatus(Authentication authentication, String username);
}
