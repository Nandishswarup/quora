package com.upgrad.quora.service.entity;

import javax.persistence.*;
import java.time.ZonedDateTime;


@Entity
@Table(name = "answer")
@NamedQueries(
        {
                @NamedQuery(name = "getAnswerById", query = "select u from AnswerEntity u where u.id=:answerId"),
                @NamedQuery(name = "getAllAnswersOfQuestion", query = "select u from AnswerEntity u where u.question_id=:question_id"),
        }
)
public class AnswerEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    int id;
    @Column(name = "uuid")
    String uuid;
    @Column(name = "ans")
    String ans;
    @Column(name = "date")
    ZonedDateTime date;
    @Column(name = "user_id")
    int user_id;
    @Column(name = "question_id")
    int question_id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }
}
