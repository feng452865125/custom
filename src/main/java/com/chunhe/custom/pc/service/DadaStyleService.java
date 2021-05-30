package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.framework.utils.TransformStringListUtil;
import com.chunhe.custom.pc.mapper.DadaPartsMapper;
import com.chunhe.custom.pc.mapper.DadaStyleMapper;
import com.chunhe.custom.pc.model.DadaParts;
import com.chunhe.custom.pc.model.DadaRelStyleParts;
import com.chunhe.custom.pc.model.DadaStyle;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by white 2018-9-5 14:49:47
 * dada系列的样式管理
 */
@Service
public class DadaStyleService extends BaseService<DadaStyle> {

    @Autowired
    private DadaStyleMapper dadaStyleMapper;

    @Autowired
    private DadaPartsMapper dadaPartsMapper;

    @Autowired
    private DadaPartsService dadaPartsService;

    @Autowired
    private DadaRelStylePartsService dadaRelStylePartsService;


    //全部列表
    public List<DadaStyle> findDadaStyleList(DadaStyle dadaStyle) {
        List<DadaStyle> dadaStyleList = dadaStyleMapper.findDadaStyleList(dadaStyle);
        return dadaStyleList;
    }

    //全部列表(佩戴类别，group by type)
    public List<DadaStyle> findDadaStyleTypeList(DadaStyle dadaStyle) {
        List<DadaStyle> dadaStyleTypeList = dadaStyleMapper.findDadaStyleTypeList(dadaStyle);
        return dadaStyleTypeList;
    }

    /**
     * 更多组合（2个以上sku）
     *
     * @param dadaStyle
     * @return
     */
    public List<DadaStyle> findDadaStyleMoreList(DadaStyle dadaStyle) {
        List<DadaStyle> dadaStyleList = new ArrayList<>();
        //先查这款样式的组件列表
        DadaRelStyleParts dadaRelStyleParts = new DadaRelStyleParts();
        dadaRelStyleParts.setStyleId(dadaStyle.getId());
        List<DadaParts> dadaPartsList = dadaPartsMapper.findDadaRelStyleParts(dadaRelStyleParts);
        //需求：两个以上（>=2）SKU的组合效果（只包括吊坠，不包括项链）,即组件至少3个，稍微做个拦截
        if (dadaPartsList != null && dadaPartsList.size() > 2) {
            String search = "";
            for (int i = 0; i < dadaPartsList.size(); i++) {
                DadaParts dadaParts = dadaPartsList.get(i);
                if (!dadaParts.getTypeName().equals("项链")) {
                    search = search + dadaParts.getId() + ",";
                }
            }
            search = "(" + search.substring(0, search.length() - 1) + ")";
            dadaStyle.setSearch(search);
            dadaStyle.setSearchLength(2);
            //等需求反馈，该方法（>=）
            dadaStyleList = dadaStyleMapper.findDadaStyleByPartsList(dadaStyle);
            if (dadaStyleList != null) {
                for (int i = 0; i < dadaStyleList.size(); i++) {
                    if (dadaStyleList.get(i).getId() == dadaStyle.getId()) {
                        //与当前的详情dadaStyle同一个，remove
                        dadaStyleList.remove(i--);
                    }
                }
            }
        }
        return dadaStyleList;
    }

    //定制里，选择组件，查询产品
    public List<DadaStyle> findDadaStyleByPartsList(DadaStyle dadaStyle) {
        int searchLength = 0;
        if (dadaStyle.getSearch() != null) {
            List<String> list = Arrays.asList(dadaStyle.getSearch().split(","));
            searchLength = list.size();
        }
        dadaStyle.setSearchLength(searchLength);
        List<DadaStyle> dadaStyleList = dadaStyleMapper.findDadaStyleByPartsList(dadaStyle);
        return dadaStyleList;
    }

    /**
     * 查询数据
     */
    public List<DadaStyle> dadaStyleList(DataTablesRequest dataTablesRequest) {
        DadaStyle dadaStyle = new DadaStyle();
        //排序
        String orderBy = dataTablesRequest.orders();
        if (StringUtils.isNotBlank(orderBy)) {
            dadaStyle.setOrderBy(orderBy);
        }
        //code
        DataTablesRequest.Column code = dataTablesRequest.getColumn("code");
        if (StringUtils.isNotBlank(code.getSearch().getValue())) {
            dadaStyle.setCode(code.getSearch().getValue());
        }
        //名称
        DataTablesRequest.Column name = dataTablesRequest.getColumn("name");
        if (StringUtils.isNotBlank(name.getSearch().getValue())) {
            dadaStyle.setName(name.getSearch().getValue());
        }
        //系列
        DataTablesRequest.Column series = dataTablesRequest.getColumn("series");
        if (series != null && StringUtils.isNotBlank(series.getSearch().getValue())) {
            dadaStyle.setSeries(series.getSearch().getValue());
        }
        //佩戴类别
        DataTablesRequest.Column type = dataTablesRequest.getColumn("type");
        if (type != null && StringUtils.isNotBlank(type.getSearch().getValue())) {
            dadaStyle.setType(new Integer(type.getSearch().getValue()));
        }
        //寓意
        DataTablesRequest.Column moral = dataTablesRequest.getColumn("moral");
        if (moral != null && StringUtils.isNotBlank(moral.getSearch().getValue())) {
            dadaStyle.setMoral(moral.getSearch().getValue());
        }
        //多表关联，不用example
        List<DadaStyle> dadaStyleList = this.findDadaStyleList(dadaStyle);
        return dadaStyleList;
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Transactional
    public DadaStyle getDadaStyle(Long id) {
        DadaStyle dadaStyle = new DadaStyle();
        dadaStyle.setId(id);
        DadaStyle ds = dadaStyleMapper.getDadaStyle(dadaStyle);
        if (ds == null) {
            throw new RFException("款式不存在");
        }
        //轮播图
        List list = TransformStringListUtil.StringToList(ds.getImgsUrl());
        ds.setImgsUrlList(list);
        return ds;
    }


    /**
     * app调用，详细数据
     *
     * @param dadaStyle
     * @return
     */
    @Transactional
    public DadaStyle getDadaStyleDetail(DadaStyle dadaStyle) {
        DadaStyle ds = dadaStyleMapper.getDadaStyle(dadaStyle);
        if (ds == null) {
            throw new RFException("款式不存在");
        }
        //轮播图
        List list = TransformStringListUtil.StringToList(ds.getImgsUrl());
        ds.setImgsUrlList(list);
        //搭配的组件(APP用到组件信息，平台有单独查询语句)
        DadaRelStyleParts dadaRelStyleParts = new DadaRelStyleParts();
        dadaRelStyleParts.setStyleId(ds.getId());
        List<DadaParts> dadaPartsList = dadaPartsMapper.findDadaRelStyleParts(dadaRelStyleParts);
        ds.setDadaPartsList(dadaPartsList);
        return ds;
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> dadaStyleMap) {
        DadaStyle dadaStyle = new DadaStyle();
        String code = ConvertUtil.convert(dadaStyleMap.get("code"), String.class);
        String name = ConvertUtil.convert(dadaStyleMap.get("name"), String.class);
        String series = ConvertUtil.convert(dadaStyleMap.get("series"), String.class);
        Integer type = ConvertUtil.convert(dadaStyleMap.get("type"), Integer.class);
        String imgUrl = ConvertUtil.convert(dadaStyleMap.get("imgUrl"), String.class);
        String remark = ConvertUtil.convert(dadaStyleMap.get("remark"), String.class);
        dadaStyle.setCode(code);
        dadaStyle.setName(name);
        dadaStyle.setSeries(series);
        dadaStyle.setType(type);
        dadaStyle.setImgUrl(imgUrl);
        dadaStyle.setRemark(remark);
        dadaStyle.setCount(DadaStyle.countInit);
        dadaStyle.setPrice(DadaStyle.priceInit);
        if (insertNotNull(dadaStyle) != 1) {
            return ServiceResponse.error("添加失败");
        }
        return ServiceResponse.succ("添加成功");
    }

    /**
     * 是否更新成功
     */
    @Transactional
    public ServiceResponse update(Map<String, Object> dadaStyleMap) {
        Long id = ConvertUtil.convert(dadaStyleMap.get("id"), Long.class);
        DadaStyle dadaStyle = selectByKey(id);
        String code = ConvertUtil.convert(dadaStyleMap.get("code"), String.class);
        String name = ConvertUtil.convert(dadaStyleMap.get("name"), String.class);
        String series = ConvertUtil.convert(dadaStyleMap.get("series"), String.class);
        Integer type = ConvertUtil.convert(dadaStyleMap.get("type"), Integer.class);
        String imgUrl = ConvertUtil.convert(dadaStyleMap.get("imgUrl"), String.class);
        String remark = ConvertUtil.convert(dadaStyleMap.get("remark"), String.class);
        dadaStyle.setCode(code);
        dadaStyle.setName(name);
        dadaStyle.setSeries(series);
        dadaStyle.setType(type);
        dadaStyle.setImgUrl(imgUrl);
        dadaStyle.setRemark(remark);
        if (updateNotNull(dadaStyle) != 1) {
            return ServiceResponse.error("更新失败");
        }
        return ServiceResponse.succ("更新成功");
    }

    /**
     * 详情维护，轮播图，视频，寓意，描述相关，绑定组件
     */
    @Transactional
    public ServiceResponse detailUpdate(Map<String, Object> dadaStyleMap) {
        Long id = ConvertUtil.convert(dadaStyleMap.get("id"), Long.class);
        DadaStyle dadaStyle = selectByKey(id);
        List imgsUrlList = ConvertUtil.convert(dadaStyleMap.get("imgsUrl"), List.class);
//        String videoUrl = ConvertUtil.convert(dadaStyleMap.get("videoUrl"), String.class);
        String wearUrl = ConvertUtil.convert(dadaStyleMap.get("wearUrl"), String.class);
        String moral = ConvertUtil.convert(dadaStyleMap.get("moral"), String.class);
        String remark = ConvertUtil.convert(dadaStyleMap.get("remark"), String.class);
        String imgsUrl = "";
        if (imgsUrlList.size() > 0) {
            imgsUrl = TransformStringListUtil.ListToString(imgsUrlList);
        }
        dadaStyle.setImgsUrl(imgsUrl);
//        dadaStyle.setVideoUrl(videoUrl);
        dadaStyle.setWearUrl(wearUrl);
        dadaStyle.setMoral(moral);
        dadaStyle.setRemark(remark != null ? remark : "");
        if (updateNotNull(dadaStyle) != 1) {
            return ServiceResponse.error("更新失败");
        }
        return ServiceResponse.succ("更新成功");
    }

    /**
     * 增加绑定，insert
     *
     * @param relMap
     */
    public void createDadaRelStyleParts(Map<String, Object> relMap) {
        //关联表绑定
        DadaRelStyleParts dadaRelStyleParts = dadaRelStylePartsService.getDadaRelStyleParts(relMap);
        if (dadaRelStyleParts == null) {
            Long styleId = ConvertUtil.convert(relMap.get("styleId"), Long.class);
            Long partsId = ConvertUtil.convert(relMap.get("partsId"), Long.class);
            DadaRelStyleParts rel = new DadaRelStyleParts();
            rel.setStyleId(styleId);
            rel.setPartsId(partsId);
            dadaRelStylePartsService.insertNotNull(rel);
            //自身dadaStyle统计（count+1, price+1）
            DadaParts dadaParts = dadaPartsService.getDadaParts(partsId);
            DadaStyle dadaStyle = getDadaStyle(styleId);
            dadaStyle.setCount(dadaStyle.getCount() + 1);
            dadaStyle.setPrice(dadaStyle.getPrice() + dadaParts.getPrice());
            updateNotNull(dadaStyle);
        }
    }

    /**
     * 解除绑定，delete
     *
     * @param relMap
     */
    public void deleteDadaRelStyleParts(Map<String, Object> relMap) {
        //关联表解除绑定
        DadaRelStyleParts dadaRelStyleParts = dadaRelStylePartsService.getDadaRelStyleParts(relMap);
        if (dadaRelStyleParts != null) {
            dadaRelStylePartsService.deleteByKey(dadaRelStyleParts.getId());
            //自身dadaStyle统计编号（count-1, price-1）
            Long styleId = dadaRelStyleParts.getStyleId();
            Long partsId = dadaRelStyleParts.getPartsId();
            DadaParts dadaParts = dadaPartsService.getDadaParts(partsId);
            DadaStyle dadaStyle = getDadaStyle(styleId);
            dadaStyle.setCount(dadaStyle.getCount() - 1);
            dadaStyle.setPrice(dadaStyle.getPrice() - dadaParts.getPrice());
            updateNotNull(dadaStyle);
        }
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Transactional
    public ServiceResponse deleteById(Long id) {
        DadaStyle dadaStyle = selectByKey(id);
        if (expireNotNull(dadaStyle) != 1) {
            return ServiceResponse.error("删除失败");
        }
        return ServiceResponse.succ("删除成功");
    }

    /**
     * 检查是否存在（dadaStyle中的code唯一）
     *
     * @param property
     * @param value
     * @return
     */
    public Boolean isExistByParam(String property, String value) {
        Example example = new Example(DadaStyle.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(property, value)
                .andIsNull("expireDate");
        return getMapper().selectCountByExample(example) > 0;
    }

    /**
     * 设置参数（类型的名字）
     *
     * @param dadaStyle
     */
    public void setParam(DadaStyle dadaStyle) {

    }

}
