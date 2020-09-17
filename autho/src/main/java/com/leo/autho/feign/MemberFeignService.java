package com.leo.autho.feign;

import com.leo.autho.vo.Member;
import com.leo.autho.vo.WeiBoUser;
import com.leo.commonuse.common.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Liu
 */
@FeignClient(name = "member")
public interface MemberFeignService {

    @PostMapping("/member/authLogin")
    CommonResult authLogin(@RequestBody WeiBoUser weiBoUser) throws Exception;

    @PostMapping("/member/addMember")
    CommonResult addMember(@RequestBody Member member);

    @PostMapping("/member/getOne")
    CommonResult getOneMember(@RequestBody Member member);
}
