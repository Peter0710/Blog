package com.leo.member.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leo.member.entity.Item;
import com.leo.member.entity.Member;
import com.leo.member.vo.WeiBoUser;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @author Liu
 */
public interface MemberService {

    /**
     *
     * @return
     */
    List<Member> getAllMember(Page<Member> pageInfo);

    Member getOneMember(Member member);

    Integer addMember(Member member);

    Member getMemberById(String id);

    Member login(WeiBoUser weiBoUser) throws Exception;
}
