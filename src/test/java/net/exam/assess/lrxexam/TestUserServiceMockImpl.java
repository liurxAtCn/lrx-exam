package net.exam.assess.lrxexam;

import net.exam.assess.lrxexam.entity.UserEntity;
import net.exam.assess.lrxexam.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserServiceMockImpl {
    private final static Logger LOGGER = LoggerFactory.getLogger( TestUserServiceMockImpl.class);

    @Autowired
    private UserService userService;

    @Test
    public void testCreateUser(){
        UserEntity user = new UserEntity();
        user.setFirst("testfirst");
        user.setLast("testlast");
        user.setEmail("test0@test.com");
        user.setPassword("testpwd0");
        Timestamp now = new Timestamp( System.currentTimeMillis());
        user.setCreated( now);
        user.setCreatedBy( "user");
        user.setModified( now);
        user.setModifiedBy( "user");
        long id = userService.createUser( user);
        LOGGER.info( "id==>"+id);
        Assert.assertTrue( id > 0);
    }

    @Test
    public void testLogin(){
        UserEntity user = new UserEntity();
        user.setEmail("test0@test.com");
        user.setPassword("testpwd0");
        String token = userService.login( user);
        LOGGER.info( "token==>"+token);
        Assert.assertNotNull( token);
    }

    @Test
    public void testGetLoginUserInfo(){
        UserEntity user = new UserEntity();
        user.setEmail("test0@test.com");
        user.setPassword("testpwd0");
        String token = userService.login( user);
        LOGGER.info("login token==>{}",token);
        UserEntity dbUser = userService.getLoginUserInfo( token);
        LOGGER.info( "login user==>{}", dbUser);
        Assert.assertNotNull( dbUser);
    }

    @Test
    public void testLogout(){
        UserEntity user = new UserEntity();
        user.setEmail("test0@test.com");
        user.setPassword("testpwd0");
        String token = userService.login( user);
        LOGGER.info("login token==>{}",token);
        userService.logout( token);
        LOGGER.info("logout");
        UserEntity dbUser = userService.getLoginUserInfo( token);
        LOGGER.info( "login user==>{}", dbUser);
        Assert.assertNull( dbUser);
    }

}
