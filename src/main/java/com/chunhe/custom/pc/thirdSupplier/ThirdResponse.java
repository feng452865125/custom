package com.chunhe.custom.pc.thirdSupplier;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThirdResponse {
    public int status;
    public msgdata msgdata;

    public int code;
    public String message;
    public data data;
    public int lockstatus;
    public String orderid;
}
