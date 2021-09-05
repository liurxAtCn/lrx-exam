package net.exam.assess.lrxexam.dao;

import net.exam.assess.lrxexam.entity.UserActivityEntity;
import net.exam.assess.lrxexam.entity.UserEntity;
import org.springframework.stereotype.Repository;

/**
 * @Aytor lrx
 * @Date 2021-09-05
 * dao, access user and user activity info from derby db.
 * more info about schema,table, index, refer to according mappping file.
 * due to derby not support check whether schema or table exists, thus,
 * the init method createXXX() will fail when execute more than once.
 *
 */


@Repository
public interface UserDao {

    /**
     * create schema
     */
    void createSchema();

    /**
     * set schema xxxx
     */
    void setSchema();

    /**
     * create user table t_user
     */
    void createUserTable();

    /**
     * create user activity table t_user_activity
     */
    void createUserActivityTable();

    /**
     * create an new user
     * @param ent
     * @return
     */
    long createUser( UserEntity ent);

    /**
     * create an new user activity
     * @param activity
     * @return
     */
    long createUserActivity(UserActivityEntity activity);

    /**
     * fina user by primary key
     * @param id
     * @return
     */
    UserEntity findUserById( long id);

    /**
     * fina user by email
     * email indicate an unique user.
     * @param email
     * @return
     */
    UserEntity findUserByEmail( String email);

    /**
     * find user`s login info
     * @param userId
     * @return
     */
    UserActivityEntity findActivityByUserId( long userId);

    /**
     * find user activity by temporal token
     * due to the login status was temporal, thus save them to db was not suitable.
     * the better way should be saved onto cache like redis,
     * but, it`s too costly to raise a redis, thus , derby was adopted
     * @param token
     * @return
     */
    UserActivityEntity findActivityByToken( String token);

    /**
     * del activity record
     * @param token
     * @return
     */
    int delActivityByToken( String token);
}
