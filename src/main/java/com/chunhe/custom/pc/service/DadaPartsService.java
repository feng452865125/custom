package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.pc.mapper.DadaPartsMapper;
import com.chunhe.custom.pc.model.DadaParts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by white 2018-9-5 14:49:47
 * dada系列的组件管理
 */
@Service
public class DadaPartsService extends BaseService<DadaParts> {

    @Autowired
    private DadaPartsMapper dadaPartsMapper;


    //全部列表
    public List<DadaParts> findDadaPartsList(DadaParts dadaParts) {
        List<DadaParts> dadaPartsList = dadaPartsMapper.findDadaPartsList(dadaParts);
        return dadaPartsList;
    }


    /**
     * 查询数据
     */
    public List<DadaParts> dadaPartsList(DataTablesRequest dataTablesRequest) {
        DadaParts dadaParts = new DadaParts();
        String orders = dataTablesRequest.orders();
        if (StringUtils.isNotBlank(orders)) {
            dadaParts.setOrderBy(orders);
        }
        //编码
        DataTablesRequest.Column code = dataTablesRequest.getColumn("code");
        if (code != null && StringUtils.isNotBlank(code.getSearch().getValue())) {
            dadaParts.setCode(code.getSearch().getValue());
        }
        //名字
        DataTablesRequest.Column name = dataTablesRequest.getColumn("name");
        if (name != null && StringUtils.isNotBlank(name.getSearch().getValue())) {
            dadaParts.setName(name.getSearch().getValue());
        }
        //系列
        DataTablesRequest.Column series = dataTablesRequest.getColumn("series");
        if (series != null && StringUtils.isNotBlank(series.getSearch().getValue())) {
            dadaParts.setSeries(series.getSearch().getValue());
        }
        //佩戴类别
        DataTablesRequest.Column type = dataTablesRequest.getColumn("type");
        if (type != null && StringUtils.isNotBlank(type.getSearch().getValue())) {
            dadaParts.setType(new Integer(type.getSearch().getValue()));
        }
        //寓意
        DataTablesRequest.Column exYy = dataTablesRequest.getColumn("exYy");
        if (exYy != null && StringUtils.isNotBlank(exYy.getSearch().getValue())) {
            dadaParts.setExYy(exYy.getSearch().getValue());
        }
        //其他
        List<DadaParts> dadaPartsList = this.findDadaPartsList(dadaParts);
        return dadaPartsList;
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Transactional
    public DadaParts getDadaParts(Long id) {
        DadaParts dadaParts = new DadaParts();
        dadaParts.setId(id);
        DadaParts par = dadaPartsMapper.getDadaParts(dadaParts);
        return par;
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> partsMap) {
        DadaParts dadaParts = new DadaParts();
        String code = ConvertUtil.convert(partsMap.get("code"), String.class);
        String name = ConvertUtil.convert(partsMap.get("name"), String.class);

//        if (insertNotNull(dadaParts) != 1) {
//            return ServiceResponse.error("添加失败");
//        }
        return ServiceResponse.succ("添加成功");
    }

    /**
     * 是否更新成功
     */
    @Transactional
    public ServiceResponse update(Map<String, Object> partsMap) {
        Long id = ConvertUtil.convert(partsMap.get("id"), Long.class);
        DadaParts dadaParts = selectByKey(id);
        String imgUrl = ConvertUtil.convert(partsMap.get("imgUrl"), String.class);
        String remark = ConvertUtil.convert(partsMap.get("remark"), String.class);
        dadaParts.setRemark(remark != null ? remark : "");

//        if (updateNotNull(dadaParts) != 1) {
//            return ServiceResponse.error("更新失败");
//        }
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
        DadaParts dadaParts = selectByKey(id);
        if (expireNotNull(dadaParts) != 1) {
            return ServiceResponse.error("删除失败");
        }
        return ServiceResponse.succ("删除成功");
    }


    /**
     * 设置参数
     *
     * @param dadaParts
     */
    public void setParam(DadaParts dadaParts) {

    }
}
