package net.exam.assess.lrxexam.web;

import com.google.common.collect.Maps;
import net.exam.assess.lrxexam.constants.DefaultVersion;
import net.exam.assess.lrxexam.domain.User;
import net.exam.assess.lrxexam.domain.Version;
import net.exam.assess.lrxexam.entity.UserEntity;
import net.exam.assess.lrxexam.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.Map;


/**
 * @Author：lrx
 * @Date: 2021-09-03
 * An service entry page, include:
 */
@Controller
public class UserWeb {

    private static final Logger LOGGER = LoggerFactory.getLogger( UserWeb.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "redirect:/docs";
    }

    @RequestMapping(value = "/heartbeat", method = RequestMethod.GET)
    @ResponseBody
    public Version doHeartBeat(){
        return DefaultVersion.getDefaultVersion();
    }

    @RequestMapping(value="/docs", method = RequestMethod.GET)
    public String docs(){
        return "guide";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> createUser( User input){
        LOGGER.info( "createUser-->{}", input);

        UserEntity userEnt = new UserEntity();
        BeanUtils.copyProperties(  input, userEnt);
        Timestamp now = new Timestamp( System.currentTimeMillis());
        userEnt.setCreated( now);
        userEnt.setCreatedBy( "user");
        userEnt.setModified( now);
        userEnt.setModifiedBy( "user");

        long id = userService.createUser( userEnt);
        Map<String,String> result = Maps.newHashMap();
        result.put( "id", String.valueOf(id));
        return result;
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> userLogin( User input){
        LOGGER.info( "userLogin-->{}", input);
        UserEntity user = new UserEntity();
        user.setEmail( input.getEmail());
        user.setPassword( input.getPassword());
        String  newToken = userService.login( user);

        Map<String,String> result = Maps.newHashMap();
        result.put( "token", newToken);
        return result;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,String> userInfo(  String token){
        LOGGER.info( "getUserInfo-->{}", token);
        UserEntity user = userService.getLoginUserInfo( token);

        Map<String,String> result = Maps.newHashMap();
        result.put( "id", String.valueOf( user.getId()));
        result.put( "first", user.getFirst());
        result.put( "last", user.getLast());
        result.put( "email", user.getEmail());
        return result;
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.POST)
    @ResponseBody
    public String logout(  String token){
        LOGGER.info( "logout-->{}", token);
        userService.logout( token);
        return "succeed to logout!";
    }





}
