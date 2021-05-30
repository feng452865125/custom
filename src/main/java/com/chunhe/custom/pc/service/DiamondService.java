package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.pc.mapper.StyleMapper;
import com.chunhe.custom.pc.model.Parts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by white 2018年8月7日11:40:29
 */
@Service
public class DiamondService extends PartsService {

    @Autowired
    private StyleMapper styleMapper;
    //全部列表
    public List<Parts> findDiamondList(Parts parts) {
        parts.setType(Parts.TYPE_DIAMOND);
        return super.findPartsList(parts);
    }

    /**
     * 查询数据
     */
    public List<Parts> diamondList(DataTablesRequest dataTablesRequest) {
        return super.partsList(dataTablesRequest, Parts.TYPE_DIAMOND);
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Transactional
    public Parts getDiamond(Long id) {
        return super.getParts(id);
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> partsMap) {
        return super.save(partsMap, Parts.TYPE_DIAMOND);
    }

    /**
     * 是否更新成功
     */
    @Transactional
    public ServiceResponse update(Map<String, Object> partsMap) {
        return super.update(partsMap, Parts.TYPE_DIAMOND);
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
