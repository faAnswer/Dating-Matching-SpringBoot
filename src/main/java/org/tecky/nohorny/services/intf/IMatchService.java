package org.tecky.nohorny.services.intf;

import org.springframework.security.core.Authentication;
import org.tecky.nohorny.dto.UserProfileDTO;

import java.util.List;
import java.util.Map;

public interface IMatchService {

    public boolean dailyMatch(Authentication authentication);

    public List<UserProfileDTO> getMatchUser(Authentication authentication);

    public void matching(Map<String, String> map, Authentication authentication);


}
