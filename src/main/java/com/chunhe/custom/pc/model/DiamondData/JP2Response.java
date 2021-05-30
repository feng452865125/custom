package com.chunhe.custom.pc.model.DiamondData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JP2Response {

    public int code;
    public String msg;
    public String count;
    public String cols;
    public String SumHtml;
    public String orderListCols;
    public JP2msgdata data;

}


