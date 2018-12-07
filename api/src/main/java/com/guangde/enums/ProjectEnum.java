package com.guangde.enums;

/**
 * Created by ZHL on 2018/11/13.
 */
public enum ProjectEnum {
    ProjectIsRecommend(1, "推荐项目"),
    ProjectNotHide(0, "项目显示"),
    ProjectSender(240, "项目募捐中"),
    ProjectEnd(260, "项目结束"),
    ;

    private int code;

    private String msg;

    ProjectEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
