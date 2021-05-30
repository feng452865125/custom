package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.framework.utils.TableUtil;
import com.chunhe.custom.pc.mapper.DadaSeriesMapper;
import com.chunhe.custom.pc.model.DadaSeries;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * Created by white 2018-9-5 14:49:47
 * dada系列的系列管理
 */
@Service
public class DadaSeriesService extends BaseService<DadaSeries> {

    @Autowired
    private DadaSeriesMapper dadaSeriesMapper;

    //全部列表
    public List<DadaSeries> findDadaSeriesList(DadaSeries dadaSeries) {
        List<DadaSeries> dadaSeriesList = dadaSeriesMapper.findDadaSeriesList(dadaSeries);
        return dadaSeriesList;
    }

    /**
     * 查询数据
     */
    public List<DadaSeries> dadaSeriesList(DataTablesRequest dataTablesRequest) {
        Example example = new Example(DadaSeries.class);
        String orders = dataTablesRequest.orders();
        if (StringUtils.isNotBlank(orders)) {
            example.setOrderByClause(orders);
        }
        Example.Criteria criteria = example.createCriteria();
        //名称
        DataTablesRequest.Column name = dataTablesRequest.getColumn("name");
        if (StringUtils.isNotBlank(name.getSearch().getValue())) {
            criteria.andLike("name", TableUtil.toFuzzySql(name.getSearch().getValue()));
        }
        //图片描述
        DataTablesRequest.Column remark = dataTablesRequest.getColumn("remark");
        if (StringUtils.isNotBlank(remark.getSearch().getValue())) {
            criteria.andLike("remark", TableUtil.toFuzzySql(remark.getSearch().getValue()));
        }
        //其他
        criteria.andIsNull("expireDate");
        return getMapper().selectByExample(example);
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Transactional
    public DadaSeries getDadaSeries(Long id) {
        DadaSeries dadaSeries = new DadaSeries();
        dadaSeries.setId(id);
        DadaSeries ds = dadaSeriesMapper.getDadaSeries(dadaSeries);
        return ds;
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> dadaSeriesMap) {
        DadaSeries dadaSeries = new DadaSeries();
        String name = ConvertUtil.convert(dadaSeriesMap.get("name"), String.class);
        String remark = ConvertUtil.convert(dadaSeriesMap.get("remark"), String.class);
        String imgUrl = ConvertUtil.convert(dadaSeriesMap.get("seriesImg"), String.class);
        Boolean enable = ConvertUtil.convert(dadaSeriesMap.get("isEnabled"), Boolean.class);
        Integer level = ConvertUtil.convert(dadaSeriesMap.get("level"), Integer.class);
        dadaSeries.setName(name);
        dadaSeries.setRemark(remark);
        dadaSeries.setImgUrl(imgUrl);
        dadaSeries.setEnabled(enable);
        dadaSeries.setLevel(level);
        if (insertNotNull(dadaSeries) != 1) {
            return ServiceResponse.error("添加失败");
        }
        return ServiceResponse.succ("添加成功");
    }

    /**
     * 是否更新成功
     */
    @Transactional
    public ServiceResponse update(Map<String, Object> dadaSeriesMap) {
        Long id = ConvertUtil.convert(dadaSeriesMap.get("id"), Long.class);
        DadaSeries dadaSeries = selectByKey(id);
        String remark = ConvertUtil.convert(dadaSeriesMap.get("remark"), String.class);
        String imgUrl = ConvertUtil.convert(dadaSeriesMap.get("seriesImg"), String.class);
        Boolean enable = ConvertUtil.convert(dadaSeriesMap.get("isEnabled"), Boolean.class);
        Integer level = ConvertUtil.convert(dadaSeriesMap.get("level"), Integer.class);
        dadaSeries.setRemark(remark);
        dadaSeries.setImgUrl(imgUrl);
        dadaSeries.setEnabled(enable);
        dadaSeries.setLevel(level);
        if (updateNotNull(dadaSeries) != 1) {
            return ServiceResponse.error("更新失败");
        }
        return ServiceResponse.succ("更新成功");
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Transactional
    public ServiceResponse deleteById(Long id) {
        DadaSeries dadaSeries = selectByKey(id);
        if (expireNotNull(dadaSeries) != 1) {
            return ServiceResponse.error("删除失败");
        }
        return ServiceResponse.succ("删除成功");
    }


    /**
     * 可用/不可用
     *
     * @param isEnabled 是否可用
     */
    @Transactional(readOnly = false)
    public Boolean enabled(Long id, Boolean isEnabled) {
        DadaSeries dadaSeries = selectByKey(id);
        dadaSeries.setEnabled(isEnabled);
        return updateNotNull(dadaSeries) == 1;
    }
}
