package com.chunhe.custom.mybatis;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * PageList
 *
 * @Description: 重写pageHelper属性
 * @Author: qiu chunjing
 * @Date: 2020/11/17 14:15
 */
@Data
public class PageList<T> {

    private Long total = 0L;
    private int count = 1;
    private List<T> list;

    public PageList(List<T> list) {
        PageInfo pageInfo = new PageInfo<>(list);
        this.total = pageInfo.getTotal();
        this.count = pageInfo.getSize();
        this.list = list;
    }

}
