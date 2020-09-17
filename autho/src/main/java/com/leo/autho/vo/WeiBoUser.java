package com.leo.autho.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Liu
 */
@Data
public class WeiBoUser implements Serializable {

    private String access_token;

    private String remind_in;

    private long expires_in;

    private String uid;

    private String isRealName;
}
