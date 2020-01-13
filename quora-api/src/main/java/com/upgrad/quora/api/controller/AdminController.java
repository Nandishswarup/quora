package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.UserDeleteResponse;
import com.upgrad.quora.service.business.AdminControllerService;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.Users;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.SignUpRestrictedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class AdminController {
    @Autowired
    private AdminControllerService adminControllerService;
    @Autowired
    UserDao userDao;

    @RequestMapping(method = RequestMethod.DELETE, path = "/admin/user/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDeleteResponse> deleteUser(@PathVariable("userId") Integer userId, @RequestParam String accesstoken) throws SignUpRestrictedException, AuthorizationFailedException, UserNotFoundException {
        Users users = userDao.getUserByid(userId);

        if (users == null)
            throw new UserNotFoundException("USR-001", "User with entered uuid to be deleted does not exist");

        UserAuthTokenEntity userAuthTokenEntity = userDao.checkAuthToken(accesstoken);
        if (userAuthTokenEntity == null)
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");


        Users userOfAccessToken=userDao.getUserByid(userAuthTokenEntity.getId());
        if (!userOfAccessToken.getRole().contentEquals("admin"))
            throw new AuthorizationFailedException("ATHR-003", "Unauthorized Access, Entered user is not an admin");



        adminControllerService.deleteUser(userId);

        final UserDeleteResponse userDeleteResponse = new UserDeleteResponse();
        userDeleteResponse.setId(String.valueOf(userId));


        return new ResponseEntity<UserDeleteResponse>(userDeleteResponse, HttpStatus.OK);
    }

}
