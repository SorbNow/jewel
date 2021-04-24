/*
package org.jewel.web;


import org.jewel.db.UserRepository;
import org.jewel.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthencationProvider implements AuthenticationProvider {
    @Autowired
    private UserRepository dao;
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();
        //получаем пользователя
        User myUser = dao.findByLogin(userName);
        //смотрим, найден ли пользователь в базе
        if (myUser == null) {
            throw new BadCredentialsException("Unknown user "+userName);
        }
        if (!password.equals(myUser.getEncodedPassword())) {
            System.out.println("Bad password "+ myUser.getEncodedPassword() + "&" + password);
            throw new BadCredentialsException("Bad password");
        }
        UserDetails principal = org.springframework.security.core.userdetails.User.builder()
                .username(myUser.getLogin())
                .password(myUser.getEncodedPassword())
                .roles(myUser.getUserRole().getRoleName())
                .build();
        return new UsernamePasswordAuthenticationToken(
                principal, password, principal.getAuthorities());
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}*/
