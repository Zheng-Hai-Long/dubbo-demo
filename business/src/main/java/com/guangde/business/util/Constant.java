package com.guangde.business.util;

public interface Constant
{
    /**
     * url 路径分隔符   
     */
    String url_separator = "/";
    
    /**
     * 数据库，数据默认分隔符
     */
    String data_separator = ",";
    
    /**
     * 注册用户
     */
    String DONOR_TYPE_IN = "InternalPers";
    
    /**
     * 未注册用户
     */
    String DONOR_TYPE_OUT = "externalPers";
    
    /**
     * 企业助善
     */
    String COMPANY_GOODHELP = "enterpriseGoodHelp";
    
    /**
     *  企业助捐
     */
    String ENTERPRISE_DONATION = "enterpriseDonation";
    
    /**
     * 企业用户捐助
     */
    String ENTERPRICEUSERS = "enterpriseUsers";
    
    /**
     * 个人捐助
     */
    String PERSONALITEMS = "personalItems";
    
    /**
     * 余额支付
     */
    String FREEZ_PAY = "freezType";
    
    /**
     * 微信捐步
     */
    String WERUN_PAY = "weRunType";

    /**
     * 微信+红包
     */
    String TEN_RED_PAY = "tenRedType";

    /**
     * 支付宝+红包
     */
    String ALI_RED_PAY = "aliRedType";
    
    /**
     * 红包支付
     */
    String RED_PAY = "redpay";
    
    /**
     * 助善项目退款
     */
    String BACK_PAY = "back";
    
    String DONATETYPE_GOODHELPBACK = "goodHelpBack";
    
    interface ConfigParam
    {
        //焦点图
        String focusMap = "focusMap";
        
        //文件基本路径
        String fileBasicURL = "fileBasicURL";
        
        //热门项目
        String hotItem = "hotItem";
    }
    
    /**
     * 配置文件的key值
     * @author phx
     *
     */
    interface Config
    {
        String fileStoragePath = "file.storage.path";
        
        String res_url_pic = "res.url.pic";
    }
    
    /**
     * 未捐款、未打款、未支付
     */
    public static final Integer PAY_STATE_300 = 300;
    
    /**
     * 失败
     */
    public static final Integer PAY_STATE_301 = 301;
    
    /**
     * 成功
     */
    public static final Integer PAY_STATE_302 = 302;
    
    /**
     * 批量捐款成功
     */
    public static final Integer PAY_STATE_305 = 305;
    
    /**
     * 资金进
     */
    public static final Integer CAPITAL_IN = 0;
    
    /**
     * 资金出
     */
    public static final Integer CAPITAL_OUT = 1;
    
    /**
     * 资金进(充值)
     */
    public static final Integer RECAGRGE = 1;
    
    /**
     * 资金进(互助充值)
     */
    public static final Integer HUZHU_RECAGRGE = 13;

    /**
     * 资金进(善库充值)
     */
    public static final Integer GOODLIBRARY_RECAGRGE = 12;
    
    /**
     * 资金进(助善终止退款)
     */
    public static final Integer CAPITAL_BACK = 4;
    
    /**
     * 资金进(审核不通过退款 )
     */
    public static final Integer CAPITAL_NOPASS = 5;
    
    /**
     * 资金进(募捐已完成退款 )
     */
    public static final Integer CAPITAL_COMPLETE = 6;
    
    /**
     * 资金进(募捐项目提前终止退款)
     */
    public static final Integer CAPITAL_STOP = 7;
    
    /**
     * 资金进(募捐)
     */
    public static final Integer DONATE = 0;
    
    /**
     * 资金进(红包)
     */
    public static final Integer DONATE_REDPACKET = 8;
    /**
     * 资金出(红包)
     */
    public static final Integer DONATE_REDPACKET_OUT = 9;
    
    /**
     * 配捐进
     */
    public static final Integer MATCHDONATE_INT = 10;
    

    /**
     * 资金进(批量捐)
     */
    public static final Integer BATCH_DONATE_INT = 12;
    
    /**
     * 资金出(提款)
     */
    public static final Integer DRAW_MONEY_OUT = 3;
    
    /**
     * 资金出(助善)
     */
    public static final Integer DONATE_GOODHELP_OUT = 2;
    
    /**
     * 银行卡默认选中
     */
    public static final Integer CARD_SELECTED = 0;
    
    /**
     * 银行卡默认不选中
     */
    public static final Integer CARD_NOSELECTED = 1;
    
    /**
     * 微信捐步状态 101:捐步成功
     */
    public static final Integer WERUN_STATE_101 = 101;
    
}
