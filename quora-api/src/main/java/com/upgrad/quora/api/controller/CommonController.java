package com.upgrad.quora.api.controller;


import com.upgrad.quora.api.model.UserDetailsResponse;
import com.upgrad.quora.service.business.CommonControllerBusinnesService;
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
public class CommonController {

    @Autowired
    private CommonControllerBusinnesService commonControllerBusinnesService;

    @RequestMapping(method = RequestMethod.GET, path = "/userprofile/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> getUserDetail(@PathVariable("userId") Integer userId, @RequestParam String accesstoken) throws SignUpRestrictedException, AuthorizationFailedException, UserNotFoundException {
        Users users = commonControllerBusinnesService.getUserDetail(userId, accesstoken);

        if (users == null)
            throw new UserNotFoundException("USR-001", "User with entered uuid does not exist");


        final UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        userDetailsResponse.setAboutMe(users.getAboutme());
        userDetailsResponse.setContactNumber(users.getContactnumber());
        userDetailsResponse.setCountry(users.getCountry());
        userDetailsResponse.setDob(users.getDob());
        userDetailsResponse.setEmailAddress(users.getEmail());
        userDetailsResponse.setFirstName(users.getFirstname());
        userDetailsResponse.setLastName(users.getLastname());
        userDetailsResponse.setUserName(users.getUsername());


        return new ResponseEntity<UserDetailsResponse>(userDetailsResponse, HttpStatus.OK);
    }
}
