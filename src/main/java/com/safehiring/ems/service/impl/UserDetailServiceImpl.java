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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        return User.withUsername(user.getEmail()).password(user.getPassword()).disabled(!user.isAccountVerified()).authorities(getAuthorities(user)).build();
    }

    private Collection<GrantedAuthority> getAuthorities(UserEntity user) {
        Set<Group> groups = user.getUserGroups();
        Collection<GrantedAuthority> authorities = new ArrayList<>(groups.size());
        groups.forEach(group -> authorities.add(new SimpleGrantedAuthority(group.getCode().toUpperCase())));
        return authorities;
    }
}
