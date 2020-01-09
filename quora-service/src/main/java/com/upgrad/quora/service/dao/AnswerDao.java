package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.AnswerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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



}
