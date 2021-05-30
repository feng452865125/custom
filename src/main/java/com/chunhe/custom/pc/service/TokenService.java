package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.MyTool;
import com.chunhe.custom.pc.mapper.TokenMapper;
import com.chunhe.custom.pc.model.Token;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by white 2019年4月16日10:38:45
 */
@Service
public class TokenService extends BaseService<Token> {

    @Autowired
    private TokenMapper tokenMapper;

    /**
     * 生成token
     *
     * @param id
     * @return
     */
    public Token createToken(Integer id) {
        Token token = new Token();
        token.setCreateDate(new Date());
        token.setUserId(id);
        token.setLastDate(new Date(new Date().getTime() + 60 * 60 * 1000));
        // 生成token
        String tokenStr = MyTool.makeToken(id);
        token.setToken(tokenStr);
        tokenMapper.insert(token);
        //设置其它token失效
        tokenMapper.setTimeOver(token);
        return token;
    }

    /**
     * 检查token是否过期
     *
     * @param token
     * @return
     */
    public boolean checkToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        Token tt = new Token(token);
        Token tk = tokenMapper.getToken(tt);
        if (tk == null || tk.getId() == null || tk.getExpireDate() != null ||
                (tk.getLastDate() != null && tk.getLastDate().before(new Date()))) {
            return false;
        }
        tk.setLastDate(new Date(new Date().getTime() + 60 * 60 * 1000));
        tokenMapper.updateByPrimaryKey(tk);
        return true;
    }

}
