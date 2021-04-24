package org.jewel.service;

import org.jewel.repos.UserRepository;
import org.jewel.model.User;
import org.jewel.model.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserRoleService implements UserDetailsService {
    @Autowired
    private UserRepository users;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = users.findUserByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user " + username + " found.");
        }

        System.out.println("Found user: " + user.getLogin());
        /*ArrayList<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (username.equals("admin")) {
            roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }*/

        user.setLastLogin(new Date());
        user.setStatus(UserStatus.ACTIVE);
        users.save(user);

        UserDetails userD =
                org.springframework.security.core.userdetails.User.builder()
                        .username(user.getLogin())
                        .password(user.getEncodedPassword())
                        .roles(user.getUserRole())//user.getUserRole().getRoleName())
                        .build();

        return userD; /*new org.springframework.security.core.userdetails.User(
                username, user.getEncodedPassword(), roles
        );*/
    }
}
