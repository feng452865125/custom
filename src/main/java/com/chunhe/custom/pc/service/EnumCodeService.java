package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.pc.mapper.EnumCodeMapper;
import com.chunhe.custom.pc.model.EnumCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by white 2018年8月2日14:32:49
 */
@Service
public class EnumCodeService extends BaseService<EnumCode> {

    @Autowired
    private EnumCodeMapper enumCodeMapper;

    /**
     * 所有编码列表
     *
     * @param enumCode
     * @return
     */
    public List<EnumCode> findEnumCodeList(EnumCode enumCode) {
        List<EnumCode> enumCodeList = enumCodeMapper.findEnumCodeList(enumCode);
        return enumCodeList;
    }

    /**
     * 查询字典数据
     *
     * @param code
     * @param type
     * @return
     */
    @Transactional
    public EnumCode getEnumCode(String code, String type) {
        EnumCode enumCode = new EnumCode();
        enumCode.setCode(code);
        enumCode.setType(type);
        EnumCode ec = enumCodeMapper.getEnumCode(enumCode);
        return ec;
    }

    /**
     * 查询具体编码
     *
     * @param name
     * @param type
     * @return
     */
    @Transactional
    public String getEnumCodeByName(String name, String type) {
        String code = "";
        if (name == null || name.equals("")) {
            return code;
        }
        EnumCode enumCode = new EnumCode();
        enumCode.setName(name);
        enumCode.setType(type);
        EnumCode ec = enumCodeMapper.getEnumCode(enumCode);
        if (ec != null) {
            code = ec.getCode();
        }
        return code;
    }

    /**
     * 查询字典数据
     *
     * @param code
     * @param type
     * @return
     */
    @Transactional
    public String getEnumCodeByCode(String code, String type) {
        String name = "";
        if (code == null || code.equals("")) {
            return name;
        }
        EnumCode enumCode = new EnumCode();
        enumCode.setCode(code);
        enumCode.setType(type);
        EnumCode ec = enumCodeMapper.getEnumCode(enumCode);
        if (ec != null) {
            name = ec.getName();
        }
        return name;
    }
}
