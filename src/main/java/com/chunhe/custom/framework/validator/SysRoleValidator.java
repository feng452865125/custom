package com.chunhe.custom.framework.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.chunhe.custom.framework.service.SysPermissionService;

import java.util.Map;


@Component
public class SysRoleValidator extends BaseValidator {

    @Autowired
    private SysPermissionService sysPermissionService;

    @Override
    public Tip validate(Map<String, Object> map, String identity) {

        if (IDENTITY_CREATE.equals(identity)) {
            return commonValidate(map);
        } else if (IDENTITY_PATCH.equals(identity)) {
            return commonValidate(map);
        } else if (IDENTITY_CHECK.equals(identity)) {
            if (!validateNotNullRequired(map, "name")) {
                return new Tip("name", null, "角色名不能为空");
            }
        }
        return null;
    }

    private Tip commonValidate(Map<String, Object> map){

        if(!validateNotNullRequired(map, "name")){
            return new Tip("name", null, "角色名不能为空");
        }

        if(!validateNotNullRequired(map, "isSystem")){
            return new Tip("isSystem", null, "是否系统内置不能为空");
        }

        if(!validateString(map, "name", 0, 32)){
            return new Tip("name", null, "角色名长度不能超过32");
        }

        if(validateNotNullRequired(map, "description") && !validateString(map, "description", 0, 64)){
            return new Tip("description", null, "权限描述长度不能超过64");
        }

        return null;
    }

}