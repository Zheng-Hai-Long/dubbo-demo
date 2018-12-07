package com.guangde.business.enums;


public enum ResultEnum {
    NotLogin(0,"未登录"),Success(1,"成功"),Error(-1,"服务器异常"),NotMoreData(2,"没有更多数据了"),OtherError(3,"其他错误"),EmptyData(4,"数据为空"),SystemError(-5,"系统错误"),ThirdUserExist(-7, "第三方用户已经存在"),
    VerificationCodeExpires(10001,"验证码过期"),VerificationCodeError(10002,"验证码错误"),IncorrectPasswordFormat(10003,"密码格式错误"),IncorrectMobileFormat(10004,"手机号格式错误"),IncorrectNameFormat(10005,"用户名格式错误"),
    GetWeixinCode(10007, "获取微信code"), NotWeixinBrowser(10008, "不是微信浏览器"),RegisterError(10009, "注册失败"),NotExistUser(10010, "用户不存在"), BindMobileError(10011, "此账号已经绑定过手机号"),
    EditMobileError(10012, "此账号未绑定过手机号，请先去绑定手机号"),UnknownBusinessType(10013,"未知的业务类型"),MobileRegistered(10015,"手机号已经被注册过"), ParameterError(10016, "参数错误"),
    NotSetPassWord(10006,"用户密码未设置，请设置密码"),NotEqualPassWord(10017,"原密码错误"),UserNameRegistered(10018,"会员名已经被注册过了"),UserNameNotRegistered(10019,"会员名未被注册"),LoginFail(10020,"登录失败"),
    PassWordError(10021,"密码错误"),IDCardFormatError(10022,"身份证格式错误"),BankCardFormatError(10023,"银行卡格式错误"),ZipCodeError(10024,"邮政编码格式错误"),
    OpenIdOrUnionIdEmpty(10025,"openid或者unionId为空"),
    TransactPasswordIsSet(10029, "用户交易密码已设置过"),SurePasswordNotEqual(10030, "两次密码输入不相同"),
    TransactPasswordNotSet(10031,"用户交易密码未设置"),LoginOutFail(10032,"登出失败"),UserBankCardNotExist(10033,"用户未绑定银行卡"),NotExistHighUserId(10034,"用户没有上阶id"),
    BalanceNotPay(10035,"余额不足"),NotPower(10036,"权限不足"),LoginInvalid(10039,"登录已失效"),
    WxIsBind(10040,"微信号已经被绑定了"),UserLocked(10041,"账户已锁定，请联系客服"),
    AuditFinish(10044,"已审核"),
    ImageSizeError(20001, "上传图片太大，请保持2M内"),ImageTypeError(20002,"上传图片格式出现问题，上传的图片格式jpg、png、gif、bmp"),ImageEmptyError(20003,"图片为空"),
    FileSizeError(30001, "上传文件太大，请保持10M内"),FileTypeError(30002,"上传文件格式出现问题，上传的文件格式pdf、xls、doc、xlsx、docx"),FileEmptyError(30003,"文件为空"),
    OrderError(40001,"订单异常"),ShipmentError(50001,"发货异常"),
    ;
    private int code;

    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static ResultEnum stateOf(int index){
        for (ResultEnum state : values()) {
            if(state.getCode() == index){
                return state;
            }
        }
        return null;
    }
}
