package com.chunhe.custom.entity;

import com.chunhe.custom.mybatis.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.Data;
import javax.persistence.Table;
import java.util.Date;

/**
 * <p>
 * 登录token
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-30
 */

@Table(name = "sys_token")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysToken extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id，admin表
     */
    private Integer sysTokenUserId;

    /**
     * 用户类型，0PC端，1移动端
     */
    private Integer sysTokenUserType;

    /**
     * token值
     */
    private String sysTokenValue;

    /**
     * 登录ip
     */
    private String sysTokenLoginIp;

    /**
     * 有效时间，判断登录过期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sysTokenLastDate;


}