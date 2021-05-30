package com.chunhe.custom.pc.model.DiamondData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HSTResponse {

    private Integer status;

    private String tarurl;

    private String message;

    private HSTMsgdata msgdata;

}


