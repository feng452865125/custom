package com.chunhe.custom.pc.service;

import com.chunhe.custom.pc.model.PartsMiddle;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.pc.mapper.PartsMiddleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PartsMiddleService extends BaseService<PartsMiddle> {

    @Autowired
    private PartsMiddleMapper partsMiddleMapper;



    public List<PartsMiddle> findPartsList(PartsMiddle partsMiddle){
        List<PartsMiddle> partsList = partsMiddleMapper.findPartsMiddleList(partsMiddle);
        return partsList;
    }




}
