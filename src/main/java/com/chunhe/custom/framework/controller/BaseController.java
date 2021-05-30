package com.chunhe.custom.framework.controller;

import com.chunhe.custom.framework.exception.BadRequestException;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.utils.SpringUtil;
import com.chunhe.custom.framework.validator.BaseValidator;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * Created by xuqiang on 2017/6/12.
 */
public abstract class BaseController {

    public static BaseValidator getValidator(String component) {
        return SpringUtil.getBean(component);
    }

    public Map<String, Object> packageAndValid(String prefix, @NonNull Map<String, Object> map, @NonNull String component, String identity) {
        return packageAndValid(prefix, map, getValidator(component), identity);
    }

    public Map<String, Object> packageAndValid(String prefix, @NonNull Map<String, Object> map, @NonNull BaseValidator validator, String identity) {
        if (StringUtils.isNotBlank(prefix)) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (StringUtils.isBlank(entry.getKey()) && !entry.getKey().startsWith(prefix)) {
                    map.remove(entry.getKey());
                }
            }
        }

        final BaseValidator.Tip tip = validator.validate(map, identity);
        if (tip != null) {
            throw new BadRequestException(tip.getKey(), tip.getMessage());
        }

        return map;
    }

    /**
     * 操作的响应处理
     * @param result
     * @return
     */
    public ResponseEntity responseDeal(ServiceResponse result) {
        if (!result.isSucc()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        return ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }
}
