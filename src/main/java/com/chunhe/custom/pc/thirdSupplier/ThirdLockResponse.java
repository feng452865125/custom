package com.chunhe.custom.pc.thirdSupplier;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThirdLockResponse {
    public int status;
    public String message;
    public msgdata[] msgdata;
}
