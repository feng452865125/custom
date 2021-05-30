package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.pc.mapper.RelAdvertisementStyleMapper;
import com.chunhe.custom.pc.model.RelAdvertisementStyle;
import com.chunhe.custom.pc.model.Style;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by white 2018-7-21 10:36:36
 */
@Service
public class RelAdvertisementStyleService extends BaseService<RelAdvertisementStyle> {

    @Autowired
    private RelAdvertisementStyleMapper relAdvertisementStyleMapper;

    /**
     * 查找广告维护与样式的关联
     * @param dataTablesRequest
     * @return
     */
    public List<Style> findRelAdvertisementStyle( DataTablesRequest dataTablesRequest, Long id) {
        Style style = new Style();
        style.setAdvertisementId(id);
        //编码
        DataTablesRequest.Column relId = dataTablesRequest.getColumn("id");
        if (StringUtils.isNotBlank(relId.getSearch().getValue())) {
            style.setId(new Long(relId.getSearch().getValue()));
        }
        //名称
        DataTablesRequest.Column name = dataTablesRequest.getColumn("name");
        if (StringUtils.isNotBlank(name.getSearch().getValue())) {
            style.setName(name.getSearch().getValue());
        }
        //系列
        DataTablesRequest.Column series = dataTablesRequest.getColumn("series");
        if (StringUtils.isNotBlank(series.getSearch().getValue())) {
            style.setSeries(series.getSearch().getValue());
        }
        List<Style> relStyleList = relAdvertisementStyleMapper.findRelAdvertisementStyle(style);
        return relStyleList;
    }

    /**
     * 增加绑定，insert
     * @param relMap
     */
    public void createRelAdvertisementStyle(Map<String, Object> relMap) {
        RelAdvertisementStyle relAdvertisementStyle = this.getRelAdvertisementStyle(relMap);
        if(relAdvertisementStyle == null){
            Long advertisementId = ConvertUtil.convert(relMap.get("advertisementId"), Long.class);
            Long styleId = ConvertUtil.convert(relMap.get("styleId"), Long.class);
            RelAdvertisementStyle rel = new RelAdvertisementStyle();
            rel.setAdvertisementId(advertisementId);
            rel.setStyleId(styleId);
            insertNotNull(rel);
        }
    }

    /**
     * 解除绑定，delete
     * @param relMap
     * @return
     */
    public void deleteRelAdvertisementStyle(Map<String, Object> relMap) {
        RelAdvertisementStyle relAdvertisementStyle = this.getRelAdvertisementStyle(relMap);
        if(relAdvertisementStyle != null){
            deleteByKey(relAdvertisementStyle.getId());
        }

    }

    /**
     * 查找表中是否存在关联
     * @param relMap
     * @return
     */
    public RelAdvertisementStyle getRelAdvertisementStyle(Map<String, Object> relMap){
        Long advertisementId = ConvertUtil.convert(relMap.get("advertisementId"), Long.class);
        Long styleId = ConvertUtil.convert(relMap.get("styleId"), Long.class);
        RelAdvertisementStyle relAdvertisementStyle = new RelAdvertisementStyle();
        relAdvertisementStyle.setAdvertisementId(advertisementId);
        relAdvertisementStyle.setStyleId(styleId);
        RelAdvertisementStyle rel = relAdvertisementStyleMapper.getRelAdvertisementStyle(relAdvertisementStyle);
        return rel;
    }
}
