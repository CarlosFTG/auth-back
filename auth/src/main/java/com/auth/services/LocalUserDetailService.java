package com.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth.dto.LocalUser;
import com.auth.entities.UserEntity;
import com.auth.exceptions.ResourceNotFoundException;
import com.auth.util.GeneralUtils;

@Service("localUserDetailService")
public class LocalUserDetailService implements UserDetailsService{
	@Autowired
    private UserService userService;
 
    @Override
    @Transactional
    public LocalUser loadUserByUsername(final String email) throws UsernameNotFoundException {
        UserEntity user = userService.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }
        return createLocalUser(user);
    }
 
    @Transactional
    public LocalUser loadUserById(Long id) {
        UserEntity user = userService.findUserById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return createLocalUser(user);
    }
 
    /**
     * @param user
     * @return
     */
    private LocalUser createLocalUser(UserEntity user) {
        return new LocalUser(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, GeneralUtils.buildSimpleGrantedAuthorities(user.getRoles()), user);
    }
}
