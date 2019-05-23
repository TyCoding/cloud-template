package cn.tycoding.api.common.utils;

import cn.tycoding.api.common.constant.CommonConstants;
import cn.tycoding.api.common.constant.enums.CommonEnums;
import lombok.*;

import java.io.Serializable;

/**
 * @author tycoding
 * @date 2019-05-22
 */
@Builder
@ToString
@AllArgsConstructor
public class Result<T> implements Serializable {

    @Getter
    @Setter
    private int code = CommonConstants.SUCCESS;

    @Getter
    @Setter
    private Object msg = "success";

    @Getter
    @Setter
    private T data;

    public Result() {
        super();
    }

    public Result(T data) {
        super();
        this.data = data;
    }

    public Result(T data, String msg) {
        super();
        this.data = data;
        this.msg = msg;
    }

    public Result(CommonEnums enums) {
        super();
        this.code = enums.getCode();
        this.msg = enums.getMsg();
    }

    public Result(Throwable e) {
        super();
        this.code = CommonConstants.ERROR;
        this.msg = e.getMessage();
    }
}
