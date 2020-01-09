package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.QuestionDeleteResponse;
import com.upgrad.quora.api.model.QuestionEditResponse;
import com.upgrad.quora.api.model.QuestionRequest;
import com.upgrad.quora.api.model.QuestionResponse;
import com.upgrad.quora.service.business.QuestionControllerService;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import com.upgrad.quora.service.exception.SignUpRestrictedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/")
public class QuestionController {

    @Autowired
    private QuestionControllerService questionControllerService;


    @RequestMapping(method = RequestMethod.POST, path = "/question/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionResponse> createQuestion(final QuestionRequest questionRequest, Integer user_id, String access_token) throws SignUpRestrictedException, AuthorizationFailedException {
        final QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setContent(questionRequest.getContent());
        questionEntity.setDate(ZonedDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
        questionEntity.setUser_id(user_id);

        final QuestionEntity createdQuestion = questionControllerService.create(questionEntity, access_token);
        QuestionResponse userResponse = new QuestionResponse().id(String.valueOf(createdQuestion.getId())).status("QUESTION CREATED");
        return new ResponseEntity<QuestionResponse>(userResponse, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/question/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<QuestionEntity>> getAllQuestions(@RequestParam String accesstoken) throws  AuthorizationFailedException{
         List<QuestionEntity> questionEntityList = questionControllerService.getAll(accesstoken);
         return new ResponseEntity<List<QuestionEntity>>(questionEntityList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "question/all/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<QuestionEntity>> getAllQuestionsOfUser(@PathVariable("userId") Integer userId,@RequestParam String accesstoken) throws SignUpRestrictedException, AuthorizationFailedException, UserNotFoundException {
        List<QuestionEntity> questionEntityList = questionControllerService.getAllQuestionsOfUser(userId,accesstoken);
        return new ResponseEntity<List<QuestionEntity>>(questionEntityList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/question/edit/{questionId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionEditResponse> updateQuestion(@PathVariable("questionId") Integer questionId, @RequestParam String accesstoken, @RequestParam String content) throws SignUpRestrictedException, AuthorizationFailedException, UserNotFoundException, InvalidQuestionException {
        QuestionEntity questionEntity=questionControllerService.edit(questionId, accesstoken,content);

        QuestionEditResponse questionEditResponse=new QuestionEditResponse();
        questionEditResponse.setId(String.valueOf(questionEntity.getUuid()));


        return new ResponseEntity<QuestionEditResponse>(questionEditResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/question/delete/{questionId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionDeleteResponse> deleteQuestion(@PathVariable("questionId") Integer questionId, @RequestParam String accesstoken, @RequestParam Integer userId) throws SignUpRestrictedException, AuthorizationFailedException, UserNotFoundException, InvalidQuestionException {

        QuestionDeleteResponse questionDeleteResponse=new QuestionDeleteResponse();
        QuestionEntity questionEntity=questionControllerService.delete(questionId,accesstoken);
        questionDeleteResponse.setId(String.valueOf(questionEntity.getUuid()));
        return new ResponseEntity<QuestionDeleteResponse>(questionDeleteResponse, HttpStatus.OK);


    }



}
