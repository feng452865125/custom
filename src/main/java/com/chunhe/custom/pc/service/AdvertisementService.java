package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.framework.utils.TableUtil;
import com.chunhe.custom.pc.mapper.AdvertisementMapper;
import com.chunhe.custom.pc.model.Advertisement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * Created by white 2018年7月10日14:17:47
 * 广告栏
 */
@Service
public class AdvertisementService extends BaseService<Advertisement> {

    @Autowired
    private AdvertisementMapper advertisementMapper;

    //全部列表
    public List<Advertisement> findAdvertisementList(Advertisement advertisement) {
        List<Advertisement> advertisementList = advertisementMapper.findAdvertisementList(advertisement);
        return advertisementList;
    }

    /**
     * 查询数据
     */
    public List<Advertisement> advertisementList(DataTablesRequest dataTablesRequest) {
        Example example = new Example(Advertisement.class);
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
        //其他
        criteria.andIsNull("expireDate");
        List<Advertisement> advertisementList = getMapper().selectByExample(example);
        return advertisementList;
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Transactional
    public Advertisement getAdvertisement(Long id) {
        Advertisement advertisement = new Advertisement();
        advertisement.setId(id);
        Advertisement ad = advertisementMapper.getAdvertisement(advertisement);
        return ad;
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> advertisementMap) {
        Advertisement advertisement = new Advertisement();
        String name = ConvertUtil.convert(advertisementMap.get("name"), String.class);
        String remark = ConvertUtil.convert(advertisementMap.get("remark"), String.class);
        String imgUrl = ConvertUtil.convert(advertisementMap.get("url"), String.class);
        Boolean enable = ConvertUtil.convert(advertisementMap.get("isEnabled"), Boolean.class);

        if (imgUrl == null || imgUrl.equals("")) {
            return ServiceResponse.error("请上传图片");
        }
        advertisement.setName(name);
        advertisement.setRemark(remark);
        advertisement.setImgUrl(imgUrl);
        advertisement.setEnabled(enable);

        if (insertNotNull(advertisement) != 1) {
            return ServiceResponse.error("添加失败");
        }
        return ServiceResponse.succ("添加成功");
    }

    /**
     * 是否更新成功
     */
    @Transactional
    public ServiceResponse update(Map<String, Object> advertisementMap) {
        Long id = ConvertUtil.convert(advertisementMap.get("id"), Long.class);
        Advertisement advertisement = selectByKey(id);
        String name = ConvertUtil.convert(advertisementMap.get("name"), String.class);
        String remark = ConvertUtil.convert(advertisementMap.get("remark"), String.class);
        String imgUrl = ConvertUtil.convert(advertisementMap.get("url"), String.class);
        Boolean enable = ConvertUtil.convert(advertisementMap.get("isEnabled"), Boolean.class);

        if (imgUrl == null || imgUrl.equals("")) {
            return ServiceResponse.error("请上传图片");
        }
        advertisement.setName(name);
        advertisement.setRemark(remark);
        advertisement.setImgUrl(imgUrl);
        advertisement.setEnabled(enable);
        if (updateNotNull(advertisement) != 1) {
            return ServiceResponse.error("更新失败");
        }
        return ServiceResponse.succ("更新成功");
    }

    /**
     * 详情维护，跳转标题展示区，跳转内容展示区
     */
    @Transactional
    public ServiceResponse detailUpdate(Map<String, Object> advertisementMap) {
        Long id = ConvertUtil.convert(advertisementMap.get("id"), Long.class);
        Advertisement advertisement = selectByKey(id);
        String jumpTitleRemark = ConvertUtil.convert(advertisementMap.get("jumpTitleRemark"), String.class);
        String jumpTitleImgUrl = ConvertUtil.convert(advertisementMap.get("jumpTitleImgUrl"), String.class);
        String jumpContentRemark = ConvertUtil.convert(advertisementMap.get("jumpContentRemark"), String.class);
        String jumpContentImgUrl = ConvertUtil.convert(advertisementMap.get("jumpContentImgUrl"), String.class);
        String jumpVideoRemark = ConvertUtil.convert(advertisementMap.get("jumpVideoRemark"), String.class);
        String jumpVideoUrl = ConvertUtil.convert(advertisementMap.get("jumpVideoUrl"), String.class);

        if (jumpTitleImgUrl == null || jumpContentImgUrl == null) {
            return ServiceResponse.error("请上传图片");
        }
        if(jumpTitleRemark == null) {
            jumpTitleRemark = "";
        }
        if(jumpContentRemark == null) {
            jumpContentRemark = "";
        }
        if(jumpVideoRemark == null) {
            jumpVideoRemark = "";
        }
        advertisement.setJumpTitleRemark(jumpTitleRemark);
        advertisement.setJumpTitleImgUrl(jumpTitleImgUrl);
        advertisement.setJumpContentRemark(jumpContentRemark);
        advertisement.setJumpContentImgUrl(jumpContentImgUrl);
        advertisement.setJumpVideoRemark(jumpVideoRemark);
        advertisement.setJumpVideoUrl(jumpVideoUrl);
        if (updateNotNull(advertisement) != 1) {
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
        Advertisement advertisement = selectByKey(id);
        if (expireNotNull(advertisement) != 1) {
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
        Advertisement advertisement = selectByKey(id);
        advertisement.setEnabled(isEnabled);
        return updateNotNull(advertisement) == 1;
    }

    /**
     * 设置参数（显示位置）
     * @param list
     */
    public void setParam(List<Advertisement> list) {
        for(int i = 0; i < list.size(); i++) {
            Advertisement advertisement = list.get(i);
        }
    }
}
