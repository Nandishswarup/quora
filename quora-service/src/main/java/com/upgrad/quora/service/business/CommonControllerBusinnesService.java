package com.upgrad.quora.service.business;

import com.upgrad.quora.service.entity.Users;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CommonControllerBusinnesService {

    @Autowired
    private UserAdminBusinessService userAdminBusinessService;

    @Transactional
    public Users getUserDetail(Integer id, String accesstoken) throws AuthorizationFailedException {
        return userAdminBusinessService.getUserDetail(id,accesstoken);

    }

}
