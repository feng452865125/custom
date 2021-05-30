package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.pc.mapper.DadaRelStylePartsMapper;
import com.chunhe.custom.pc.model.DadaParts;
import com.chunhe.custom.pc.model.DadaRelStyleParts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by white 2018-9-6 18:09:29
 */
@Service
public class DadaRelStylePartsService extends BaseService<DadaRelStyleParts> {

    @Autowired
    private DadaRelStylePartsMapper dadaRelStylePartsMapper;

    /**
     * 查找dada款式下的组件
     * @param dataTablesRequest
     * @return
     */
    public List<DadaParts> findDadaRelStyleParts(DataTablesRequest dataTablesRequest, Long id) {
        DadaParts dadaParts = new DadaParts();
        dadaParts.setStyleId(id);
        dadaParts.setPrice(0);
        //系列
        DataTablesRequest.Column series = dataTablesRequest.getColumn("series");
        if (StringUtils.isNotBlank(series.getSearch().getValue())) {
            dadaParts.setSeries(series.getSearch().getValue());
        }
        //佩戴列表
        DataTablesRequest.Column type = dataTablesRequest.getColumn("type");
        if (StringUtils.isNotBlank(type.getSearch().getValue())) {
            dadaParts.setType(new Integer(type.getSearch().getValue()));
        }
        //编码
        DataTablesRequest.Column code = dataTablesRequest.getColumn("code");
        if (StringUtils.isNotBlank(code.getSearch().getValue())) {
            dadaParts.setCode(code.getSearch().getValue());
        }
        //寓意
        DataTablesRequest.Column exYy = dataTablesRequest.getColumn("exYy");
        if (StringUtils.isNotBlank(exYy.getSearch().getValue())) {
            dadaParts.setExYy(exYy.getSearch().getValue());
        }
        List<DadaParts> dadaPartsList = dadaRelStylePartsMapper.findDadaRelStyleParts(dadaParts);
        return dadaPartsList;
    }

    /**
     * 查找表中是否存在关联
     * @param relMap
     * @return
     */
    public DadaRelStyleParts getDadaRelStyleParts(Map<String, Object> relMap){
        Long styleId = ConvertUtil.convert(relMap.get("styleId"), Long.class);
        Long partsId = ConvertUtil.convert(relMap.get("partsId"), Long.class);
        DadaRelStyleParts rel = new DadaRelStyleParts();
        rel.setStyleId(styleId);
        rel.setPartsId(partsId);
        DadaRelStyleParts dadaRelStyleParts = dadaRelStylePartsMapper.getDadaRelStyleParts(rel);
        return dadaRelStyleParts;
    }
}
