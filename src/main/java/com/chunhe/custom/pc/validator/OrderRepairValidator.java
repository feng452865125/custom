package com.chunhe.custom.pc.validator;

import com.chunhe.custom.framework.validator.BaseValidator;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderRepairValidator extends BaseValidator {

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

        if(!validateNotNullRequired(map, "type")){
            return new Tip("type", null, "报修类型不能为空");
        }

        if(!validateNotNullRequired(map, "address")){
            if(!validateNotNullRequired(map, "build")
                    || !validateNotNullRequired(map, "floor")
                    || !validateNotNullRequired(map, "room")){
                return new Tip("address", null, "地址不能为空");
            }
        }

        return null;
    }

}
