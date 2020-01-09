package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.Users;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class AuthenticationService {


    @Autowired
    PasswordCryptographyProvider passwordCryptographyProvider;

    @Autowired
    UserDao userDao;
    public UserAuthTokenEntity authenticate(final String username, final String password) throws AuthenticationFailedException {
        Users userEntity = userDao.getUserByEmail(username);
        if(userEntity == null){
            throw new AuthenticationFailedException("ATH-001", "User with email not found");
        }
        final String encryptedPassword = passwordCryptographyProvider.encrypt(password, userEntity.getSalt());
        if(encryptedPassword.equals(userEntity.getPassword())){

            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);
            UserAuthTokenEntity userAuthToken = new UserAuthTokenEntity();
           // userAuthToken.setUser(userEntity);
            final ZonedDateTime now = ZonedDateTime.now();
            final ZonedDateTime expiresAt = now.plusHours(8);
            userAuthToken.setAccess_token(jwtTokenProvider.generateToken(userEntity.getUuid(), now, expiresAt));
            userAuthToken.setLogin_at(now);
            userAuthToken.setExpires_at(expiresAt);
            userDao.createAuthToken(userAuthToken);
            userDao.updateUser(userEntity);
            //userEntity.setLastLoginAt(now);

            return userAuthToken;
        }
        else{
            throw new AuthenticationFailedException("ATH-002", "Password Failed");
        }


    }

}
