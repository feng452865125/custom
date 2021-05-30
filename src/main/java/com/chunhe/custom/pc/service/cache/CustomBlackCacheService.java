package com.chunhe.custom.pc.service.cache;

import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.pc.mapper.CustomBlackMapper;
import com.chunhe.custom.pc.model.CustomBlack;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.CachePut;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by white 2021年3月22日23:43:34
 * 黑名单cache管理
 */
@Service
public class CustomBlackCacheService extends BaseService<CustomBlack> {

    @Autowired
    private CustomBlackMapper customBlackMapper;


//    @CacheEvict(cacheNames = "CacheCustomBlackManager", key = "#a0")
    public void delCacheCustomBlackManager(String cacheKey) {
        System.out.println("清除'" + cacheKey + "'缓存信息");
    }


//    @Cacheable(cacheNames = "CacheCustomBlackManager", key = "#a0")
    public HashMap<String, String> getCacheCustomBlackManager(String cacheKey) {
        System.out.println("mysql查询黑名单");
        CustomBlack cb = new CustomBlack();
        cb.setBlackEnable(true);
        HashMap<String, String> map = new HashMap<>();
        List<CustomBlack> list = customBlackMapper.findCustomBlackList(cb);
        for (CustomBlack customBlack : list) {
            map.put(customBlack.getBlackZsh(), customBlack.getBlackZsh());
        }
        return map;
    }
}
