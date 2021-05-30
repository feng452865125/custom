package com.chunhe.custom.framework.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.mybatis.BaseEntity;
import com.chunhe.custom.framework.mybatis.MyMapper;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.chunhe.custom.framework.utils.TableUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

public abstract class BaseService<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected MyMapper<T> mapper;

    protected MyMapper<T> getMapper() {
        return mapper;
    }

    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }

    public List<T> select(T entity) {
        return mapper.select(entity);
    }

    public T selectByKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    public int insertNotNull(T entity) {
        if (entity instanceof BaseEntity) {
            ((BaseEntity) entity).setCreateDate(new Date());
        }
        return mapper.insertSelective(entity);
    }

    public int insert(T entity) {
        if (entity instanceof BaseEntity) {
            ((BaseEntity) entity).setCreateDate(new Date());
        }
        return mapper.insert(entity);
    }

    public int updateAll(T entity){
        if (entity instanceof BaseEntity) {
            ((BaseEntity) entity).setUpdateDate(new Date());
        }
        return mapper.updateByPrimaryKey(entity);
    }

    public int updateNotNull(T entity){
        if (entity instanceof BaseEntity) {
            ((BaseEntity) entity).setUpdateDate(new Date());
        }
        return mapper.updateByPrimaryKeySelective(entity);
    }

    public int expireNotNull(T entity){
        if (entity instanceof BaseEntity) {
            ((BaseEntity) entity).setExpireDate(new Date());
        }
        return mapper.updateByPrimaryKeySelective(entity);
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

    /**
     * 搜索
     * ps:增加字段判空，如  if (type != null && ……）
     * @param dataTablesRequest
     * @param example
     * @return
     */
    public List<T> findDataTablesList(DataTablesRequest dataTablesRequest, Example example){
        String orders = dataTablesRequest.orders();
        if (StringUtils.isNotBlank(orders)) {
            example.setOrderByClause(orders);
        }
        Example.Criteria criteria = example.createCriteria();
        //string---andLike
        DataTablesRequest.Column name = dataTablesRequest.getColumn("name");
        if (name != null && StringUtils.isNotBlank(name.getSearch().getValue())) {
            criteria.andLike("name", TableUtil.toFuzzySql(name.getSearch().getValue()));
        }
        DataTablesRequest.Column username = dataTablesRequest.getColumn("username");
        if (username != null && StringUtils.isNotBlank(username.getSearch().getValue())) {
            criteria.andLike("username", TableUtil.toFuzzySql(username.getSearch().getValue()));
        }
        //int--andEqualTo
        DataTablesRequest.Column type = dataTablesRequest.getColumn("type");
        if (type != null && StringUtils.isNotBlank(type.getSearch().getValue())) {
            criteria.andEqualTo("type", type.getSearch().getValue());
        }
        DataTablesRequest.Column status = dataTablesRequest.getColumn("status");
        if (status != null && StringUtils.isNotBlank(status.getSearch().getValue())) {
            criteria.andEqualTo("status", status.getSearch().getValue());
        }
        criteria.andIsNull("expireDate");

        return getMapper().selectByExample(example);
    }




}
