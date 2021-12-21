package com.auth.services;

import java.util.Map;
import java.util.Optional;
 
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import com.auth.dto.LocalUser;
import com.auth.dto.SignUpRequest;
import com.auth.entities.UserEntity;
 
//import com.javachinna.dto.LocalUser;
//import com.javachinna.dto.SignUpRequest;
//import com.javachinna.exception.UserAlreadyExistAuthenticationException;

public interface UserService {
	public UserEntity registerNewUser(SignUpRequest signUpRequest);
			//throws UserAlreadyExistAuthenticationException;
	 
	UserEntity findUserByEmail(String email);
 
    Optional<UserEntity> findUserById(Long id);
 
    LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);
}
