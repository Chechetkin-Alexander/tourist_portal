package org.portal.configure;

import org.portal.models.User;
import org.portal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.find(username);
        String role = user.getRole().getName();

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        if (role.equals("organizer")) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ORGANIZER"));
        } else if (role.equals("participant")) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_PARTICIPANT"));
        }

        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(),
                grantedAuthorities);
    }
}
