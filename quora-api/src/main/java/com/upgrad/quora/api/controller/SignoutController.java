package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.SignupUserResponse;
import com.upgrad.quora.service.business.SignoutBusinessService;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.exception.SignOutRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SignoutController {

    @Autowired
    private SignoutBusinessService signoutBusinessService;


    @RequestMapping(method = RequestMethod.POST, path = "/user/signout", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupUserResponse> signout(final String accessToken) throws SignOutRestrictedException {
        UserAuthTokenEntity userAuthTokenEntity= signoutBusinessService.signout(accessToken);
        if(userAuthTokenEntity==null)
            throw new SignOutRestrictedException("SGR-001","User is not Signed in");
        SignupUserResponse userResponse = new SignupUserResponse().id(userAuthTokenEntity.getUuid()).status("SIGNED OUT SUCCESSFULLY");
        return new ResponseEntity<SignupUserResponse>(userResponse, HttpStatus.OK);
    }

}
