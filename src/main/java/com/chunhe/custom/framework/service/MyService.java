package com.chunhe.custom.framework.service;

import com.chunhe.custom.framework.mybatis.MyMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;

import java.util.ArrayList;
import java.util.List;

public abstract class MyService<T> extends BaseService<T> {

    public List<T> selectByKeys(long[] keys) {
        if (keys.length == 0) {
            return Lists.newArrayList();
        }
        if (keys.length <= 1000) {
            return ((MyMapper<T>) getMapper()).selectByIds(StringUtils.join(keys, ','));
        }

        List<T> result = new ArrayList<>(keys.length);
        int count = keys.length / 1000 + (keys.length % 1000 == 0 ? 0 : 1);
        for (int i = 0 ; i < count; i++) {
            int start = i * 1000;
            int end = i == (count - 1) ? keys.length : 1000;
            long[] subarray = ArrayUtils.subarray(keys, start, end);
            result.addAll(selectByKeys(subarray));
        }
        return result;
    }

}
