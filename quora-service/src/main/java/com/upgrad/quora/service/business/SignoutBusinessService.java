package com.upgrad.quora.service.business;

import com.upgrad.quora.service.entity.Users;
import com.upgrad.quora.service.exception.SignOutRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignoutBusinessService {

    @Autowired
    private UserAdminBusinessService userAdminBusinessService;

    @Transactional(noRollbackFor={TransactionException.class})
    public Users signout(String  accessToken) throws SignOutRestrictedException {
        return userAdminBusinessService.signoutUser(accessToken);
    }
}
