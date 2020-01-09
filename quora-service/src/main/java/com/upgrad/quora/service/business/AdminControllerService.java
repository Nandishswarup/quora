package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminControllerService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public void deleteUser(Integer userId) {
        userDao. deleteUser(userId);

    }
}
