package com.chunhe.custom.pc.thirdSupplier;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class msgdata {
    public String token;
    public String total;
    public rows[] rows;
    public String productid;
    public int operatestatus;
    public int lockstatus;
    public String operatemessage;
    public String orderid;

    public String message;
    public int status;
}
