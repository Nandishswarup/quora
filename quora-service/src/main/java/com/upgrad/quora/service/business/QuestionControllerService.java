package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.Users;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionControllerService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private QuestionDao questionDao;

    @Transactional(noRollbackFor = {TransactionException.class})
    public QuestionEntity create(QuestionEntity questionEntity, String access_token) throws AuthorizationFailedException {

        UserAuthTokenEntity userAuthTokenEntity = userDao.checkAuthToken(access_token);
        if (userAuthTokenEntity == null) {
            throw new AuthorizationFailedException("ATHR-001'", "User has not signed in");
        }
        questionEntity.setUuid(userAuthTokenEntity.getUuid());

        return questionDao.create(questionEntity);


    }

    @Transactional(noRollbackFor = {TransactionException.class})
    public List<QuestionEntity> getAll(String access_token) throws AuthorizationFailedException {
        UserAuthTokenEntity userAuthTokenEntity = userDao.checkAuthToken(access_token);
        if (userAuthTokenEntity == null) {
            throw new AuthorizationFailedException("ATHR-001'", "User has not signed in");
        }
        return questionDao.getAll();

    }

    public List<QuestionEntity> getAllQuestionsOfUser(Integer userId, String access_token) throws AuthorizationFailedException {

        UserAuthTokenEntity userAuthTokenEntity = userDao.checkAuthToken(access_token);
        if (userAuthTokenEntity == null) {
            throw new AuthorizationFailedException("ATHR-001'", "User has not signed in");
        }
        return questionDao.getAllQuestionOfUser(userId);



    }

    @Transactional(noRollbackFor = {TransactionException.class})
    public QuestionEntity edit(Integer questionId, String access_token, String content) throws AuthorizationFailedException, InvalidQuestionException {
        UserAuthTokenEntity userAuthTokenEntity = userDao.checkAuthToken(access_token);
        if (userAuthTokenEntity == null) {
            throw new AuthorizationFailedException("ATHR-001'", "User has not signed in");
        }
        QuestionEntity questionEntity = questionDao.getQuestionById(questionId);
        if (questionEntity == null)
            throw new InvalidQuestionException("QUES-001'", "Entered question uuid does not exist");

        if (userAuthTokenEntity.getId() != questionEntity.getUser_id()) {
            throw new AuthorizationFailedException("ATHR-003'", "Only the question owner can edit the question");
        }


        questionEntity.setContent(content);
        questionDao.updateQuestion(questionEntity);
        return questionEntity;
    }

    public QuestionEntity delete(Integer questionId, String access_token) throws AuthorizationFailedException, InvalidQuestionException {

        UserAuthTokenEntity userAuthTokenEntity = userDao.checkAuthToken(access_token);
        if (userAuthTokenEntity == null) {
            throw new AuthorizationFailedException("ATHR-001'", "User has not signed in");
        }

        QuestionEntity questionEntity = questionDao.getQuestionById(questionId);
        if (questionEntity == null)
            throw new InvalidQuestionException("QUES-001'", "Entered question uuid does not exist");



        Users users = userDao.getuserByid(userAuthTokenEntity.getId());
        if (userAuthTokenEntity.getId() != questionEntity.getUser_id()) {
            if(!users.getRole().contentEquals("admin"))
            throw new AuthorizationFailedException("ATHR-003'", "Only the question owner or admin can delete the question");
        }



        questionDao.deleteQuestion(questionId);
        return questionEntity;
    }


}
