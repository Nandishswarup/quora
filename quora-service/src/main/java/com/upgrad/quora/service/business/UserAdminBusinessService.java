package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.Users;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Service

public class UserAdminBusinessService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordCryptographyProvider cryptographyProvider;

    public Users signup(Users users) throws SignUpRestrictedException {
        String password = users.getPassword();
        if (password == null) {
            users.setPassword("quora@123");
        }
        String[] encryptedText = cryptographyProvider.encrypt(users.getPassword());
        users.setSalt(encryptedText[0]);
        users.setPassword(encryptedText[1]);
        if (userDao.getUserByUserName(users.getUsername()) != null) {
            throw new SignUpRestrictedException("SGR-001", "Try any other Username, this Username has already been taken");
        }
        if (userDao.getUserByEmail(users.getEmail()) != null) {
            throw new SignUpRestrictedException("SGR-002", "This user has already been registered, try with any other emailId");
        }
        return userDao.createUser(users);
    }

    @Transactional(noRollbackFor = {TransactionException.class})
    public UserAuthTokenEntity signoutUser(String accessToken) {

        UserAuthTokenEntity userAuthTokenEntity = userDao.checkAuthToken(accessToken);
        if (userAuthTokenEntity == null) {
            return null;
        }
        return userDao.signoutUser(userAuthTokenEntity);

    }


    @Transactional(noRollbackFor = {TransactionException.class})
    public Users getUserDetail(Integer id, String accesstoken) throws AuthorizationFailedException {

        UserAuthTokenEntity userAuthTokenEntity = userDao.checkAuthToken(accesstoken);
        if (userAuthTokenEntity == null) {
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }

        //Checking if user has logged out with respect to current date and time
        ZonedDateTime getLogoutAt = userAuthTokenEntity.getLogout_at();
        ZonedDateTime dateCurrent = ZonedDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
        if (getLogoutAt != null)
            if (getLogoutAt.isBefore(dateCurrent))
                throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to get user details");

        return userDao.getUserDetail(id);

    }
}
