package com.chunhe.custom.pc.model.DiamondData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HAOResponse {

    private Integer status;

    private String message;

    private HAOMsgdata msgdata;

    private String tarurl;
}


