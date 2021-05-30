package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.framework.utils.TableUtil;
import com.chunhe.custom.pc.mapper.DadaJewelryTypeMapper;
import com.chunhe.custom.pc.model.DadaJewelryType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * Created by white 2018-9-5 14:49:47
 * dada系列的佩戴类别管理
 */
@Service
public class DadaJewelryTypeService extends BaseService<DadaJewelryType> {

    @Autowired
    private DadaJewelryTypeMapper dadaJewelryTypeMapper;

    //全部列表
    public List<DadaJewelryType> findDadaJewelryTypeList(DadaJewelryType dadaJewelryType) {
        List<DadaJewelryType> dadaJewelryTypeList = dadaJewelryTypeMapper.findDadaJewelryTypeList(dadaJewelryType);
        return dadaJewelryTypeList;
    }

    /**
     * 查询数据
     */
    public List<DadaJewelryType> dadaJewelryTypeList(DataTablesRequest dataTablesRequest) {
        Example example = new Example(DadaJewelryType.class);
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
    public DadaJewelryType getDadaJewelryType(Long id) {
        DadaJewelryType dadaJewelryType = new DadaJewelryType();
        dadaJewelryType.setId(id);
        DadaJewelryType djt = dadaJewelryTypeMapper.getDadaJewelryType(dadaJewelryType);
        return djt;
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> dadaJewelryTypeMap) {
        DadaJewelryType dadaJewelryType = new DadaJewelryType();
        String code = ConvertUtil.convert(dadaJewelryTypeMap.get("code"), String.class);
        String name = ConvertUtil.convert(dadaJewelryTypeMap.get("name"), String.class);
        String remark = ConvertUtil.convert(dadaJewelryTypeMap.get("remark"), String.class);

        dadaJewelryType.setCode(code);
        dadaJewelryType.setName(name);
        dadaJewelryType.setRemark(remark);

        if (insertNotNull(dadaJewelryType) != 1) {
            return ServiceResponse.error("添加失败");
        }
        return ServiceResponse.succ("添加成功");
    }

    /**
     * 是否更新成功
     */
    @Transactional
    public ServiceResponse update(Map<String, Object> dadaJewelryTypeMap) {
        Long id = ConvertUtil.convert(dadaJewelryTypeMap.get("id"), Long.class);
        DadaJewelryType dadaJewelryType = selectByKey(id);
        String code = ConvertUtil.convert(dadaJewelryTypeMap.get("code"), String.class);
        String remark = ConvertUtil.convert(dadaJewelryTypeMap.get("remark"), String.class);

        dadaJewelryType.setCode(code);
        dadaJewelryType.setRemark(remark);
        if (updateNotNull(dadaJewelryType) != 1) {
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
        DadaJewelryType dadaJewelryType = selectByKey(id);
        if (expireNotNull(dadaJewelryType) != 1) {
            return ServiceResponse.error("删除失败");
        }
        return ServiceResponse.succ("删除成功");
    }


}
