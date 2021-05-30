package com.chunhe.custom.framework.validator;

import com.chunhe.custom.framework.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SysUserValidator extends BaseValidator {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Tip validate(Map<String, Object> map, String identity) {

        if(IDENTITY_CREATE.equals(identity)){
            if(!validateNotNullRequired(map,  "code")){
                return new Tip("code", null, "用户编码不为空");
            }
            if(!validateNotNullRequired(map,  "username")){
                return new Tip("username", null, "登录账号不能为空");
            }
            if(!validateNotNullRequired(map,  "password")){
                return new Tip("password", null, "登录密码不能为空");
            }
            commonValidate(map);
        } else if(IDENTITY_PATCH.equals(identity)){
            return commonValidate(map);
        } else if(IDENTITY_CHECK.equals(identity)){
           if(!validateNotNullRequired(map,  "username")){
               return new Tip("username", null, "账号码不能为空");
           }
        }

        return null;
    }

    private Tip commonValidate(Map<String, Object> map){

        if(!validateNotNullRequired(map,  "name")){
            return new Tip("name", null, "用户名不能为空");
        }

        if(!validateNotNullRequired(map,  "isLocked")){
            return new Tip("isLocked", null, "锁定状态不能为空");
        }

        if(!validateNotNullRequired(map,  "isEnabled")){
            return new Tip("isEnabled", null, "可用状态不能为空");
        }

        if(!validateString(map, "name", 0, 32)){
            return new Tip("name", null, "名称长度不能超过32");
        }

        return null;
    }

}
