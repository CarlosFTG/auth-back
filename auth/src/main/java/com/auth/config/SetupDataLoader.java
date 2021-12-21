package com.auth.config;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.auth.dto.SocialProvider;
import com.auth.entities.RoleEntity;
import com.auth.entities.UserEntity;
import com.auth.repositories.RoleRepository;
import com.auth.repositories.UserRepository;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent>{
	private boolean alreadySetup = false;
	 
    @Autowired
    private UserRepository userRepository;
 
    @Autowired
    private RoleRepository roleRepository;
 
    @Autowired
    private PasswordEncoder passwordEncoder;
 
    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        // Create initial roles
        RoleEntity userRole = createRoleIfNotFound(RoleEntity.ROLE_USER);
        RoleEntity adminRole = createRoleIfNotFound(RoleEntity.ROLE_ADMIN);
        RoleEntity modRole = createRoleIfNotFound(RoleEntity.ROLE_MODERATOR);
        createUserIfNotFound("admin@javachinna.com", new HashSet<RoleEntity>(Arrays.asList(userRole, adminRole, modRole)));
        alreadySetup = true;
    }
 
    @Transactional
    private final UserEntity createUserIfNotFound(final String email, Set<RoleEntity> roles) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            user = new UserEntity();
            user.setDisplayName("Admin");
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("admin@"));
            user.setRoles(roles);
            user.setProvider(SocialProvider.LOCAL.getProviderType());
            user.setEnabled(true);
            Date now = Calendar.getInstance().getTime();
            user.setCreatedDate(now);
            user.setModifiedDate(now);
            user = userRepository.save(user);
        }
        return user;
    }
 
    @Transactional
    private final RoleEntity createRoleIfNotFound(final String name) {
    	RoleEntity role = roleRepository.findByName(name);
        if (role == null) {
            role = roleRepository.save(new RoleEntity(name));
        }
        return role;
    }
}
