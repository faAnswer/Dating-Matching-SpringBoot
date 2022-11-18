package org.tecky.nohorny.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.tecky.nohorny.entities.UserEntity;
import org.tecky.nohorny.entities.UserRoleEntity;
import org.tecky.nohorny.mapper.RoleEntityRepository;
import org.tecky.nohorny.mapper.UserEntityRespostity;
import org.tecky.nohorny.mapper.UserRoleEntityRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoHornyUserDetailService implements UserDetailsService {

    @Autowired
    UserEntityRespostity userEntityRespostity;

    @Autowired
    RoleEntityRepository roleEntityRepository;

    @Autowired
    UserRoleEntityRepository userRoleEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userEntityRespostity.findByUsername(username);

        List<UserRoleEntity> userRoleEntityList = userRoleEntityRepository.findByUid(userEntity.getUid());

        List<GrantedAuthority> authorities = new ArrayList<>();

        for(UserRoleEntity roleEntity : userRoleEntityList){

            authorities.add(new SimpleGrantedAuthority(roleEntityRepository.findByRoleid(roleEntity.getRoleid()).getRolename()));
        }

        UserDetails userDetails = new User(userEntity.getUsername(), userEntity.getShapassword(), authorities);

        return userDetails;
    }
}
