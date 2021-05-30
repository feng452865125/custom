package com.chunhe.custom.service.fm;

import com.chunhe.custom.datatables.DataTablesRequest;
import com.chunhe.custom.entity.SysConfig;
import com.chunhe.custom.mapper.SysConfigMapper;
import com.chunhe.custom.mybatis.BaseService;
import com.chunhe.custom.utils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 常用系统配置 freemarker-服务层
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-30
 */

@Service
public class FmSysConfigService extends BaseService<SysConfig> {

    private Logger logger = LogManager.getLogger(getClass());


    @Autowired
    private SysConfigMapper sysConfigMapper;

    public List<SysConfig> getList(){
        List<SysConfig> list = sysConfigMapper.listSysConfig(new SysConfig());
        return list;
    }
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
            criteria.andLike("name", name.getSearch().getValue());
        }
        criteria.andIsNull("deleteDate");
        return getMapper().selectByExample(example);
    }

    public String update(String key, Map<String, Object> sysConfigMap, SysConfig sysConfig) {
        String name = ConvertUtils.convert(sysConfigMap.get("name"), String.class);
        String value = ConvertUtils.convert(sysConfigMap.get("value"), String.class);
        String remark = ConvertUtils.convert(sysConfigMap.get("remark"), String.class);
        sysConfig.setSysConfigName(name);
        sysConfig.setSysConfigValue(value);
        sysConfig.setSysConfigRemark(remark);
        if (update(sysConfig) != 1) {
            return "error";
        }
        return sysConfig.getSysConfigValue();
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
    public String getSysConfigByKey(String key, String value) {
        SysConfig sysConfig = new SysConfig();
        sysConfig.setSysConfigKey(key);
        SysConfig config = sysConfigMapper.getSysConfig(sysConfig);
        if (config != null) {
            if (!StringUtils.isEmpty(config.getSysConfigValue())) {
                value = config.getSysConfigValue();
            }
        }
        System.out.println("key=" + key + "||value=" + value);
        return value;
    }
    
}