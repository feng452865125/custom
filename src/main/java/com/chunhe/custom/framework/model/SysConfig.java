package com.chunhe.custom.framework.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 配置维护
 */
@Getter
@Setter
@Entity
@Table(name = "sys_config")
public class SysConfig extends BaseEntity {
    /**
     * 卡片任务对接POS
     */
    public static final String SYS_CONFIG_CARD_POS = "cardPos";
    /**
     * 下单Pos地址
     */
    public static final String SYS_CONFIG_ORDER_POS = "orderPos";
    /**
     * 附近店铺范围
     */
    public static final String SYS_CONFIG_NEARBY_DISTANCE = "nearbyDistance";
    /**
     * 初始密码
     */
    public static final String SYS_CONFIG_DEFAULT_PASSWORD = "defaultPassword";
    /**
     * 邮箱信息
     */
    public static final String SYS_CONFIG_THIRD_SENDER_ADDRESS = "senderAddress";
    public static final String SYS_CONFIG_THIRD_SENDER_ACCOUNT = "senderAccount";
    public static final String SYS_CONFIG_THIRD_SENDER_PASSWORD = "senderPassword";
    public static final String SYS_CONFIG_THIRD_SENDER_SMTP = "mailSmtp";
    public static final String SYS_CONFIG_THIRD_RECIPIENT_ADDRESS = "recipientAddress";
    public static final String SYS_CONFIG_THIRD_CHINASTAR_MILE_TITLE = "mailTitle";
    public static final String SYS_CONFIG_THIRD_ORDER_TITLE = "orderTitle";
    public static final String SYS_CONFIG_THIRD_CANCEL_TITLE = "cancelTitle";
    /**
     * chinastar接口地址
     * chinastar秘钥
     * chinastar发件人邮箱，账号，密码，邮箱服务器smtp，收件人邮箱，邮件标题
     */
    public static final String SYS_CONFIG_THIRD_CHINASTAR_URL = "thirdChinastarUrl";
    public static final String SYS_CONFIG_THIRD_CHINASTAR_SECRETKEY = "thirdChinastarSecretKey";
    /**
     * JP接口地址
     * JP登录账号
     * JP登录密码
     */
    public static final String SYS_CONFIG_THIRD_JP_URL = "thirdJPUrl";
    public static final String SYS_CONFIG_THIRD_JP_LOGINNAME = "thirdJPLoginName";
    public static final String SYS_CONFIG_THIRD_JP_PASSWORD = "thirdJPLoginPassword";


    /**
     * HB请求地址
     * HB登陆账号
     * HB登陆密码
     */
    public static final String SYS_CONFIG_THIRD_HB_LOCK_URL = "thirdHBLockUrl";
    public static final String SYS_CONFIG_THIRD_HB_VIEW_URL = "thirdHBLockUrl";
    public static final String SYS_CONFIG_THIRD_HB_LOGINNAME = "thirdHBLoginName";
    public static final String SYS_CONFIG_THIRD_HB_PASSWORD = "thirdHBLoginPassword";

    /**
     * PG接口地址
     */
    public static final String SYS_CONFIG_THIRD_PG_URL = "thirdPGUrl";
    /**
     * Diamart接口地址
     */
    public static final String SYS_CONFIG_THIRD_DIAMART_URL = "thirdDiamartUrl";

    /**
     * POP
     */
    public static final String SYS_CONFIG_THIRD_OPO_NAME = "opoName";
    public static final String SYS_CONFIG_THIRD_OPO_PASSWORD = "opoPassword";
    public static final String SYS_CONFIG_THIRD_OPO_URL = "opoUrl";

    /**
     * KBS接口地址
     * KBS登录账号
     * KBS登录密码
     */
    public static final String SYS_CONFIG_THIRD_KBS_URL = "thirdKBSUrl";
    public static final String SYS_CONFIG_THIRD_KBS_LOGINNAME = "thirdKBSLoginName";
    public static final String SYS_CONFIG_THIRD_KBS_PASSWORD = "thirdKBSLoginPassword";

    /**
     * HAO接口地址
     * HAO登录账号
     * HAO登录密码
     */
    public static final String SYS_CONFIG_THIRD_HAO_URL = "thirdHAOUrl";
    public static final String SYS_CONFIG_THIRD_HAO_LOGINNAME = "thirdHAOLoginName";
    public static final String SYS_CONFIG_THIRD_HAO_PASSWORD = "thirdHAOLoginPassword";

    /**
     * YBL接口地址
     * YBL登录账号
     * YBL登录密码
     */
    public static final String SYS_CONFIG_THIRD_YBL_URL = "thirdYBLUrl";
    public static final String SYS_CONFIG_THIRD_YBL_LOGINNAME = "thirdYBLLoginName";
    public static final String SYS_CONFIG_THIRD_YBL_PASSWORD = "thirdYBLLoginPassword";

    /**
     * HB2接口地址
     * HB2登录账号
     * HB2登录密码
     */
    public static final String SYS_CONFIG_THIRD_HB2_STONE_URL = "thirdHB2StoneUrl";
    public static final String SYS_CONFIG_THIRD_HB2_ORDER_URL = "thirdHB2OrderUrl";
    public static final String SYS_CONFIG_THIRD_HB2_LOGINNAME = "thirdHB2LoginName";
    public static final String SYS_CONFIG_THIRD_HB2_PASSWORD = "thirdHB2LoginPassword";
    public static final String SYS_CONFIG_THIRD_HB2_APIKEY = "thirdHB2LoginApikey";

    /**
     * EX3接口地址
     * EX3用户标识（供应商分配）
     */
    public static final String SYS_CONFIG_THIRD_EX3_URL = "thirdEX3Url";
    public static final String SYS_CONFIG_THIRD_EX3_OPENID = "thirdEX3Openid";

    /**
     * KEERSAP接口地址1:查询
     * KEERSAP接口地址2:下单
     * KEERSAP登录账号
     * KEERSAP登录密码
     */
    public static final String SYS_CONFIG_THIRD_KEERSAP_URL_SELECT = "thirdKEERSAPUrlSelect";
    public static final String SYS_CONFIG_THIRD_KEERSAP_URL_ORDER = "thirdKEERSAPUrlOrder";
    public static final String SYS_CONFIG_THIRD_KEERSAP_LOGINNAME = "thirdKEERSAPLoginName";
    public static final String SYS_CONFIG_THIRD_KEERSAP_PASSWORD = "thirdKEERSAPLoginPassword";

    /**
     * HST接口地址
     * HST登录账号
     * HST登录密码
     */
    public static final String SYS_CONFIG_THIRD_HST_URL = "thirdHSTUrl";
    public static final String SYS_CONFIG_THIRD_HST_LOGINNAME = "thirdHSTLoginName";
    public static final String SYS_CONFIG_THIRD_HST_PASSWORD = "thirdHSTLoginPassword";

    /**
     * YZ接口地址
     * YZ登录账号
     * YZ登录密码
     */
    public static final String SYS_CONFIG_THIRD_YZ_URL = "thirdYZUrl";
    public static final String SYS_CONFIG_THIRD_YZ_LOGINNAME = "thirdYZLoginName";
    public static final String SYS_CONFIG_THIRD_YZ_PASSWORD = "thirdYZLoginPassword";

    /**
     * JP2接口地址
     * JP2登录账号
     * JP2登录密码
     */
    public static final String SYS_CONFIG_THIRD_JP2_URL = "thirdJP2Url";
    public static final String SYS_CONFIG_THIRD_JP2_LOGINNAME = "thirdJP2LoginName";
    public static final String SYS_CONFIG_THIRD_JP2_PASSWORD = "thirdJP2LoginPassword";

    /**
     * KDL接口地址
     */
    public static final String SYS_CONFIG_THIRD_KDL_URL = "thirdKDLUrl";

    /**
     * 是否开启该地址的钻石
     */
    public static final String LOCATION_INDIA = "0";


    /**
     * token有效时间
     */

    public static final String TOKEN_TIME = "tokenTime";

    /**
     * 超时登录
     */

    public static final String IS_TOKEN_LOGIN = "isTokenLogin";

    /**
     * 是否同时登录
     */

    public static final String IS_LOGIN_SAME_TIME = "isLoginSameTime";

    /**
     * 是否允许sap同步
     */

    public static final String IS_ALLOW_SAP = "isAllowSap";

    /**
     * 锁定后多少间隔自动下单
     */
    public static final String SYS_CONFIG_THIRD_LOCKTOORDER = "thirdLockToOrder";
    /**
     * 美元转人民币系数
     */
    public static final String SYS_CONFIG_THIRD_SALEDOLLAR = "thirdSaleDollar";
    /**
     * 加价率（千叶销售系数）
     */
    public static final String SYS_CONFIG_THIRD_SALEKEER = "thirdSaleKeer";

    /**
     * 设备绑定认证开关(uuid)
     */
    public static final String SYS_CONFIG_UUID_SWITCH = "uuidSwitch";
    /**
     * PG接口地址
     */
    public static final String SYS_CONFIG_THIRD_UPDATE_SWITCH = "thirdUpdateSwitch";

    /**
     * 4C标准基价系数（默认1）
     */
    public static final String SYS_CONFIG_BASE_PRICE_MULTIPLE = "basePriceMultiple";
    public static final String SYS_CONFIG_BASE_PRICE_MULTIPLE1 = "basePriceMultiple1";
    public static final String SYS_CONFIG_BASE_PRICE_MULTIPLE2 = "basePriceMultiple2";

    /**
     * 门店销售系数（默认1）
     */
    public static final String SYS_CONFIG_STORE_PRICE_MULTIPLE = "storePriceMultiple";
    /**
     * 门店结算价，默认系数
     */
    public static final String SYS_CONFIG_STORE_ACCOUNT_PRICE_RATE = "storeAccountPriceRate";

    /**
     * 调用第三方供应商接口时用到
     */
    public static final String SYS_CONFIG_THIRD_LOGIN = "login";
    public static final String SYS_CONFIG_THIRD_LOGIN_USERNAME = "username";
    public static final String SYS_CONFIG_THIRD_LOGIN_PASSWORD = "password";
    public static final String SYS_CONFIG_THIRD_LOCK = "lock";
    public static final String SYS_CONFIG_THIRD_UNLOCK = "unlock";
    public static final String SYS_CONFIG_THIRD_ORDER = "order";
    public static final String SYS_CONFIG_THIRD_THIRD = "third";
    public static final String SYS_CONFIG_THIRD_NULL = "";

    /**
     * 状态-展示
     */
    public static final Boolean ENABLED_TRUE = Boolean.TRUE;

    /**
     * * 状态-不展示
     */
    public static final Boolean ENABLED_FALSE = Boolean.FALSE;

    //库存地，香港吗，深圳的回货时间
    public static final String SYS_CONFIG_LOCATION_IN_HK = "locationInHk";
    public static final String SYS_CONFIG_LOCATION_IN_SZ = "locationInSz";

    /**
     * 前端显示用
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 查询用
     */
    @Column(name = "`key`")
    private String key;

    /**
     * 配置的值
     */
    @Column(name = "`value`")
    private String value;

    /**
     * 相关备注
     */
    @Column(name = "`remark`")
    private String remark;


}