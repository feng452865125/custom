package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.framework.utils.TableUtil;
import com.chunhe.custom.pc.mapper.AdditionalCostsMapper;
import com.chunhe.custom.pc.model.AdditionalCosts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * Created by white 2018年7月12日11:16:49
 */
@Service
public class AdditionalCostsService extends BaseService<AdditionalCosts> {

    @Autowired
    private AdditionalCostsMapper additionalCostsMapper;

    //全部列表
    public List<AdditionalCosts> findAdditionalCostsList(AdditionalCosts additionalCosts) {
        List<AdditionalCosts> additionalCostsList = additionalCostsMapper.findAdditionalCostsList(additionalCosts);
        return additionalCostsList;
    }

    /**
     * 查询数据
     */
    public List<AdditionalCosts> additionalCostsList(DataTablesRequest dataTablesRequest) {
        Example example = new Example(AdditionalCosts.class);
        String orders = dataTablesRequest.orders();
        if (StringUtils.isNotBlank(orders)) {
            example.setOrderByClause(orders);
        }
        Example.Criteria criteria = example.createCriteria();
        //编码
        DataTablesRequest.Column code = dataTablesRequest.getColumn("code");
        if (StringUtils.isNotBlank(code.getSearch().getValue())) {
            criteria.andLike("code", TableUtil.toFuzzySql(code.getSearch().getValue()));
        }
        //名称
        DataTablesRequest.Column name = dataTablesRequest.getColumn("name");
        if (StringUtils.isNotBlank(name.getSearch().getValue())) {
            criteria.andLike("name", TableUtil.toFuzzySql(name.getSearch().getValue()));
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
    public AdditionalCosts getAdditionalCosts(Long id) {
        AdditionalCosts additionalCosts = new AdditionalCosts();
        additionalCosts.setId(id);
        AdditionalCosts ac = additionalCostsMapper.getAdditionalCosts(additionalCosts);
        return ac;
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> additionalCostsMap) {
        AdditionalCosts additionalCosts = new AdditionalCosts();
        String code = ConvertUtil.convert(additionalCostsMap.get("code"), String.class);
        String name = ConvertUtil.convert(additionalCostsMap.get("name"), String.class);
        String remark = ConvertUtil.convert(additionalCostsMap.get("remark"), String.class);

        additionalCosts.setCode(code);
        additionalCosts.setName(name);
        additionalCosts.setRemark(remark);

        if (insertNotNull(additionalCosts) != 1) {
            return ServiceResponse.error("添加失败");
        }
        return ServiceResponse.succ("添加成功");
    }

    /**
     * 是否更新成功
     */
    @Transactional
    public ServiceResponse update(Map<String, Object> additionalCostsMap) {
        Long id = ConvertUtil.convert(additionalCostsMap.get("id"), Long.class);
        AdditionalCosts additionalCosts = selectByKey(id);
        String code = ConvertUtil.convert(additionalCostsMap.get("code"), String.class);
        String name = ConvertUtil.convert(additionalCostsMap.get("name"), String.class);
        String remark = ConvertUtil.convert(additionalCostsMap.get("remark"), String.class);

        additionalCosts.setCode(code);
        additionalCosts.setName(name);
        additionalCosts.setRemark(remark);
        if (updateNotNull(additionalCosts) != 1) {
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
        AdditionalCosts additionalCosts = selectByKey(id);
        if (expireNotNull(additionalCosts) != 1) {
            return ServiceResponse.error("删除失败");
        }
        return ServiceResponse.succ("删除成功");
    }


}
