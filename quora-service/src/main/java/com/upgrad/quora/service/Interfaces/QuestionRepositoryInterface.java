package com.upgrad.quora.service.Interfaces;

import com.upgrad.quora.service.entity.QuestionEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface QuestionRepositoryInterface extends CrudRepository<QuestionEntity, Long> {

    @Modifying
    @Transactional
    @Query("delete from QuestionEntity u where u.id=:id")
    void deleteQuestion(Integer id);



}
