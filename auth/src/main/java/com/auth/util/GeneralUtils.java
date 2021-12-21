package com.auth.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.auth.dto.LocalUser;
import com.auth.dto.SocialProvider;
import com.auth.dto.UserInfo;
import com.auth.entities.RoleEntity;
import com.auth.entities.UserEntity;

public class GeneralUtils {
	public static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<RoleEntity> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (RoleEntity role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }
 
    public static SocialProvider toSocialProvider(String providerId) {
        for (SocialProvider socialProvider : SocialProvider.values()) {
            if (socialProvider.getProviderType().equals(providerId)) {
                return socialProvider;
            }
        }
        return SocialProvider.LOCAL;
    }
 
    public static UserInfo buildUserInfo(LocalUser localUser) {
        List<String> roles = localUser.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        UserEntity user = localUser.getUser();
        return new UserInfo(user.getId().toString(), user.getDisplayName(), user.getEmail(), roles);
    }
}
