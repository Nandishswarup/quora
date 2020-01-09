package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.Users;
import com.upgrad.quora.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignupBusinessService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserAdminBusinessService userAdminBusinessService;

    @Autowired
    PasswordCryptographyProvider passwordCryptographyProvider;

    @Autowired
    private PasswordCryptographyProvider cryptographyProvider;


    //    @Transactional(propagation = Propagation.REQUIRED)
    @Transactional
    public Users signup(Users users) throws SignUpRestrictedException {
        return userAdminBusinessService.signup(users);

    }
}
