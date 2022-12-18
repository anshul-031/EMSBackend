package com.safehiring.ems.service.impl;

import com.safehiring.ems.jpa.data.Group;
import com.safehiring.ems.jpa.data.UserEntity;
import com.safehiring.ems.jpa.repository.UserRepository;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Service
@Data
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username).orElseThrow(()->  new UsernameNotFoundException("User '" + username + "' not found."));
        if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                user.isAccountVerified(), true, true, true, getAuthorities(user));

    }

    private Collection<GrantedAuthority> getAuthorities(UserEntity user) {
        Set<Group> groups = user.getUserGroups();
        Collection<GrantedAuthority> authorities = new ArrayList<>(groups.size());
        groups.forEach(group -> authorities.add(new SimpleGrantedAuthority(group.getCode().toUpperCase())));
        return authorities;
    }
}
