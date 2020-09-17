package com.leo.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leo.commonuse.util.HttpUtils;
import com.leo.member.dao.MemberDao;
import com.leo.member.entity.Item;
import com.leo.member.entity.Member;
import com.leo.member.vo.WeiBoUser;
import io.swagger.models.auth.In;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Liu
 */
@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    MemberDao memberDao;

    @Override
    public List<Member> getAllMember(Page<Member> pageInfo) {
        Page<Member> page = memberDao.selectPage(pageInfo, null);
        List<Member> records = page.getRecords();
        return records;
    }

    @Override
    public Member getOneMember(Member member) {
        QueryWrapper<Member> wrapper = new QueryWrapper<Member>()
                .eq("name", member.getName())
                .eq("password", member.getPassword());
        return memberDao.selectOne(wrapper);
    }

    @Override
    public Integer addMember(Member member) {
        QueryWrapper<Member> wrapper = new QueryWrapper<Member>().eq("name", member.getName());
        if(memberDao.selectOne(wrapper)!=null){
            return null;
        }
        return memberDao.insert(member);
    }

    @Override
    public Member getMemberById(String id) {
        if (memberDao.selectById(id) != null){
            return null;
        }
        return memberDao.selectById(id);
    }

    @Override
    public Member login(WeiBoUser weiBoUser) throws Exception {
        Member third = memberDao.selectOne(new QueryWrapper<Member>().eq("third", weiBoUser.getUid()));
        if (third == null){
            Map<String, String> map = new HashMap<>();
            map.put("access_token", weiBoUser.getAccess_token());
            map.put("uid", weiBoUser.getUid());
            HttpResponse response = HttpUtils.doGet("https://api.weibo.com", "/2/users/show.json", "get", new HashMap<String, String>(), map);
            if (response.getStatusLine().getStatusCode()==200){
                Member member = new Member();
                String string = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = JSON.parseObject(string);
                member.setName(jsonObject.getString("name"));
                member.setThird(weiBoUser.getUid());
                memberDao.insert(member);
                return member;
            }
        }
        return third;
    }
}
