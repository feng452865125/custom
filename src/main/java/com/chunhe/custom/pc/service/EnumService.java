package com.chunhe.custom.pc.service;

import com.alibaba.fastjson.JSONObject;
import com.chunhe.custom.utils.DictUtils;
import org.springframework.stereotype.Service;

/**
 * Created by white 2018年7月14日15:00:28
 */
@Service
public class EnumService {

    /**
     * 获取枚举值列表
     *
     * @param typeName
     * @return
     */
    public JSONObject findKeyEnum(String typeName) {
        JSONObject jsonObject = DictUtils.findDicByType(typeName);
        return jsonObject;
    }

    /**
     * 获取枚举
     *
     * @param typeName
     * @param key
     * @return
     */
    public String getKeyEnum(String typeName, Integer key) {
        String jsonObject = DictUtils.findValueByTypeAndKey(typeName, key);
        return jsonObject;
    }


}
