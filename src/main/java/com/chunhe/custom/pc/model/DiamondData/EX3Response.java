package com.chunhe.custom.pc.model.DiamondData;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EX3Response {

    private Integer error;

    private String message;

    private List<EX3Response2> succ;

    private List<EX3Response2> fail;

}


