package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.framework.utils.TableUtil;
import com.chunhe.custom.pc.mapper.JewelryTypeMapper;
import com.chunhe.custom.pc.model.JewelryType;
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
public class JewelryTypeService extends BaseService<JewelryType> {

    @Autowired
    private JewelryTypeMapper jewelryTypeMapper;

    //全部列表
    public List<JewelryType> findJewelryTypeList(JewelryType jewelryType) {
        List<JewelryType> jewelryTypeList = jewelryTypeMapper.findJewelryTypeList(jewelryType);
        return jewelryTypeList;
    }

    /**
     * 查询数据
     */
    public List<JewelryType> jewelryTypeList(DataTablesRequest dataTablesRequest) {
        Example example = new Example(JewelryType.class);
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
    public JewelryType getJewelryType(Long id) {
        JewelryType jewelryType = new JewelryType();
        jewelryType.setId(id);
        JewelryType jt = jewelryTypeMapper.getJewelryType(jewelryType);
        return jt;
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> jewelryTypeMap) {
        JewelryType jewelryType = new JewelryType();
        String code = ConvertUtil.convert(jewelryTypeMap.get("code"), String.class);
        String name = ConvertUtil.convert(jewelryTypeMap.get("name"), String.class);
        String remark = ConvertUtil.convert(jewelryTypeMap.get("remark"), String.class);

        jewelryType.setCode(code);
        jewelryType.setName(name);
        jewelryType.setRemark(remark);

        if (insertNotNull(jewelryType) != 1) {
            return ServiceResponse.error("添加失败");
        }
        return ServiceResponse.succ("添加成功");
    }

    /**
     * 是否更新成功
     */
    @Transactional
    public ServiceResponse update(Map<String, Object> jewelryTypeMap) {
        Long id = ConvertUtil.convert(jewelryTypeMap.get("id"), Long.class);
        JewelryType jewelryType = selectByKey(id);
        String code = ConvertUtil.convert(jewelryTypeMap.get("code"), String.class);
        String remark = ConvertUtil.convert(jewelryTypeMap.get("remark"), String.class);

        jewelryType.setCode(code);
        jewelryType.setRemark(remark);
        if (updateNotNull(jewelryType) != 1) {
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
        JewelryType jewelryType = selectByKey(id);
        if (expireNotNull(jewelryType) != 1) {
            return ServiceResponse.error("删除失败");
        }
        return ServiceResponse.succ("删除成功");
    }


}
