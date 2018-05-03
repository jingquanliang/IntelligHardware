package controller;

import Utility.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
// import Host;

@RestController
@EnableAutoConfiguration
public class LoginController {

   /**
     * 引入日志，注意都是"org.slf4j"包下
     */
    private final static Logger logger = LoggerFactory.getLogger(SampleController.class);

    @RequestMapping("/login")
    String login(HttpSession session,@RequestParam(value="name")String name,@RequestParam(value="password")String password) {

        logger.info("name="+name);
        logger.error("password="+password);
        session.setAttribute(name,password);
        return Host.getIPAndHostName()+"-name="+name+"-password="+password;
    }
}