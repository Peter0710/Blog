package com.leo.autho.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leo.commonuse.common.CommonParam;
import com.leo.autho.feign.MemberFeignService;
import com.leo.autho.vo.Member;
import com.leo.autho.vo.WeiBoUser;
import com.leo.commonuse.common.CommonResult;
import com.leo.commonuse.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Liu
 */
@Controller
public class OAuthLogin {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    MemberFeignService memberFeignService;
    /**
     * 请求到微博的登录授权页http://api.weibo.com/oauth2/authorize?client_id=xxxxx&response_type=code&redirect=XXXXX
     * 返回code,然后用code换取社交登录的access_token,即访问令牌
     * code换取access-token只能使用一次
     * 同一个用户的access_token在一段时间内是不会变换的，在一段时间按是有效的
     * @return
     */

    @GetMapping("/oauth2/success")
    public String weibo(@RequestParam("code") String code, HttpServletRequest request) throws Exception {
        Map<String,String> header = new HashMap<>();
        Map<String,String> query = new HashMap<>();

        Map<String,String> map = new HashMap<>();
        map.put("client", "2324566");
        map.put("client_secret","");
        map.put("grant_type", "authorization_code");
        map.put("redirect_uri","http://auth.leo.com/oauth2/success");
        map.put("code",code);

        //换取access_token ,使用httpUtils（自定义方法？？？？？） 调用
        HttpResponse response = HttpUtils.doPost("https://api.weibo.com", "/oauth2/access_token", "post", header, query, map);
        if(response.getStatusLine().getStatusCode()==200){
            String string = EntityUtils.toString(response.getEntity());
            WeiBoUser weiBoUser = JSON.parseObject(string, WeiBoUser.class);
            //第一次社交登录。需要先注册,同一个第三方账号登录，uid是一致的
            //已经登录过，不用再注册，直接登录
            CommonResult commonResult = memberFeignService.authLogin(weiBoUser);
            if (commonResult.getCode().equals(CommonResult.SUCCESS)){
                Map<String, Object> data = commonResult.getData();
                ObjectMapper mapper = new ObjectMapper();
                Member member = mapper.convertValue(data.get("member"), Member.class);
                HttpSession session = request.getSession();
                session.setAttribute(CommonParam.SESSIONKEY, member);
                return "redirect:http://leo.com";
            }else {
                return "redirect:http://leo.com/login.html";
            }
        } else {
            return "redirect:http://leo.com/login.html";
        }
    }
}
