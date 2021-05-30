package com.chunhe.custom.pc.model.DiamondData;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EX3Page {

    private Integer pageCount;

    private Integer page;

    private Integer count;

    private List<EX3Diamond> result;

    private String message;
}


