package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.SignupUserRequest;
import com.upgrad.quora.api.model.SignupUserResponse;
import com.upgrad.quora.service.business.SignupBusinessService;
import com.upgrad.quora.service.entity.Users;
import com.upgrad.quora.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/")
public class SignupController {
    @Autowired
    private SignupBusinessService signupBusinessService;

    @RequestMapping(method = RequestMethod.POST, path = "/signup", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupUserResponse> signup(final SignupUserRequest signupUserRequest) throws SignUpRestrictedException {
        final Users users = new Users();
        users.setUuid(UUID.randomUUID().toString());
        users.setFirstname(signupUserRequest.getFirstName());
        users.setLastname(signupUserRequest.getLastName());
        users.setEmail(signupUserRequest.getEmailAddress());
        users.setPassword(signupUserRequest.getPassword());
        users.setContactnumber(signupUserRequest.getContactNumber());
        users.setUsername(signupUserRequest.getUserName());
        users.setSalt("1234abc");
        final Users createdUsers = signupBusinessService.signup(users);
        SignupUserResponse userResponse = new SignupUserResponse().id(createdUsers.getUuid()).status("REGISTERED");
        return new ResponseEntity<SignupUserResponse>(userResponse, HttpStatus.CREATED);
    }
}

