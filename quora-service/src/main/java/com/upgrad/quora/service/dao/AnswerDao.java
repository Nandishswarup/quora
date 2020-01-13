package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.AnswerEntity;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AnswerDao {
    @PersistenceContext
    private EntityManager entityManager;

    public AnswerEntity create(AnswerEntity answerEntity) {
        entityManager.persist(answerEntity);
        return answerEntity;
    }


    public AnswerEntity getAnswerById(Integer answerId) {

        try {
            return entityManager.createNamedQuery("getAnswerById", AnswerEntity.class).setParameter("answerId", answerId).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public void updateAnswer(final AnswerEntity answerEntity) {
        entityManager.merge(answerEntity);
    }

    @Transactional
    public void deleteAnswer(@Param("id") Integer id) {
        try {
            entityManager.createNamedQuery("deleteAnswerById", AnswerEntity.class)
                    .setParameter(1, id)
                    .executeUpdate();

            //Execute the delete query
            entityManager.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<AnswerEntity> getAllAnswerToQuestion(Integer questionId) {

        try {
            return entityManager.createNamedQuery("getAllAnswersOfQuestion", AnswerEntity.class).setParameter("question_id", questionId).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}

