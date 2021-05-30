package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.Token;

import java.util.List;

public interface TokenMapper extends MyMapper<Token> {

    public List<Token> findToken(Token token);

    public Token getToken(Token token);

    public int setTimeOver(Token token);

}