package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.framework.utils.TableUtil;
import com.chunhe.custom.framework.utils.TransformStringListUtil;
import com.chunhe.custom.pc.mapper.SeriesMapper;
import com.chunhe.custom.pc.model.Series;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * Created by white 2018-5-18 11:55:55
 */
@Service
public class SeriesService extends BaseService<Series> {

    @Autowired
    private SeriesMapper seriesMapper;

    @Autowired
    private StyleService styleService;

    //全部列表
    public List<Series> findSeriesList(Series series) {
        List<Series> seriesList = seriesMapper.findSeriesList(series);
        return seriesList;
    }

    /**
     * 查询数据
     */
    public List<Series> seriesList(DataTablesRequest dataTablesRequest) {
        Example example = new Example(Series.class);
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
    public Series getSeries(Long id) {
        Series series = new Series();
        series.setId(id);
        Series se = seriesMapper.getSeries(series);
        //详情轮播图转换为list
        List list = TransformStringListUtil.StringToList(se.getIntroductionImgs());
        se.setImgsUrlList(list);
        return se;
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> seriesMap) {
        Series series = new Series();
        String name = ConvertUtil.convert(seriesMap.get("name"), String.class);
        String remark = ConvertUtil.convert(seriesMap.get("remark"), String.class);
        String imgUrl = ConvertUtil.convert(seriesMap.get("seriesImg"), String.class);
        Boolean enable = ConvertUtil.convert(seriesMap.get("isEnabled"), Boolean.class);
        Integer level = ConvertUtil.convert(seriesMap.get("level"), Integer.class);
        String introductionWord = ConvertUtil.convert(seriesMap.get("introductionWord"), String.class);
        String introductionVideo = ConvertUtil.convert(seriesMap.get("introductionVideo"), String.class);
        List imgsUrlList = ConvertUtil.convert(seriesMap.get("imgsUrl"), List.class);
        if (imgUrl == null || imgUrl.equals("")) {
            return ServiceResponse.error("请上传图片");
        }
        String imgsUrl = "";
        if (imgsUrlList.size() > 0) {
            imgsUrl = TransformStringListUtil.ListToString(imgsUrlList);
        }
        series.setIntroductionWord(introductionWord);
        series.setIntroductionVideo(introductionVideo);
        series.setIntroductionImgs(imgsUrl);
        series.setName(name);
        series.setRemark(remark);
        series.setImgUrl(imgUrl);
        series.setEnabled(enable);
        series.setLevel(level);

        if (insertNotNull(series) != 1) {
            return ServiceResponse.error("添加失败");
        }
        return ServiceResponse.succ("添加成功");
    }

    /**
     * 是否更新成功
     */
    @Transactional
    public ServiceResponse update(Map<String, Object> seriesMap) {
        Long id = ConvertUtil.convert(seriesMap.get("id"), Long.class);
        Series series = selectByKey(id);
        String remark = ConvertUtil.convert(seriesMap.get("remark"), String.class);
        String imgUrl = ConvertUtil.convert(seriesMap.get("seriesImg"), String.class);
        Boolean enable = ConvertUtil.convert(seriesMap.get("isEnabled"), Boolean.class);
        Integer level = ConvertUtil.convert(seriesMap.get("level"), Integer.class);
        String introductionWord = ConvertUtil.convert(seriesMap.get("introductionWord"), String.class);
        String introductionVideo = ConvertUtil.convert(seriesMap.get("introductionVideo"), String.class);
        List imgsUrlList = ConvertUtil.convert(seriesMap.get("imgsUrl"), List.class);
        if (imgUrl == null || imgUrl.equals("")) {
            return ServiceResponse.error("请上传图片");
        }
        String imgsUrl = "";
        if (imgsUrlList.size() > 0) {
            imgsUrl = TransformStringListUtil.ListToString(imgsUrlList);
        }
        series.setIntroductionWord(introductionWord);
        series.setIntroductionVideo(introductionVideo);
        series.setIntroductionImgs(imgsUrl);
        series.setRemark(remark);
        series.setImgUrl(imgUrl);
        series.setEnabled(enable);
        series.setLevel(level);
        if (updateNotNull(series) != 1) {
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
        Series series = selectByKey(id);
        if (expireNotNull(series) != 1) {
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
        Series series = selectByKey(id);
        series.setEnabled(isEnabled);
        styleService.openOnOff(series.getName(),isEnabled);
        return updateNotNull(series) == 1;
    }
}
