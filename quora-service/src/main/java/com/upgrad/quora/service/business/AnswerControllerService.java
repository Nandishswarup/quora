package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.AnswerDao;
import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;


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
}
