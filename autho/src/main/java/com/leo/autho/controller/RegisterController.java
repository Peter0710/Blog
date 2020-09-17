package com.leo.autho.controller;

import com.leo.autho.feign.MemberFeignService;
import com.leo.autho.vo.Member;
import com.leo.commonuse.common.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Liu
 */
@RestController
public class RegisterController {

    @Autowired
    MemberFeignService memberFeignService;

    @PostMapping("/register")
    public CommonResult register(@RequestBody Member member){
        CommonResult commonResult = memberFeignService.addMember(member);
        if (commonResult.getCode()==500){
            return CommonResult.error();
        }
        return CommonResult.ok();
    }
}
