package com.chunhe.custom.mybatis;

import com.chunhe.custom.datatables.DataTablesRequest;
import com.chunhe.custom.datatables.DataTablesResponse;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public abstract class BaseService<T> {

    protected Logger log = LogManager.getLogger(getClass());

    @Autowired
    protected BaseMapper<T> mapper;

    protected BaseMapper<T> getMapper() {
        return mapper;
    }

    public int insert(T entity) {
        if (entity instanceof BaseEntity) {
            ((BaseEntity) entity).setCreateDate(new Date());
        }
        return mapper.insert(entity);
    }

    public int update(T entity) {
        if (entity instanceof BaseEntity) {
            ((BaseEntity) entity).setUpdateDate(new Date());
        }
        return mapper.updateByPrimaryKey(entity);
    }

    public int delete(T entity) {
        if (entity instanceof BaseEntity) {
            ((BaseEntity) entity).setDeleteDate(new Date());
            return mapper.updateByPrimaryKey(entity);
        } else {
            return mapper.deleteByPrimaryKey(entity);
        }
    }


    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }

    public List<T> selectList(T entity) {
        return mapper.select(entity);
    }

    public T selectByKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    public int deleteByKey(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

    public int selectCountByExample(Object example) {
        return mapper.selectCountByExample(example);
    }

    public PageImpl<T> selectPage(Pageable pageable, ISelect select) {
        Page<T> page = PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize(), true).doSelectPage(select);
        return new PageImpl<>(page.getResult(), pageable, page.getTotal());
    }

    public DataTablesResponse<T> selectPage(DataTablesRequest dataTablesRequest, ISelect select) {
        DataTablesResponse<T> dataTablesResponse = new DataTablesResponse<>();
        try {
            Page<T> page = PageHelper.offsetPage(dataTablesRequest.getStart(), dataTablesRequest.getLength(), true).doSelectPage(select);
            dataTablesResponse.setData(page.getResult());
            dataTablesResponse.setDraw(dataTablesRequest.getDraw());
            dataTablesResponse.setRecordsFiltered(page.getTotal());
            dataTablesResponse.setRecordsTotal(page.getTotal());
            return dataTablesResponse;
        } catch (Throwable t) {
            t.printStackTrace();    // TODO 拦截日志打印记录到报错报表中
            dataTablesResponse.setDraw(dataTablesRequest.getDraw());
            dataTablesResponse.setError("分页失败，请稍后重试");
            return dataTablesResponse;
        }
    }

    public List<T> selectByKeys(long[] keys) {
        if (keys.length == 0) {
            return Lists.newArrayList();
        }
        if (keys.length <= 1000) {
            return ((BaseMapper<T>) getMapper()).selectByIds(StringUtils.join(keys, ','));
        }

        List<T> result = new ArrayList<>(keys.length);
        int count = keys.length / 1000 + (keys.length % 1000 == 0 ? 0 : 1);
        for (int i = 0; i < count; i++) {
            int start = i * 1000;
            int end = i == (count - 1) ? keys.length : 1000;
            long[] subarray = ArrayUtils.subarray(keys, start, end);
            result.addAll(selectByKeys(subarray));
        }
        return result;
    }

}
