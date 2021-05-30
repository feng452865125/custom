package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.pc.mapper.ThreeDFileMapper;
import com.chunhe.custom.pc.model.ThreeDFile;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by white 2018-8-3 16:51:26
 */
@Service
public class ThreeDFileService extends BaseService<ThreeDFile> {

    @Autowired
    private ThreeDFileMapper threeDFileMapper;

    //全部列表
    public List<ThreeDFile> findThreeDFileList(ThreeDFile threeDFile) {
        List<ThreeDFile> list = new ArrayList<>();
        List<ThreeDFile> modulesList = threeDFileMapper.findThreeDModulesList(threeDFile);
        for (int i = 0; i < modulesList.size(); i++) {
            ThreeDFile modules = modulesList.get(i);
            list.add(modules);
            ThreeDFile file = new ThreeDFile();
            file.setModules(modules.getModules());
            List<ThreeDFile> childList = threeDFileMapper.findThreeDFileList(file);
            list.get(i).setChildList(childList);
        }
        return list;
    }

    /**
     * 查询数据
     */
    public List<ThreeDFile> threeDFileList(DataTablesRequest dataTablesRequest) {
        ThreeDFile threeDFile = new ThreeDFile();
        //排序
//        String orderBy = dataTablesRequest.orders();
//        if (StringUtils.isNotBlank(orderBy)) {
//            threeDFile.setOrderBy(orderBy);
//        }
        //类型
        DataTablesRequest.Column type = dataTablesRequest.getColumn("type");
        if (StringUtils.isNotBlank(type.getSearch().getValue())) {
            threeDFile.setType(new Integer(type.getSearch().getValue()));
        }
//        //订单开始时间
//        DataTablesRequest.Column startDateColumn = dataTablesRequest.getColumn("startDate");
//        if (StringUtils.isNotBlank(startDateColumn.getSearch().getValue())) {
//            String startDate = ConvertUtil.convert(startDateColumn.getSearch().getValue(), String.class);
//            threeDFile.setStartDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"));
//        }

        //其他
        List<ThreeDFile> threeDFileList = this.findThreeDFileList(threeDFile);
        return threeDFileList;
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Transactional
    public ThreeDFile getThreeDFile(Long id) {
        ThreeDFile threeDFile = new ThreeDFile();
        threeDFile.setId(id);
        ThreeDFile file = threeDFileMapper.getThreeDFile(threeDFile);
        return file;
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> UseLogMap) {
        ThreeDFile threeDFile = new ThreeDFile();
        String code = ConvertUtil.convert(UseLogMap.get("code"), String.class);
        String name = ConvertUtil.convert(UseLogMap.get("name"), String.class);
        String remark = ConvertUtil.convert(UseLogMap.get("remark"), String.class);
        String imgUrl = ConvertUtil.convert(UseLogMap.get("url"), String.class);

        if (insertNotNull(threeDFile) != 1) {
            return ServiceResponse.error("添加失败");
        }
        return ServiceResponse.succ("添加成功");
    }

    /**
     * 是否更新成功
     */
    @Transactional
    public ServiceResponse update(Map<String, Object> UseLogMap) {
        Long id = ConvertUtil.convert(UseLogMap.get("id"), Long.class);
        ThreeDFile threeDFile = selectByKey(id);
        String code = ConvertUtil.convert(UseLogMap.get("code"), String.class);
        String name = ConvertUtil.convert(UseLogMap.get("name"), String.class);
        String remark = ConvertUtil.convert(UseLogMap.get("remark"), String.class);
        String imgUrl = ConvertUtil.convert(UseLogMap.get("url"), String.class);
        if (updateNotNull(threeDFile) != 1) {
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
        ThreeDFile threeDFile = selectByKey(id);
        if (expireNotNull(threeDFile) != 1) {
            return ServiceResponse.error("删除失败");
        }
        return ServiceResponse.succ("删除成功");
    }

}
