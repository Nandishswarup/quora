package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.Users;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Users createUser(Users users) {
        entityManager.persist(users);
        return users;
    }

 
    public Users getUserByEmail(final String email) {

        try {
            return entityManager.createNamedQuery("userByEmail", Users.class).setParameter("email", email).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Users getUserByid(final int id) {

        try {
            return entityManager.createNamedQuery("userById", Users.class).setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public UserAuthTokenEntity checkAuthToken(final String accessToken) {

        try {
            return entityManager.createNamedQuery("userAuthTokenByAccessToken", UserAuthTokenEntity.class).setParameter("accessToken", accessToken).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Users getUserByUserName(final String userName) {

        try {
            return entityManager.createNamedQuery("userByUserName", Users.class).setParameter("userName", userName)
                   .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }



    public void updateUser(final Users updatedUserEntity) {
        entityManager.merge(updatedUserEntity);
    }

    public UserAuthTokenEntity createAuthToken(final UserAuthTokenEntity userAuthTokenEntity) {
        entityManager.persist(userAuthTokenEntity);
        return userAuthTokenEntity;
    }

    public Users getUserDetail(Integer id) {

        try {
            return entityManager.createNamedQuery("userById", Users.class).setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }


    @Transactional
    public void deleteUser(@Param("id") Integer id){
        try {
            entityManager.createNamedQuery("deleteUserById", Users.class)
                    .setParameter(1, id)
                    .executeUpdate();

            //Execute the delete query
            entityManager.flush();

        } catch (Exception e) {
            e.printStackTrace();
            // return false;
        }
    }
    @Transactional
    public UserAuthTokenEntity signoutUser(final UserAuthTokenEntity userAuthTokenEntity) {
        userAuthTokenEntity.setLogout_at( ZonedDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
        entityManager.merge(userAuthTokenEntity);
        return  userAuthTokenEntity;
    }
}
