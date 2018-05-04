package controller;

import Utility.Host;
import dao.bean.WxUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
// import Host;

@RestController
@EnableAutoConfiguration
public class LoginController {

    /**
     * 引入日志，注意都是"org.slf4j"包下
     */
    private final static Logger logger = LoggerFactory.getLogger(SampleController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestOperations restOperations;

    @RequestMapping("/login")
    String loginFromWX(HttpSession session, @RequestParam(value = "type") int type, @RequestParam(value = "code") String code) {

        WxUserInfo userInfo = null;
        if (type == 0) {
            //from weixin
            logger.info("from weixin");

//            RestTemplate restTemplate = new RestTemplate();
            String APPID = "wxe75bc768e291fd04";
            String SECRET = "9afb6fc9e310fea8e4350266ab3fa641";
            String JSCODE = code;
            String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APPID + "&secret=" + SECRET + "&js_code=" + JSCODE + "&grant_type=authorization_code";
            userInfo = restTemplate.getForObject(url, WxUserInfo.class);
            logger.info(userInfo.toString());
//            String userInfoStr = restTemplate.getForObject(url, String.class);
//            logger.info(userInfoStr);

        }
        if (userInfo.getErrcode() != 40029)
            return Host.getIPAndHostName() + "-getOpenid=" + userInfo.getOpenid() + "-getSession_key=" + userInfo.getSession_key();
        else
            return "0";
    }

    @RequestMapping("/loginFromApp")
    String loginFromApp(HttpSession session, @RequestParam(value = "type") int type, @RequestParam(value = "code") String code, @RequestParam(value = "name") String name, @RequestParam(value = "password") String password) {

        if (type == 0) {
            //from weixin
            logger.info("from weixin");

            RestTemplate restTemplate = new RestTemplate();
            String APPID = "wxe75bc768e291fd04";
            String SECRET = "9afb6fc9e310fea8e4350266ab3fa641";
            String JSCODE = code;
            String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APPID + "&secret=" + SECRET + "&js_code=" + JSCODE + "&grant_type=authorization_code";
            WxUserInfo quote = restTemplate.getForObject(url, WxUserInfo.class);
            logger.info(quote.toString());
            logger.info(quote.toString());

        } else {
            //from app or web
            logger.info("name=" + name);
            logger.error("password=" + password);
            session.setAttribute(name, password);
        }
        return Host.getIPAndHostName() + "-name=" + name + "-password=" + password;
    }


}