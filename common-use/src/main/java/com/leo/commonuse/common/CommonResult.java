package com.leo.commonuse.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Liu
 */
@Data
public class CommonResult {

    /**
     * 成功
     */
    public static Integer SUCCESS = 20000;

    /**
     * 失败
     */
    public static Integer ERROR = 50000;

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<>();

    /**把构造方法私有
     *
     */
    private CommonResult() {}

    //成功静态方法
    public static CommonResult ok() {
        CommonResult r = new CommonResult();
        r.setSuccess(true);
        r.setCode(SUCCESS);
        r.setMessage("success");
        return r;
    }

    //失败静态方法
    public static CommonResult error() {
        CommonResult r = new CommonResult();
        r.setSuccess(false);
        r.setCode(ERROR);
        r.setMessage("fail");
        return r;
    }

    public CommonResult success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public CommonResult message(String message){
        this.setMessage(message);
        return this;
    }

    public CommonResult code(Integer code){
        this.setCode(code);
        return this;
    }

    public CommonResult data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public CommonResult data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
