package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.AnswerDao;
import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.Users;
import com.upgrad.quora.service.exception.AnswerNotFoundException;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AnswerControllerService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private AnswerDao answerDao;
    @Autowired
    QuestionDao questionDao;


    @Transactional(noRollbackFor = {TransactionException.class})
    public AnswerEntity create(AnswerEntity answerEntity, String access_token, Integer questionId) throws AuthorizationFailedException, InvalidQuestionException {


        UserAuthTokenEntity userAuthTokenEntity = userDao.checkAuthToken(access_token);
        if (userAuthTokenEntity == null) {
            throw new AuthorizationFailedException("ATHR-001'", "User has not signed in");
        }
        QuestionEntity questionEntity=questionDao.getQuestionById(questionId);
        if(questionEntity==null)
            throw new InvalidQuestionException("ATHR-001'", "Enter valid Question ID");

        answerEntity.setUuid(userAuthTokenEntity.getUuid());
        answerEntity.setUser_id(userAuthTokenEntity.getUser_id());

        return answerDao.create(answerEntity);



    }

    @Transactional(noRollbackFor = {TransactionException.class})
    public AnswerEntity editAnswer(Integer answerId, String access_token,  String content) throws AuthorizationFailedException, AnswerNotFoundException {

        UserAuthTokenEntity userAuthTokenEntity = userDao.checkAuthToken(access_token);
        if (userAuthTokenEntity == null) {
            throw new AuthorizationFailedException("ATHR-001'", "User has not signed in");
        }
        AnswerEntity answerEntity = answerDao.getAnswerById(answerId);
        if (answerEntity == null)
            throw new AnswerNotFoundException("QUES-001'", "Entered answer uuid does not exist");


        if (userAuthTokenEntity.getId() != answerEntity.getUser_id()) {
            throw new AuthorizationFailedException("ATHR-003'", "Only the answer owner can edit the answer");

        }


        answerEntity.setAns(content);
        answerDao.updateAnswer(answerEntity);
        return answerEntity;
    }

    public AnswerEntity delete(Integer answerId, String access_token) throws AuthorizationFailedException, AnswerNotFoundException {
        UserAuthTokenEntity userAuthTokenEntity = userDao.checkAuthToken(access_token);
        if (userAuthTokenEntity == null) {
            throw new AuthorizationFailedException("ATHR-001'", "User has not signed in");
        }
        AnswerEntity answerEntity = answerDao.getAnswerById(answerId);
        if (answerEntity == null)
            throw new AnswerNotFoundException("QUES-001'", "Entered answer uuid does not exist");


        Users users = userDao.getuserByid(userAuthTokenEntity.getId());

        if (userAuthTokenEntity.getId() != answerEntity.getUser_id()) {
            if(!users.getRole().contentEquals("admin"))
                throw new AuthorizationFailedException("ATHR-003'", "Only the answer owner or admin can delete the answer");
        }



        answerDao.deleteAnswer(answerId);
        return answerEntity;

    }

    @Transactional(noRollbackFor = {TransactionException.class})
    public List<AnswerEntity> getAllAnswerToQuestion(Integer questionId, String access_token) throws AuthorizationFailedException {

        UserAuthTokenEntity userAuthTokenEntity = userDao.checkAuthToken(access_token);
        if (userAuthTokenEntity == null) {
            throw new AuthorizationFailedException("ATHR-001'", "User has not signed in");
        }
        return answerDao.getAllAnswerToQuestion(questionId);



    }
}
