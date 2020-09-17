package com.leo.member.vo;

import lombok.Data;

/**
 * @author Liu
 */
@Data
public class WeiBoUser {

    private String access_token;

    private String remind_in;

    private long expires_in;

    private String uid;

    private String isRealName;
}
