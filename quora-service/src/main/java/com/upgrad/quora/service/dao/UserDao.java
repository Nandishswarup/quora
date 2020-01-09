package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.ZonedDateTime;

@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Users createUser(Users users) {
        entityManager.persist(users);
        return users;
    }


   /* public SignupUserRequest getUser(final String userUuid) {

        try {
            return entityManager.createNamedQuery("userByUuid", SignupUserRequest.class).setParameter("uuid", userUuid).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }*/

    public Users getUserByEmail(final String email) {

        try {
           /* String sql = "SELECT * FROM users AS UserEntity WHERE email = ?";

            javax.persistence.Query query = entityManager.createNativeQuery(sql, Users.class);
            query.setParameter(1, email);
            return  (Users) query.getSingleResult();
*/

            return entityManager.createNamedQuery("userByEmail", Users.class).setParameter("email", email).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Users getuserByid(final int id) {

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



    @Transactional
    public void updateUser(final Users updatedUserEntity) {
        entityManager.merge(updatedUserEntity);
    }

    @Transactional
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

    @Modifying
    @Query("delete from Users u where u.id=:id")
    public void deleteUser(@Param("id") Integer id){

    }


    @Query("update UserAuthTokenEntity c set c.logout_at = :logoutAt WHERE c.access_token = :accessToken")
    public Users signoutUser(@Param("logoutAt") ZonedDateTime logoutAt, @Param("accessToken") String accessToken) {
        int iduser= checkAuthToken(accessToken).getId();

        return getuserByid(iduser);
    }
}
