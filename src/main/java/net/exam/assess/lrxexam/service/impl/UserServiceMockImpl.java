package net.exam.assess.lrxexam.service.impl;

import net.exam.assess.lrxexam.dao.UserDao;
import net.exam.assess.lrxexam.domain.User;
import net.exam.assess.lrxexam.entity.UserActivityEntity;
import net.exam.assess.lrxexam.entity.UserEntity;
import net.exam.assess.lrxexam.service.TokenGenerator;
import net.exam.assess.lrxexam.service.UserService;
import net.exam.assess.lrxexam.utils.SecurityTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Aytor lrx
 * @Date 2021-09-05
 * user service implement
 *
 */
@Service
public class UserServiceMockImpl implements UserService {
    private final static Logger LOGGER = LoggerFactory.getLogger( UserServiceMockImpl.class);

    private static final Map<String, User> deposit = new ConcurrentHashMap<>();

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private UserDao userDao;

    /**
     * since we use a memory db derby, we need inti it firstly, then it can be approached.
     */
    @PostConstruct
    public void init_user_db(){
        initSchema();
        initUserTable();
        initUserActivityTable();

        LOGGER.info("succeed to init all db");
    }

    private void initSchema(){
        LOGGER.info("start init schema");
        try {
            userDao.createSchema();
        } catch (Exception e) {
            LOGGER.error("",e);
        }
        try {
            userDao.setSchema();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("start init schema");
    }



    private void initUserTable(){
        LOGGER.info("start init t_user");
        try {
            userDao.createUserTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUserActivityTable(){
        LOGGER.info("start init t_user_activity");
        try {
            userDao.createUserActivityTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public long createUser( UserEntity user) {
        try{
            LOGGER.info( "will insert=-->{}", user);
            user.setPassword( SecurityTools.getFootPrint( user.getPassword()));
            long id = userDao.createUser( user);
            return id;
        }catch ( Exception e){
            LOGGER.error("exception when create user",e);
            throw new RuntimeException( e);
        }
    }

    @Override
    public String login( UserEntity input){
        UserEntity user = verifyUserLogin( input);
        UserActivityEntity uae = tryGetLoginToken( user.getId());
        if( null != uae){
            return uae.getToken();
        }
        return this.generateToken( user);
    }

    private UserEntity verifyUserLogin( UserEntity input){
        String errMsg =  null;
        String email = input.getEmail();
        UserEntity user = userDao.findUserByEmail( input.getEmail());
        if( null == user){
            errMsg = "email:"+ email+ " not registered!";
            throw new RuntimeException( errMsg);
        }
        String footprint = SecurityTools.getFootPrint( input.getPassword());
        if(!Objects.equals( footprint, user.getPassword())){
            errMsg = "email/password not math";
            throw new RuntimeException( errMsg);
        }
        return user;
    }

    private UserActivityEntity tryGetLoginToken( long user_id){
        try {
            UserActivityEntity uae = userDao.findActivityByUserId( user_id);
            return uae;
        } catch (Exception e) {
            LOGGER.warn( "", e);
        }
        return null;
    }

    private String generateToken( UserEntity user){
        String newToken = tokenGenerator.randomId();
        LOGGER.info( "generate newToken:{}", newToken);
        if( null == newToken){
            String errMsg = "System busy, try later";
            throw new RuntimeException( errMsg);
        }
        UserActivityEntity uae = new UserActivityEntity();
        uae.setUserId( user.getId());
        uae.setToken( newToken);
        Timestamp now = new Timestamp( System.currentTimeMillis());
        uae.setCreated( now);
        uae.setCreatedBy( "user");
        uae.setModified( now);
        uae.setModifiedBy( "user");
        userDao.createUserActivity( uae);
        return newToken;
    }

    @Override
    public UserEntity getLoginUserInfo( String token){
        String errMsg = null;
        UserActivityEntity entity = userDao.findActivityByToken( token);
        if( null == entity){
            errMsg = "error token,login please firstly";
            throw new RuntimeException( errMsg);
        }
        UserEntity user = userDao.findUserById( entity.getUserId());
        if( null == entity){
            errMsg = "error user info, login please firstly";
            throw  new RuntimeException( errMsg);
        }
        return user;
    }

    @Override
    public void logout( String token){
        try {
            userDao.delActivityByToken( token);
            LOGGER.info("logout succeed:{}",token);
        } catch (Exception e) {
            LOGGER.error("logout exception",e);
        }
    }
}
