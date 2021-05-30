package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.pc.model.Parts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by white 2018年7月14日15:00:28
 * 花头
 */
@Service
public class FlowerHeadService extends PartsService {

    //全部列表
    public List<Parts> findFlowerHeadList(Parts parts) {
        parts.setType(Parts.TYPE_FLOWER_HEAD);
        return super.findPartsList(parts);
    }

    /**
     * 查询数据
     */
    public List<Parts> flowerHeadList(DataTablesRequest dataTablesRequest) {
        return super.partsList(dataTablesRequest, Parts.TYPE_FLOWER_HEAD);
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Transactional
    public Parts getFlowerHead(Long id) {
        return super.getParts(id);
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> partsMap) {
        return super.save(partsMap, Parts.TYPE_FLOWER_HEAD);
    }

    /**
     * 是否更新成功
     */
    @Transactional
    public ServiceResponse update(Map<String, Object> partsMap) {
        return super.update(partsMap, Parts.TYPE_FLOWER_HEAD);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Transactional
    public ServiceResponse deleteById(Long id) {
        return super.deleteById(id);
    }

}
