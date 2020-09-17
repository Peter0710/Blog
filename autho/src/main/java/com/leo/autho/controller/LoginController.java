package com.leo.autho.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leo.commonuse.common.CommonParam;
import com.leo.autho.feign.MemberFeignService;
import com.leo.autho.vo.Member;
import com.leo.commonuse.common.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author Liu
 */
@RestController("/admin")
public class LoginController {

    @Autowired
    MemberFeignService memberFeignService;

    @PostMapping("/dologin")
    public CommonResult doLogin(@RequestBody Member memberVo, HttpServletRequest request){
        CommonResult oneMember = memberFeignService.getOneMember(memberVo);
        if (oneMember.getCode() == 50000){
            return CommonResult.error();
        }
        Map<String, Object> data = oneMember.getData();
        //rpc 远程调用的情况下使用的httpclient,传递参数的时候是需要顺序的，所以map中的值也按照顺序传递出去了，所以才会变成linkedHashMap
        ObjectMapper mapper = new ObjectMapper();
        Member member = mapper.convertValue(data.get("member"), Member.class);
        System.out.println(member);
        HttpSession session = request.getSession();
        session.setAttribute(CommonParam.SESSIONKEY, member);
        return CommonResult.ok();
    }

}
