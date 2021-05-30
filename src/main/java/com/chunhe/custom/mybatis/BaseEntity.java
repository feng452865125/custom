package com.chunhe.custom.mybatis;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deleteDate;

    //筛选，时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Transient
    private Date startDate;

    //筛选，时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Transient
    private Date endDate;

    //筛选，分页
    @Transient
    private Integer firstnum;

    //筛选，分页
    @Transient
    private Integer num;

}
