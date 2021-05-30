package com.chunhe.custom.pc.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Table(name = "token")
public class Token {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private Integer id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * token字段
     */
    @Column(name = "token")
    private String token;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 上一次登录时间
     */
    @Column(name = "last_date")
    private Date lastDate;

    @Column(name = "expire_date")
    private Date expireDate;

    public Token() {

    }

    public Token(String token) {
        this.token = token;
    }

    public Token(Integer userId, String token) {
        this.userId = userId;
        this.token = token;
    }
}