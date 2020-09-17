package com.leo.member.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leo.commonuse.common.CommonResult;
import com.leo.member.entity.Item;
import com.leo.member.entity.Member;
import com.leo.member.feign.ItemFeignService;
import com.leo.member.service.impl.MemberService;
import com.leo.member.vo.MemberVo;
import com.leo.member.vo.WeiBoUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Liu
 */
@RestController()
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    ItemFeignService itemFeignService;

    @Qualifier("mainThreadPool")
    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    /**
     * 获取所有用户
     * @return
     */
    @GetMapping("/getAll")
    public CommonResult getAllMember(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        Page<Member> page = new Page<>(pageNum, 5);
        List<Member> allMember = memberService.getAllMember(page);
        return CommonResult.ok().data("data", allMember);
    }

    /**
     * 获取一个用户信息. 用于登录校验
     * @param member
     * @return
     */
    @PostMapping("/getOne")
    public CommonResult getOneMember(@RequestBody Member member){
        Member oneMember = memberService.getOneMember(member);
        if (oneMember == null){
            return CommonResult.error();
        }
        return CommonResult.ok().data("member", oneMember);
    }

    /**
     * 注册用户
     * @param member
     * @return
     */
    @PostMapping("/addMember")
    public CommonResult addMember(@RequestBody Member member){
        Integer integer = memberService.addMember(member);
        if (integer==null){
            return CommonResult.error();
        }
        return CommonResult.ok().message("add success");
    }

    /**
     * 根据用户id获取用户信息
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    public CommonResult getMemberById(@PathVariable String id){
        Member memberById = memberService.getMemberById(id);
        if (memberById == null){
            return CommonResult.error().message("the use is not exit");
        }
        return CommonResult.ok().data("data",memberById);
    }

    /**
     * 获取用户个人信息详细页面，包括个人信息和所发文章简介
     * @param id
     * @return
     */
    @GetMapping("/getMemberDetail/{id}")
    public CommonResult getDetailInfo(@PathVariable String id) throws Exception {
        //通过异步编排缩短页面的刷新时间
        //TODO 查询用户个人信息
        CompletableFuture<MemberVo> memberFuture = CompletableFuture.supplyAsync(() -> {
            Member memberById = memberService.getMemberById(id);
            MemberVo memberVo = new MemberVo();
            //使用beanUtils,只传递部分信息给客户端
            BeanUtils.copyProperties(memberById, memberVo);
            return memberVo;
        }, threadPoolExecutor);
        //TODO 查询用户所发文章（远程调用，查询item 模块）
        CompletableFuture<List<Item>> itemFuture = CompletableFuture.supplyAsync(() -> {
            List<Item> itemByUserId = itemFeignService.getItemByUserId(id, 1);
            return itemByUserId;
        }, threadPoolExecutor);
        //TODO 查询导航栏信息

        CompletableFuture.allOf(memberFuture, itemFuture);
        return CommonResult.ok().data("member", memberFuture.get()).data("item", itemFuture.get());
    }

    /**
     * 用于第三方微博账号的登录，如果已经登录过，则不用将用户信息插入到数据库，
     * 如果用户没有登录过，则先需要插入数据
     * @param weiBoUser
     * @return
     * @throws Exception
     */
    @PostMapping("/authLogin")
    public CommonResult authLogin(@RequestBody WeiBoUser weiBoUser) throws Exception {
        Member oneMember = memberService.login(weiBoUser);
        return CommonResult.ok().data("data", oneMember);
    }

}
