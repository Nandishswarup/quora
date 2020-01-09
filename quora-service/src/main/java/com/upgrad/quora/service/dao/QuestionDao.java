package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.QuestionEntity;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class QuestionDao {


    @PersistenceContext
    private EntityManager entityManager;

    public QuestionEntity create(QuestionEntity questionEntity) {
        entityManager.persist(questionEntity);
        return questionEntity;
    }

    public List<QuestionEntity> getAll() {

        try {
            return entityManager.createNamedQuery("getAll", QuestionEntity.class).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<QuestionEntity> getAllQuestionOfUser(Integer userId) {

        try {
            return entityManager.createNamedQuery("getAllOfUser", QuestionEntity.class).setParameter("userId", userId).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public QuestionEntity getQuestionById(Integer questionId) {

        try {
            return entityManager.createNamedQuery("getById", QuestionEntity.class).setParameter("questionId", questionId).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public void updateQuestion(final QuestionEntity updatedQuestion) {
        entityManager.merge(updatedQuestion);
    }

    @Transactional
    public void deleteQuestion(@Param("id") Integer id) {
        try {
            entityManager.createNamedQuery("deleteQuestionById", QuestionEntity.class)
                    .setParameter(1, id)
                    .executeUpdate();

            //Execute the delete query
            entityManager.flush();

        } catch (Exception e) {
            e.printStackTrace();
            // return false;
        }
    }

}
