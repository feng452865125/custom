package com.chunhe.custom.entity;

import com.chunhe.custom.mybatis.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.Data;
import javax.persistence.Table;

/**
 * <p>
 * 微信用户信息
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-30
 */

@Table(name = "sys_wx_user")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysWxUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 小程序的sessionkey
     */
    private String sysWxUserSessionkey;

    private String sysWxUserOpenid;

    private String sysWxUserUnionid;

    /**
     * 是否关注公众号
     */
    private Integer sysWxUserSubscribe;

    /**
     * 显示昵称
     */
    private String sysWxUserNc;

    /**
     * 头像地址
     */
    private String sysWxUserImgurl;

    /**
     * 性别
     */
    private Integer sysWxUserGender;

    /**
     * 手机号
     */
    private String sysWxUserPhone;

    /**
     * 国家
     */
    private String sysWxUserCountry;

    /**
     * 省
     */
    private String sysWxUserProvince;

    /**
     * 市
     */
    private String sysWxUserCity;

    /**
     * 备注
     */
    private String sysWxUserRemark;


}