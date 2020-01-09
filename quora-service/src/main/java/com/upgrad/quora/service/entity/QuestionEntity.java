package com.upgrad.quora.service.entity;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "question")

@SqlResultSetMapping(name="deleteResult", columns = { @ColumnResult(name = "count")})
@NamedNativeQueries({
        @NamedNativeQuery(
                name    =   "deleteEmployeeById",
                query   =   "DELETE FROM question WHERE id = ?1",resultSetMapping = "deleteResult"
        )
})

@NamedQueries(
        {
                @NamedQuery(name = "getAll", query = "select u from QuestionEntity u"),
                @NamedQuery(name = "getById", query = "select u from QuestionEntity u where u.id=:questionId"),
                @NamedQuery(name = "getAllOfUser", query = "select u from QuestionEntity u where u.user_id=:userId"),


        }
)
public class QuestionEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Integer id;
    @Column(name = "uuid")
    String uuid;
    @Column(name = "content")
    String content;
    @Column(name = "date")
    ZonedDateTime date;
    @Column(name = "user_id")
    Integer user_id;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
