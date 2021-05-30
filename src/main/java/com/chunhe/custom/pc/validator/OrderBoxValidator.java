package com.chunhe.custom.pc.validator;

import com.chunhe.custom.framework.validator.BaseValidator;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderBoxValidator extends BaseValidator {

    @Override
    public Tip validate(Map<String, Object> map, String identity) {
        if (IDENTITY_CREATE.equals(identity)) {
            //创建
            if (!validateNotNullRequired(map, "userId")) {
                return new Tip("userId", null, "用户不能为空");
            }
            return commonValidate(map);
        }
        return null;
    }

    private Tip commonValidate(Map<String, Object> map){

        if(!validateNotNullRequired(map, "dinnerDate")){
            return new Tip("dinnerDate", null, "用餐时间不能为空");
        }

        if(!validateNotNullRequired(map, "type")){
            return new Tip("type", null, "用餐类型不能为空");
        }

        if(!validateNotNullRequired(map, "number")){
            return new Tip("number", null, "人数不能为空");
        }

        if(!validateNotNullRequired(map, "standard")){
            return new Tip("standard", null, "人均餐标不能为空");
        }

        return null;
    }

}
