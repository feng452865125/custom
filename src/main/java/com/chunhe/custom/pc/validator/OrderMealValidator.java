package com.chunhe.custom.pc.validator;

import com.chunhe.custom.framework.validator.BaseValidator;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderMealValidator extends BaseValidator {

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

        if(!validateNotNullRequired(map, "orderMealDetailList")){
            return new Tip("orderMealDetailList", null, "订单套餐不能为空");
        }

        return null;
    }

}
