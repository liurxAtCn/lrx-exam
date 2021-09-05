package net.exam.assess.lrxexam.service;

import net.exam.assess.lrxexam.entity.UserEntity;

/**
 * @Aytor lrx
 * @Date 2021-09-05
 * service api interface,
 *
 */
public interface UserService {

    /**
     * create user
     * @param user
     * @return when succeeded, id of new user,
     *         else, an RuntimeException was raised
     */
    long createUser( UserEntity user);

    /**
     * user login
     * @param user
     * @return when succeeded, the temporal token, will be used in getLongUserInfo or logout
     *         else. an RuntimeException was raised
     */
    String login( UserEntity user);

    /**
     *
     * @param token token from login
     * @return
     */
    UserEntity getLoginUserInfo( String token);

    /**
     *
     * @param token
     */
    void logout( String token);

}
