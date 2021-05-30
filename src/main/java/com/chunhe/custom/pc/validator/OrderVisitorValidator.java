package com.chunhe.custom.pc.validator;

import com.chunhe.custom.framework.validator.BaseValidator;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderVisitorValidator extends BaseValidator {

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

        if(!validateNotNullRequired(map, "name")){
            return new Tip("name", null, "姓名不能为空");
        }

        if(!validateNotNullRequired(map, "mobile")){
            return new Tip("mobile", null, "联系号码不能为空");
        }

        if(!validateNotNullRequired(map, "comeDate")){
            return new Tip("comeDate", null, "来访时间不能为空");
        }

        if(!validateNotNullRequired(map, "total")){
            return new Tip("total", null, "总人数不能为空");
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
