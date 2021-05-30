package com.chunhe.custom.framework.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.mapper.SysConfigMapper;
import com.chunhe.custom.framework.model.SysConfig;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.framework.utils.TableUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CachePut;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class SysConfigService extends MyService<SysConfig> {

    @Autowired
    private SysConfigMapper sysConfigMapper;

//    @Autowired
//    private ConcurrentMapCacheManager concurrentMapCacheManager;

    /**
     * 查询数据
     */
    public List<SysConfig> sysConfigList(DataTablesRequest dataTablesRequest) {
        Example example = new Example(SysConfig.class);
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
        criteria.andIsNull("expireDate");
        return getMapper().selectByExample(example);
    }

//    @CachePut(value = "sysConfigManager", key = "#a0")
    public String update(String key, Map<String, Object> sysConfigMap, SysConfig sysConfig) {
        String name = ConvertUtil.convert(sysConfigMap.get("name"), String.class);
        String value = ConvertUtil.convert(sysConfigMap.get("value"), String.class);
        String remark = ConvertUtil.convert(sysConfigMap.get("remark"), String.class);
        sysConfig.setName(name);
        sysConfig.setValue(value);
        sysConfig.setRemark(remark);
        if (updateNotNull(sysConfig) != 1) {
            return "error";
        }
        return sysConfig.getValue();
    }

    /**
     * 查询
     *
     * @param sysConfig
     * @return
     */
    public SysConfig getSysConfig(SysConfig sysConfig) {
        SysConfig config = sysConfigMapper.getSysConfig(sysConfig);
        return config;
    }

    /**
     * 查询key对应的value
     *
     * @param key
     * @return
     */
//    @Cacheable(cacheNames = "sysConfigManager", key = "#a0")
    public String getSysConfigByKey(String key, String value) {
        SysConfig sysConfig = new SysConfig();
        sysConfig.setKey(key);
        SysConfig config = sysConfigMapper.getSysConfig(sysConfig);
        if (config != null) {
            if (config.getValue() != null && !config.getValue().equals("")) {
                value = config.getValue();
            }
        }
        System.out.println("key=" + key + "||value=" + value);
        return value;
    }

}
