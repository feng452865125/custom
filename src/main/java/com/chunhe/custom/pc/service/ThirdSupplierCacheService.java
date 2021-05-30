package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.pc.mapper.ThirdSupplierMapper;
import com.chunhe.custom.pc.model.ThirdSupplier;
import com.chunhe.custom.pc.model.*;
import com.chunhe.custom.pc.service.SynDiamond.*;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.CachePut;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by white 2019年3月18日19:54:00
 * 第三方供应商（钻石）
 */
@Service
public class ThirdSupplierCacheService extends BaseService<ThirdSupplier> {

    @Autowired
    private ThirdSupplierMapper thirdSupplierMapper;


//    @CachePut(value = "CacheThirdSupplierManager", key = "#result.shortName")
    public ThirdSupplier setCacheThirdSupplierManager(ThirdSupplier thirdSupplier) {
        System.out.println("添加'" + thirdSupplier.getShortName() + "'缓存信息");
        return thirdSupplier;
    }

//    @CacheEvict(cacheNames = "CacheThirdSupplierManager", key = "#a0")
    public void delCacheThirdSupplierManager(String shortName) {
        System.out.println("清除'" + shortName + "'缓存信息");
    }

//    @Cacheable(cacheNames = "CacheThirdSupplierManager", key = "#a0")
    public ThirdSupplier getCacheThirdSupplierManager(String shortName) {
        System.out.println("mysql查询供应商: " + shortName);
        ThirdSupplier thirdSupplier = new ThirdSupplier();
        thirdSupplier.setShortName(shortName.toUpperCase());
        ThirdSupplier ts = thirdSupplierMapper.getThirdSupplier(thirdSupplier);
        return ts;
    }


}
