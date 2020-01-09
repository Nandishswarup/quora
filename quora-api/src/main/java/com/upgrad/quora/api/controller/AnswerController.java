package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.AnswerDeleteResponse;
import com.upgrad.quora.api.model.AnswerEditResponse;
import com.upgrad.quora.api.model.AnswerRequest;
import com.upgrad.quora.api.model.AnswerResponse;
import com.upgrad.quora.service.business.AnswerControllerService;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.exception.*;
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
public class AnswerController {

    @Autowired
    private AnswerControllerService answerControllerService;

    @RequestMapping(method = RequestMethod.POST, path = "/question/{questionId}/answer/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerResponse> createQuestion(@PathVariable("questionId") Integer questionId, String access_token, AnswerRequest answerRequest) throws AuthorizationFailedException, InvalidQuestionException {
        final AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setAns(answerRequest.getAnswer());
        answerEntity.setDate(ZonedDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
        answerEntity.setQuestion_id(questionId);
        final AnswerEntity answerEntitynew = answerControllerService.create(answerEntity, access_token, questionId);
        AnswerResponse answerResponse = new AnswerResponse().id(String.valueOf(answerEntitynew.getId())).status("ANSWER CREATED");
        return new ResponseEntity<AnswerResponse>(answerResponse, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/answer/edit/{answerId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerEditResponse> updateAnswer(@PathVariable("answerId") Integer answerId, @RequestParam String accesstoken, @RequestParam String content) throws AuthorizationFailedException, AnswerNotFoundException {
        AnswerEntity answerEntity = answerControllerService.editAnswer(answerId, accesstoken, content);

        AnswerEditResponse answerEditResponse = new AnswerEditResponse();
        answerEditResponse.setId(String.valueOf(answerEntity.getUuid()));


        return new ResponseEntity<AnswerEditResponse>(answerEditResponse, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.DELETE, path = "/answer/delete/{answerId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerDeleteResponse> deleteQuestion(@PathVariable("answerId") Integer answerId, @RequestParam String accesstoken) throws AuthorizationFailedException, AnswerNotFoundException {

        AnswerDeleteResponse answerDeleteResponse = new AnswerDeleteResponse();
        AnswerEntity answerEntity = answerControllerService.delete(answerId, accesstoken);
        answerDeleteResponse.setId(String.valueOf(answerEntity.getUuid()));
        return new ResponseEntity<AnswerDeleteResponse>(answerDeleteResponse, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, path = "answer/all/{questionId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<AnswerEntity>> getAllAnswerOfQuestion(@PathVariable("questionId") Integer questionId, @RequestParam String accesstoken) throws SignUpRestrictedException, AuthorizationFailedException, UserNotFoundException {
        List<AnswerEntity> answerEntityList = answerControllerService.getAllAnswerToQuestion(questionId,accesstoken);
        return new ResponseEntity<List<AnswerEntity>>(answerEntityList, HttpStatus.OK);
    }

}
